package com.maradamark99.thesis.auth;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maradamark99.thesis.auth.verification.AlreadyVerifiedException;
import com.maradamark99.thesis.auth.verification.RegistrationVerificationEmailDetails;
import com.maradamark99.thesis.auth.verification.VerificationSender;
import com.maradamark99.thesis.auth.verification.VerificationToken;
import com.maradamark99.thesis.auth.verification.VerificationTokenExpiredException;
import com.maradamark99.thesis.auth.verification.VerificationTokenRepository;
import com.maradamark99.thesis.exception.ResourceAlreadyExistsException;
import com.maradamark99.thesis.exception.ResourceNotFoundException;
import com.maradamark99.thesis.user.User;
import com.maradamark99.thesis.user.UserRepository;
import com.maradamark99.thesis.user.UserRoleRepository;
import com.maradamark99.thesis.util.JWTUtil;
import com.maradamark99.thesis.util.RequestUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
	
	private final AuthenticationManager authManager;
	
	private final UserRepository userRepository;
	
    private final UserRoleRepository roleRepository;
    
    private final VerificationTokenRepository verificationTokenRepository;

    private final VerificationSender<RegistrationVerificationEmailDetails> verificationSender;

    private final PasswordEncoder passwordEncoder;
    
    public AuthTokenResponse login(AuthRequest user) {
    	var authentication = new UsernamePasswordAuthenticationToken(user.email(), user.password());
    	var authResult = authManager.authenticate(authentication);
    	
    	var roles = authResult.getAuthorities()
    			.stream()
    			.map(a -> a.getAuthority())
    			.collect(Collectors.toSet());
    	
    	var token = JWTUtil.generateToken(user.email(), roles);
    	return new AuthTokenResponse(token);
    }

    @Transactional
    public void register(AuthRequest user, HttpServletRequest request) {
    	var role = roleRepository.findByRoleIgnoreCase("CUSTOMER");
        var userToRegister = User.builder()
                .email(user.email())
                .roles(Set.of(role))
                .password(passwordEncoder.encode(user.password()))
                .build();

        if (userRepository.existsByEmail(userToRegister.getEmail())) {
            throw new ResourceAlreadyExistsException("User with email: %s already exists.".formatted(userToRegister.getEmail()));
        }

        var token = VerificationToken.withUUIDKey();
        userToRegister.setVerificationToken(token);
        var registeredUser = userRepository.save(userToRegister);

        log.info("user: {} saved", registeredUser);
        sendVerificationEmail(registeredUser, token.getKey(), request);
        log.info("Registration complete");
    }

    @Transactional
    public void verifyEmail(String token) {
        log.info("verifyEmail is called with token {}", token);
        var verificationToken = verificationTokenRepository.findByKey(token)
                .orElseThrow(ResourceNotFoundException::new);
        
        var currentDateTime = LocalDateTime.now();
        if (verificationToken.getExpiryDate().isBefore(currentDateTime)) {
            verificationTokenRepository.delete(verificationToken);
            throw new VerificationTokenExpiredException();
        }
        
        var user = verificationToken.getUser();
        user.setEnabled(true);
        log.info("user: {}", user);
        userRepository.save(user);
    }

    @Transactional
    public void resendVerificationEmail(String email, HttpServletRequest request) {
        log.info("attempting verification for email: {}", email);
        var user = userRepository.findByEmail(email)
        		.orElseThrow(() -> new ResourceNotFoundException("User with email: %s does not exist.".formatted(email)));
        
        if (user.isEnabled()) {
            throw new AlreadyVerifiedException();
        }
        
        var oldToken = user.getVerificationToken();
        if (oldToken != null) {
        	verificationTokenRepository.delete(oldToken);        	
        }
        
        var token = VerificationToken.withUUIDKey();
        user.setVerificationToken(token);
        sendVerificationEmail(user, token.getKey(), request);
    }
    

    private void sendVerificationEmail(User registeredUser, String token, HttpServletRequest request) {
        var completeServerUrl = RequestUtil.getFullServerAddress(request);
        verificationSender.sendVerification(
                new RegistrationVerificationEmailDetails(
                        registeredUser,
                        token,
                        completeServerUrl + "/api/v1/auth/verify-email"));
    }

    

}


package com.maradamark99.thesis.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.maradamark99.thesis.user.User;
import com.maradamark99.thesis.user.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public void register(RegistrationRequest user) {
        var toSave = User.builder()
                .email(user.email())
                .username(user.username())
                .password(passwordEncoder.encode(user.password()))
                .build();
        userRepository.save(toSave);
    }
}

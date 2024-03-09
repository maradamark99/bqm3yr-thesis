package com.maradamark99.thesis.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	
	@PostMapping("/login")
	public ResponseEntity<AuthTokenResponse> login(@RequestBody AuthRequest user) {
		return ResponseEntity.ok().body(authService.login(user));
	}

	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody AuthRequest user, HttpServletRequest request) {
		authService.register(user, request);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@GetMapping("/verify-email/{token}")
	public ResponseEntity<Void> verifyEmail(@PathVariable String token) {
		authService.verifyEmail(token);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/resend-verification-email")
    public ResponseEntity<Void> resendVerificationEmail(
            @RequestBody String email,
            HttpServletRequest request) {
		authService.resendVerificationEmail(email, request);
		return ResponseEntity.ok().build();
    }

}

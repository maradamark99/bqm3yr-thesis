package com.maradamark99.thesis.auth;

import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;

@Validated 
public record AuthRequest(
		@Email String email,
		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "Password must be at least 8 characters long and contain at least one letter, one number, and one special character.") String password) {
}

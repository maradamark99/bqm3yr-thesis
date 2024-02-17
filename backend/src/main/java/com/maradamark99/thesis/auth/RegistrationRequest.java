package com.maradamark99.thesis.auth;

import org.hibernate.validator.constraints.Length;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record RegistrationRequest(
		@Email String email,
		@NotBlank @Length(min = 3) String username,
		@NotBlank @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$", message = "Password must be at least 8 characters long and contain at least one letter, one number, and one special character.") String password) {

}

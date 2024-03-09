package com.maradamark99.thesis.email;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.mail")
public record EmailProperties(
		@NotBlank String host,
		@Min(25) @Max(2525) int port,
		@NotBlank String username,
		@NotBlank String password) {
}

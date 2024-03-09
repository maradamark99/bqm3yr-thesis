package com.maradamark99.thesis.auth.verification;

import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.maradamark99.thesis.auth.email.EmailService;

import lombok.RequiredArgsConstructor;

@Component
@Profile("prod")
@RequiredArgsConstructor
public class RegistrationVerificationEmailSender implements VerificationSender<RegistrationVerificationEmailDetails> {

	private final EmailService emailService;

	@Async
	@Override
	public void sendVerification(RegistrationVerificationEmailDetails verificationDetails) {
		emailService.sendEmail(
				verificationDetails.getTo().getEmail(),
				"Email verification",
				"""
						<a href="%s/%s">Click here to verify your email</a>
						""".formatted(
						verificationDetails.getVerificationUrl(),
						verificationDetails.getVerificationKey()));
	}

}

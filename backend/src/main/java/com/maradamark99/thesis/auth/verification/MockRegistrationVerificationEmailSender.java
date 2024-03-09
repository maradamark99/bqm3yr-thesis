package com.maradamark99.thesis.auth.verification;


import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Component
@Profile("dev")
@Slf4j
public class MockRegistrationVerificationEmailSender
		implements VerificationSender<RegistrationVerificationEmailDetails> {

	@Override
	public void sendVerification(RegistrationVerificationEmailDetails verificationDetails) {
		log.info("Email sent to: {} with message: " +
				"%s/%s".formatted(
						verificationDetails.getVerificationUrl(),
						verificationDetails.getVerificationKey()),
				verificationDetails.getTo().getEmail());
	}

}

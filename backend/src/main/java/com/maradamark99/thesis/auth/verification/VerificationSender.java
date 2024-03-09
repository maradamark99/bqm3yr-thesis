package com.maradamark99.thesis.auth.verification;

public interface VerificationSender<T extends VerificationDetails> {

	void sendVerification(T verificationDetails);

}
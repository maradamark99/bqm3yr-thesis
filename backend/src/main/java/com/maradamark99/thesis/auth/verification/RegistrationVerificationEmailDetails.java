package com.maradamark99.thesis.auth.verification;

import com.maradamark99.thesis.user.User;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class RegistrationVerificationEmailDetails extends VerificationDetails {

	private final String verificationUrl;

	public RegistrationVerificationEmailDetails(User to, String verificationKey, String verificationUrl) {
		super(to, verificationKey);
		this.verificationUrl = verificationUrl;
	}

}

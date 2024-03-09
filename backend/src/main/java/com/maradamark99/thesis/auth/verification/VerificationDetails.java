package com.maradamark99.thesis.auth.verification;

import com.maradamark99.thesis.user.User;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class VerificationDetails {

	private final User to;

	private final String verificationKey;

}

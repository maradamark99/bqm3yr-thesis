package com.maradamark99.thesis.auth.verification;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class AlreadyVerifiedException extends RuntimeException {
	private static final long serialVersionUID = 3096445454544037691L;

	public AlreadyVerifiedException(String message) {
		super(message);
	}

}

package com.maradamark99.thesis.auth.verification;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class VerificationTokenExpiredException extends RuntimeException {
    private static final long serialVersionUID = -355698867141327887L;

    public VerificationTokenExpiredException(String message) {
        super(message);
    }
}

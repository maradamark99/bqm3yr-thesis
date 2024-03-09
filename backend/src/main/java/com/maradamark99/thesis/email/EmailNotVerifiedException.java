package com.maradamark99.thesis.email;

public class EmailNotVerifiedException extends RuntimeException {
    private static final long serialVersionUID = -3108741639550871826L;

    public EmailNotVerifiedException(String message) {
        super(message);
    }
}

package com.maradamark99.thesis.email;

public interface EmailService {
    void sendEmail(String to, String subject, String message);
}

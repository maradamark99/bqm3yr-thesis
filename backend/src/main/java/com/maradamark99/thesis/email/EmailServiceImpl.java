package com.maradamark99.thesis.email;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {

    private final EmailProperties emailProperties;

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to, String subject, String message) {
        try {
            var mailMessage = mailSender.createMimeMessage();
            var helper = new MimeMessageHelper(mailMessage, true);
            helper.setFrom(emailProperties.username());
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(message, true);
            mailSender.send(mailMessage);
        } catch (MessagingException e) {
            log.warn("An error occurred while sending email to {}", to);
        }
    }

}

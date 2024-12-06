package com.Authentication.JWTAuthentication.service;

import com.Authentication.JWTAuthentication.email.EmailContext;
import com.Authentication.JWTAuthentication.email.EmailContext;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class EmailService {

    private final JavaMailSender emailSender;

    public EmailService(JavaMailSender emailSender) {
        this.emailSender = emailSender;
    }

    public void sendMail(final EmailContext email) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        helper.setTo(email.getTo());
        helper.setSubject(email.getSubject());
        helper.setFrom(email.getFrom());
        helper.setText(email.getContext().get("verificationLink"), false);

        emailSender.send(message);
    }
}


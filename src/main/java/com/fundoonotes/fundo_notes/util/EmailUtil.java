package com.fundoonotes.fundo_notes.util;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailUtil {

    private final JavaMailSender mailSender;

    public void sendOtp(String email, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Fundoo Notes - OTP Verification");
        message.setText(
                "Your OTP is: " + otp +
                        "\n\nThis OTP is valid for 10 minutes."
        );

        mailSender.send(message);
    }
}
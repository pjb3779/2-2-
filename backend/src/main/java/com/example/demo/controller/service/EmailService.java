package com.example.demo.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
@Component
@ComponentScan
public class EmailService {

    @Autowired
    private JavaMailSenderImpl mailSender;


    public void sendVerificationEmail(String email, String verificationToken) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Account Verification");
        message.setText("Below is your key:\n\n"
                +  verificationToken);

        mailSender.send(message);
    }
}

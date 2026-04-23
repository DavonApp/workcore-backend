package com.todolist.todolist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.todolist.todolist.model.Contact;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
    
    public void sendEmail(Contact form) {
        
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("workcore.app@gmail.com");
        message.setTo("workcore.app@gmail.com"); // Where the email will be recieved
        message.setSubject(form.getSubject());

        message.setText(
                "Name: " + form.getName() + "\n" +
                "Email: " + form.getEmail() + "\n" +
                "Message:\n" + form.getMessage() 
        );

        mailSender.send(message);

    }

    public void sendPasswordResetEmail (String toEmail, String token) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();

            message.setTo(toEmail);
            message.setSubject("Reset your WorkCore password");

            message.setText(
                "Hi, \n\n" +
                "You requested a password reset for your WorkCore account. \n\n" +
                "Click the link below to reset your password (expires in 1 hour):\n\n" +
                "https://workcore-app.netlify.app/reset-password.html?token=" + token + "\n\n" +
                "If you didn't request this, you can safefly ignore this email. \n\n" +
                "- WorkCore"
            );
            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}

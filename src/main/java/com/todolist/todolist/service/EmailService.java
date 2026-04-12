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

        message.setTo("workcore.app@gmail.com"); // Where the email will be recieved
        message.setSubject(form.getSubject());

        message.setText(
                "Name: " + form.getName() + "\n" +
                "Email: " + form.getEmail() + "\n" +
                "Message:\n" + form.getMessage() 
        );

        mailSender.send(message);

    }
}

package com.todolist.todolist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.todolist.todolist.service.EmailService;
import com.todolist.todolist.model.Contact;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/contact")
public class ContactController {
    
    @Autowired
    private EmailService emailService;

    @PostMapping
    public String sendMessage(@RequestBody Contact form) {
        emailService.sendEmail(form);
        return "Message sent successfully";
    }
    
}

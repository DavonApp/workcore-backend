package com.todolist.todolist.service;

import com.todolist.todolist.model.User;
import com.todolist.todolist.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Constructor Injection
    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User registerUser (String email, String password){
        User existingUser = userRepository.findByEmail(email);
        if(existingUser != null){
            throw new RuntimeException("User already exists");
        } 

        // Hash password before storing 
        User user = new User(email, passwordEncoder.encode(password));
        userRepository.save(user);
        return user;
    }
}

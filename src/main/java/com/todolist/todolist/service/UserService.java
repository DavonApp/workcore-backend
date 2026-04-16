package com.todolist.todolist.service;

import com.todolist.todolist.model.User;
import com.todolist.todolist.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Constructor Injection
    // Spring injects BCryptPasswordEncoder bean from SecurityConfig
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
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

    // Validates credentials and returns the user if correct
    public User loginUser(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }
        // BCrypt compares raw password against stored hash
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return user;
    }

    // Updates name and/or email for the given user
    public User updateProfile(User user, String name, String email) {
        if (name != null) {
            user.setName(name);
        }

        if (email != null) {
            user.setEmail(email);
        }

        return userRepository.save(user);
    }
}

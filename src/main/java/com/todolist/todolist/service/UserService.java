package com.todolist.todolist.service;

import com.todolist.todolist.model.User;
import com.todolist.todolist.model.Task;

import com.todolist.todolist.repository.UserRepository;
import com.todolist.todolist.repository.TaskRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@Service
public class UserService {
    
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private TaskRepository taskRepository;

    // Constructor Injection
    // Spring injects BCryptPasswordEncoder bean from SecurityConfig
    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User registerUser (String email, String password){
        User existingUser = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
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
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

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

    public void changePassword(User user, String currentPassword, String newPassword) {

        if (currentPassword == null || newPassword == null  || currentPassword.isBlank() || newPassword.isBlank()) {
            throw new RuntimeException("All password fields are required.");
        }

        // Verify current password
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new RuntimeException("Current password is incorrect.");
        }

        // Minimum Length
        if (newPassword.length() < 8) {
            throw new RuntimeException("New password must be at least 8 characters.");
        }

        // Prevent same password reuse
        if (passwordEncoder.matches(newPassword, user.getPassword())) {
            throw new RuntimeException("New password must be different.");
        }

        // Save new password
        user.setPassword(passwordEncoder.encode(newPassword));
    
        // Set the update timestamp
        user.setPasswordLastChanged(LocalDateTime.now());

        // Saves the user
        userRepository.save(user);
    }

    @Transactional
    public void deleteUserbyId(int userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found"));

        // Delete all tasks belonging to this user
        List<Task> userTasks = taskRepository.findByUser(user);
        if (userTasks != null && !userTasks.isEmpty()) {
            taskRepository.deleteAll(userTasks);
        }

        // Delete the user
        userRepository.delete(user);
    }
}

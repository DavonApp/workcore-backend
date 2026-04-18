package com.todolist.todolist.controller;

import com.todolist.todolist.config.AuthHelper;
import com.todolist.todolist.model.User;
import com.todolist.todolist.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    private AuthHelper authHelper;

    // Construtor Injection
    public UserController(UserService userService){
        this.userService = userService;
    }


    // Returns current user's name + email
    @GetMapping ("/profile")
    public ResponseEntity<?> getProfile(HttpServletRequest request, HttpSession session) {
        User user = authHelper.getCurrentUser(request, session);
        if (user == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }

        return ResponseEntity.ok(Map.of(
            "name", user.getName() != null ? user.getName() : "",
            "email", user.getEmail() != null ? user.getEmail() : "",
            "lastChanged", user.getPasswordLastChanged() != null ? user.getPasswordLastChanged().toString() : "Never"
        ));
    }

    // updates name and/or email
    @PutMapping ("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> body, HttpServletRequest request, 
                                            HttpSession session) {
        User user = authHelper.getCurrentUser(request, session);
        if (user == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }

        User updated = userService.updateProfile(user, body.get("name"), body.get("email"));

        // Keeps session in sync with saved user
        session.setAttribute("user", updated);

        return ResponseEntity.ok(Map.of(
            "name",  updated.getName()  != null ? updated.getName()  : "",
            "email", updated.getEmail() != null ? updated.getEmail() : "",
            "lastChanged", user.getPasswordLastChanged() != null ? user.getPasswordLastChanged().toString() : "Never"
        ));
    }
    
    @PutMapping ("/password") 
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body, HttpServletRequest request, HttpSession session) {
        User user = authHelper.getCurrentUser(request, session);

        if (user == null) {
            return ResponseEntity.status(401). body("Not logged in");
        }

        if ("GOOGLE".equals(user.getProvider())) {
            return ResponseEntity.badRequest().body("Google account users cannot change their password here.");
        }

        try {
            userService.changePassword(
                user, 
                body.get("currentPassword"),
                body.get("newPassword")
            );

            // refresh session user
            session.setAttribute("user", user);

            return ResponseEntity.ok("Password updated successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping ("/register")
    public User registerUser (@RequestBody User userRequest){
        return userService.registerUser(userRequest.getEmail(), userRequest.getPassword());
    }

    @DeleteMapping("/account")
    public ResponseEntity<String> deleteAccount(HttpServletRequest request, HttpSession session) {
        // Look for the user object stored from login
        User user = authHelper.getCurrentUser(request, session);

        if(user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }

        try {
            // Pass the integer ID to the service
            userService.deleteUserbyId(user.getId());

            // Kill session so user is logged out
            session.invalidate();

            return ResponseEntity.ok("Account deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Could not delete account: " + e.getMessage());
        }
    }
}

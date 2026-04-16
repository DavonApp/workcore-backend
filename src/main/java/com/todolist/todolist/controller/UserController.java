package com.todolist.todolist.controller;


import com.todolist.todolist.model.User;
import com.todolist.todolist.service.UserService;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;


import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    // Construtor Injection
    public UserController(UserService userService){
        this.userService = userService;
    }


    // Returns current user's name + email
    @GetMapping ("/profile")
    public ResponseEntity<?> getProfile(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }

        return ResponseEntity.ok(Map.of(
            "name", user.getName() != null ? user.getName() : "",
            "email", user.getEmail() != null ? user.getEmail() : ""
        ));
    }

    // updates name and/or email
    @PutMapping ("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody Map<String, String> body, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }

        User updated = userService.updateProfile(user, body.get("name"), body.get("email"));

        // Keeps session in sync with saved user
        session.setAttribute("user", updated);

        return ResponseEntity.ok(Map.of(
            "name",  updated.getName()  != null ? updated.getName()  : "",
            "email", updated.getEmail() != null ? updated.getEmail() : ""
        ));
    }
    
    @PutMapping ("/password") 
    public ResponseEntity<?> changePassword(@RequestBody Map<String, String> body, HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401). body("Not logged in");
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
}

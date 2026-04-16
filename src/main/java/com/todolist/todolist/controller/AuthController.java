package com.todolist.todolist.controller;

import com.todolist.todolist.dto.AuthRequest;
import com.todolist.todolist.model.User;
import com.todolist.todolist.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register") 
        public ResponseEntity<?> register(@RequestBody AuthRequest req) {
            try {
                User user = userService.registerUser(req.getEmail(), req.getPassword());
                return ResponseEntity.ok("User registered: " + user.getEmail());
            } catch (RuntimeException e) {
                // UserService already throws if email is taken
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }
    

    @PostMapping("/login") 
    public ResponseEntity<?> login(@RequestBody AuthRequest req, HttpSession session) {
        try {
            User user = userService.loginUser(req.getEmail(), req.getPassword());

            // Store user ID in the session (so we know who is logged in)
            session.setAttribute("user", user);
            session.setAttribute("userId", user.getId());

            System.out.println("LOGIN SESSION: " + session.getId());
            System.out.println("SET USER ID: " + user.getId());

            return ResponseEntity.ok(
                java.util.Map.of("message", "Login successful", "userId", user.getId())
            );
        } catch (RuntimeException e) {
            return ResponseEntity.status(401).body("Invalid email or password");
        }
    }

    // Allows frontend to check if the user is still logged in
    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            return ResponseEntity.status(401).body("Not logged in");
        }
        return ResponseEntity.ok(java.util.Map.of("userId", userId));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) session.invalidate();
        return ResponseEntity.ok("Logged out");
    }

    
}

package com.todolist.todolist.controller;

import com.todolist.todolist.dto.AuthRequest;
import com.todolist.todolist.model.User;
import com.todolist.todolist.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
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
    public ResponseEntity<?> login(@RequestBody AuthRequest req) {
        // Endpoint placeholder
        return ResponseEntity.ok("Login endpoint is ready");
    }

    
}

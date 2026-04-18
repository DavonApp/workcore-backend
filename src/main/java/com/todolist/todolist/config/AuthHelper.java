package com.todolist.todolist.config;

import com.todolist.todolist.model.User;
import com.todolist.todolist.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthHelper {

    @Autowired 
    private JwtUtil jwtUtil;
    @Autowired 
    private UserRepository userRepository;

    public User getCurrentUser(HttpServletRequest request, HttpSession session) {

        // Try JWT first (Google users + new local logins)
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (jwtUtil.isTokenValid(token)) {
                String email = jwtUtil.extractEmail(token);
                return userRepository.findByEmail(email).orElse(null);
            }
        }

        // Fall back to session (existing local users during transition)
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId != null) {
            return userRepository.findById(userId).orElse(null);
        }

        return null;
    }
}
package com.todolist.todolist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import java.util.List;

@Configuration
public class SecurityConfig {
    
    // Spring Security password hashing
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))
            .csrf(csrf -> csrf.disable()) // Disabled for development
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/auth/**").permitAll() // Login/Register remains always open
                .anyRequest().authenticated()               // Everything else needs login
            )
            .formLogin(form -> form.disable()) // Login handled in AuthController
            .httpBasic(basic -> basic.disable()) // No browser popups
            .logout(logout -> logout
                .logoutUrl("/api/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .logoutSuccessHandler((req, res, auth) ->
                    res.setStatus(200)) // Return 200 instead of redirect on logout
            );
        return http.build();
    }

    // Allows frontend origin to send requests with cookies
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of("http://localhost:5500", "http://127.00.1:5500"));
        config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setAllowedHeaders(List.of("*"));
        config.setAllowCredentials(true); // allows cookies to be sent
        return new UrlBasedCorsConfigurationSource() {{
            registerCorsConfiguration("/**", config);
        }};
    }
}


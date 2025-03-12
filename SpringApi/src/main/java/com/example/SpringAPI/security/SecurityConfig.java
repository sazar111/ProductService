package com.example.SpringAPI.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        HttpSecurity roleDeveloper = http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/products").hasAuthority("ROLE_Developer") //  Restrict to Developers
                        .anyRequest().permitAll() // Allow all other requests
                )
                .csrf(csrf -> csrf.disable());// Disable CSRF for APIs

        return http.build();
    }
}

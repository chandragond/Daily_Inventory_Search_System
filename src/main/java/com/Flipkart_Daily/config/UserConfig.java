package com.Flipkart_Daily.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class UserConfig {

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        // Passwords should be encoded; see SecurityConfig.passwordEncoder()
        UserDetails admin = User.withUsername("admin")
                .password("{bcrypt}$2a$10$8a9e9D6uGd3o7m8u7J8WlOe5s3o3cR7b3wzv2JzqC0nE0kV8d6JzS") // "password"
                .roles("ADMIN")
                .build();

        UserDetails user = User.withUsername("user")
                .password("{bcrypt}$2a$10$8a9e9D6uGd3o7m8u7J8WlOe5s3o3cR7b3wzv2JzqC0nE0kV8d6JzS") // "password"
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(admin, user);
    }
}

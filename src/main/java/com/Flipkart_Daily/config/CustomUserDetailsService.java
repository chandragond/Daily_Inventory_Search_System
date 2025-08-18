package com.Flipkart_Daily.config;

import org.springframework.context.annotation.Primary;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Map<String, UserDetails> USERS = Map.of(
            "dbAdmin", User.withUsername("dbAdmin")
                    .password("{bcrypt}$2a$10$8a9e9D6uGd3o7m8u7J8WlOe5s3o3cR7b3wzv2JzqC0nE0kV8d6JzS") // "password"
                    .authorities(List.of(new SimpleGrantedAuthority("ROLE_ADMIN")))
                    .build()
    );

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetails u = USERS.get(username);
        if (u == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }
        return u;
    }
}

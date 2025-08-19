package com.Flipkart_Daily.Services;

import com.Flipkart_Daily.Entities.User;
import com.Flipkart_Daily.Repositories.UserRepository;
import com.Flipkart_Daily.config.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private JwtUtil jwt;

    public User register(String username, String mobile, String password) {
        User u = new User();
        u.setUsername(username);
        u.setMobile(mobile);
        u.setPassword(encoder.encode(password));
        return repo.save(u);
    }

    public Optional<String> loginWithUsername(String username, String password) {
        return repo.findByUsername(username)
                .filter(u -> encoder.matches(password, u.getPassword()))
                .map(u -> jwt.generateToken(username, Map.of("mobile", u.getMobile(), "roles", u.getRoles())));
    }

    public Optional<String> loginWithMobile(String mobile, String password) {
        return repo.findByMobile(mobile)
                .filter(u -> encoder.matches(password, u.getPassword()))
                .map(u -> jwt.generateToken(mobile, Map.of("username", u.getUsername(), "roles", u.getRoles())));
    }

}

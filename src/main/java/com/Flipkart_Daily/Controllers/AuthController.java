package com.Flipkart_Daily.Controllers;

import com.Flipkart_Daily.Entities.User;
import com.Flipkart_Daily.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public String register(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String mobile = request.get("mobile");
        String password = request.get("password");
        User user = userService.register(username, mobile, password);
        return "User registered with ID: " + user.getId();
    }

    @PostMapping("/login/username")
    public String loginWithUsername(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        Optional<String> token = userService.loginWithUsername(username, password);
        return token.orElse("Invalid credentials");
    }

    @PostMapping("/login/mobile")
    public String loginWithMobile(@RequestBody Map<String, String> request) {
        String mobile = request.get("mobile");
        String password = request.get("password");
        Optional<String> token = userService.loginWithMobile(mobile, password);
        return token.orElse("Invalid credentials");
    }
}

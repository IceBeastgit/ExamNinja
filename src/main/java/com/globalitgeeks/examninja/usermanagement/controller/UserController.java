package com.globalitgeeks.examninja.usermanagement.controller;

import com.globalitgeeks.examninja.usermanagement.dto.UserRequest;
import com.globalitgeeks.examninja.usermanagement.model.User;
import com.globalitgeeks.examninja.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Registration Endpoint
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest) {
        try {
            User registeredUser = userService.register(userRequest);
            return ResponseEntity.status(201).body(registeredUser);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Login Endpoint


    // Change Password Endpoint

}

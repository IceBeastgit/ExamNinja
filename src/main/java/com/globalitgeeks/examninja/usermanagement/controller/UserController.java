package com.globalitgeeks.examninja.usermanagement.controller;

import com.globalitgeeks.examninja.usermanagement.dto.ApiResponse;
import com.globalitgeeks.examninja.usermanagement.dto.UserRequest;
import com.globalitgeeks.examninja.usermanagement.model.User;
import com.globalitgeeks.examninja.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Registration Endpoint
    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody UserRequest userRequest) {
        userService.register(userRequest);
        ApiResponse response = new ApiResponse("success", "User Registered Successfully!");
        return ResponseEntity.status(201).body(response);
    }

    // Login Endpoint
     @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest request) {
            User user = userService.login(request);
            ApiResponse response = new ApiResponse("success", "User Logged in Successfully!");
            return ResponseEntity.ok(response);
    }

    // Change Password Endpoint
    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@RequestBody UserRequest request) {
            User updatedUser = userService.changePassword(request);
            ApiResponse response = new ApiResponse("success", "Password changed successfully");
            return ResponseEntity.ok(response);
    }
}

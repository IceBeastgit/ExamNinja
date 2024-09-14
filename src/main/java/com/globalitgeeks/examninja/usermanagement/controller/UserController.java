package com.globalitgeeks.examninja.usermanagement.controller;

import com.globalitgeeks.examninja.usermanagement.dto.*;
import com.globalitgeeks.examninja.usermanagement.exception.ValidationException;
import com.globalitgeeks.examninja.usermanagement.model.User;
import com.globalitgeeks.examninja.usermanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.regex.Pattern;

@RestController
@RequestMapping("/api/v1/users")
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
        try {
            User updatedUser = userService.changePassword(request);
            ApiResponse response = new ApiResponse("success", "Password changed successfully");
            return ResponseEntity.ok(response);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(404).body(new ErrorResponse(e.getMessage()));
        }
    }
}

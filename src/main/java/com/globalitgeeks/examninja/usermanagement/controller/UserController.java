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
    public ResponseEntity<?> register(@RequestBody UserRequest userRequest) {
        try {
            User registeredUser = userService.register(userRequest);
            return ResponseEntity.status(201).body(registeredUser);
        } catch (ValidationException e) {
            throw e;
        } catch (Exception e) {
            return ResponseEntity.status(400).body(e.getMessage());
        }
    }

    // Login Endpoint
     @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest request) {
        User user = userService.login(request);
         ApiResponse response = new ApiResponse("success", "logged in successfully");

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

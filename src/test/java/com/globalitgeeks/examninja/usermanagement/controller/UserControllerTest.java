package com.globalitgeeks.examninja.usermanagement.controller;

import com.globalitgeeks.examninja.usermanagement.controller.UserController;
import com.globalitgeeks.examninja.usermanagement.dto.ApiResponse;
import com.globalitgeeks.examninja.usermanagement.dto.UserRegisterRequest;
import com.globalitgeeks.examninja.usermanagement.dto.UserRequest;
import com.globalitgeeks.examninja.usermanagement.model.User;
import com.globalitgeeks.examninja.usermanagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegister() {
        // Arrange
        UserRegisterRequest registerRequest = new UserRegisterRequest();
        registerRequest.setFirstName("John");
        registerRequest.setLastName("Doe");
        registerRequest.setEmail("john.doe@example.com");
        registerRequest.setPassword("password@123");

        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password@123");

        when(userService.register(any(UserRegisterRequest.class))).thenReturn(user);

        // Act
        ResponseEntity<ApiResponse> responseEntity = userController.register(registerRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals("User Registered Successfully!", responseEntity.getBody().getMessage());
        verify(userService, times(1)).register(any(UserRegisterRequest.class));
    }
    // Test for User Login
    @Test
    void testLogin() {
        // Arrange
        UserRequest loginRequest = new UserRequest();
        loginRequest.setEmail("john.doe@example.com");
        loginRequest.setPassword("password@123");

        User mockUser = new User();
        mockUser.setEmail("john.doe@example.com");
        mockUser.setPassword("password@123");

        when(userService.login(any(UserRequest.class))).thenReturn(mockUser);

        // Act
        ResponseEntity<?> response = userController.login(loginRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User Logged in Successfully!", ((ApiResponse) response.getBody()).getMessage());
        verify(userService, times(1)).login(any(UserRequest.class));
    }

    // Test for Change Password
    @Test
    void testChangePassword() {
        // Arrange
        UserRequest changePasswordRequest = new UserRequest();
        changePasswordRequest.setEmail("john.doe@example.com");
        changePasswordRequest.setPassword("newpassword@123");

        User mockUser = new User();
        mockUser.setEmail("john.doe@example.com");
        mockUser.setPassword("newpassword@123");

        when(userService.changePassword(any(UserRequest.class))).thenReturn(mockUser);

        // Act
        ResponseEntity<?> response = userController.changePassword(changePasswordRequest);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Password changed successfully", ((ApiResponse) response.getBody()).getMessage());
        verify(userService, times(1)).changePassword(any(UserRequest.class));
    }
}

package com.globalitgeeks.examninja.usermanagement.service;

import com.globalitgeeks.examninja.usermanagement.dto.UserRegisterRequest;
import com.globalitgeeks.examninja.usermanagement.dto.UserRequest;
import com.globalitgeeks.examninja.usermanagement.exception.InvalidPasswordException;
import com.globalitgeeks.examninja.usermanagement.exception.UserNotFoundException;
import com.globalitgeeks.examninja.usermanagement.model.User;
import com.globalitgeeks.examninja.usermanagement.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Test case for registering a user
    @Test
    void testRegisterSuccess() {
        // Given
        UserRegisterRequest request = new UserRegisterRequest();
        request.setFirstName("John");
        request.setLastName("Doe");
        request.setEmail("john@example.com");
        request.setPassword("password123");

        User user = new User();
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        User savedUser = userService.register(request);

        // Then
        assertNotNull(savedUser);
        verify(userRepository, times(1)).save(any(User.class));
    }

    // Test case for successful login
    @Test
    void testLoginSuccess() {
        // Given
        UserRequest request = new UserRequest();
        request.setEmail("john@example.com");
        request.setPassword("password123");

        User user = new User();
        user.setEmail("john@example.com");
        user.setPassword("password123");

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        // When
        User loggedInUser = userService.login(request);

        // Then
        assertNotNull(loggedInUser);
        assertEquals("john@example.com", loggedInUser.getEmail());
        verify(userRepository, times(1)).findByEmail("john@example.com");
    }

    // Test case for login failure (Invalid Password)
    @Test
    void testLoginInvalidPassword() {
        // Given
        UserRequest request = new UserRequest();
        request.setEmail("john@example.com");
        request.setPassword("wrongpassword");

        User user = new User();
        user.setEmail("john@example.com");
        user.setPassword("password123");

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));

        // When & Then
        assertThrows(InvalidPasswordException.class, () -> userService.login(request));
        verify(userRepository, times(1)).findByEmail("john@example.com");
    }

    // Test case for login failure (User Not Found)
    @Test
    void testLoginUserNotFound() {
        // Given
        UserRequest request = new UserRequest();
        request.setEmail("john@example.com");
        request.setPassword("password123");

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.login(request));
        verify(userRepository, times(1)).findByEmail("john@example.com");
    }

    // Test case for changing password successfully
    @Test
    void testChangePasswordSuccess() {
        // Given
        UserRequest request = new UserRequest();
        request.setEmail("john@example.com");
        request.setPassword("newpassword");

        User user = new User();
        user.setEmail("john@example.com");
        user.setPassword("oldpassword");

        when(userRepository.findByEmail("john@example.com")).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // When
        User updatedUser = userService.changePassword(request);

        // Then
        assertNotNull(updatedUser);
        assertEquals("newpassword", updatedUser.getPassword());
        verify(userRepository, times(1)).findByEmail("john@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    // Test case for changing password (User Not Found)
    @Test
    void testChangePasswordUserNotFound() {
        // Given
        UserRequest request = new UserRequest();
        request.setEmail("nonexistent@example.com");
        request.setPassword("newpassword");

        when(userRepository.findByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        // When & Then
        assertThrows(UserNotFoundException.class, () -> userService.changePassword(request));
        verify(userRepository, times(1)).findByEmail("nonexistent@example.com");
    }
}

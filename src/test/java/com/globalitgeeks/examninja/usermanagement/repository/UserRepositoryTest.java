package com.globalitgeeks.examninja.usermanagement.repository;

import com.globalitgeeks.examninja.usermanagement.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;  // Mocking the repository

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks
        user = new User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
    }

    @Test
    void testSaveUser_success() {
        // Arrange: Set up the behavior for the mock repository
        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act: Call the save method
        User savedUser = userRepository.save(user);

        // Assert: Verify the result
        assertNotNull(savedUser);
        assertEquals("John", savedUser.getFirstName());
        assertEquals("Doe", savedUser.getLastName());
        assertEquals("john.doe@example.com", savedUser.getEmail());

        // Verify the repository's save method was called
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testFindByEmail_success() {
        // Arrange: Mock the repository's findByEmail method
        when(userRepository.findByEmail("john.doe@example.com")).thenReturn(Optional.of(user));

        // Act: Call the findByEmail method
        Optional<User> foundUser = userRepository.findByEmail("john.doe@example.com");

        // Assert: Verify the result
        assertTrue(foundUser.isPresent());
        assertEquals("John", foundUser.get().getFirstName());

        // Verify the repository's findByEmail method was called
        verify(userRepository, times(1)).findByEmail("john.doe@example.com");
    }

    @Test
    void testFindByEmail_userNotFound() {
        // Arrange: Mock the repository to return an empty result
        when(userRepository.findByEmail("unknown@example.com")).thenReturn(Optional.empty());

        // Act: Call the findByEmail method with a non-existent email
        Optional<User> foundUser = userRepository.findByEmail("unknown@example.com");

        // Assert: Verify the result
        assertFalse(foundUser.isPresent());

        // Verify the repository's findByEmail method was called
        verify(userRepository, times(1)).findByEmail("unknown@example.com");
    }
}

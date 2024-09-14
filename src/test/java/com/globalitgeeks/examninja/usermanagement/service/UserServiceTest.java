package com.globalitgeeks.examninja.usermanagement.service;
import com.globalitgeeks.examninja.usermanagement.exception.InvalidPasswordException;
import com.globalitgeeks.examninja.usermanagement.exception.UserNotFoundException;
import com.globalitgeeks.examninja.usermanagement.exception.ValidationException;
import com.globalitgeeks.examninja.usermanagement.model.User;
import com.globalitgeeks.examninja.usermanagement.repository.UserRepository;
import com.globalitgeeks.examninja.usermanagement.dto.UserRequest;
import com.globalitgeeks.examninja.usermanagement.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.stereotype.Service;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void shouldRegisterUserSuccessfully() {
        // Arrange
        UserRequest userRequest = new UserRequest("John", "Doe", "john.doe@example.com", "Password1!");
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("Password1!");

        when(userRepository.save(any(User.class))).thenReturn(user);

        // Act
        User registeredUser = userService.register(userRequest);

        // Assert
        assertThat(registeredUser).isNotNull();
        assertThat(registeredUser.getEmail()).isEqualTo("john.doe@example.com");
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void shouldThrowValidationExceptionWhenFirstNameIsNull() {
        // Arrange
        UserRequest userRequest = new UserRequest(null, "Doe", "john.doe@example.com", "Password1!");

        // Act
        Throwable thrown = catchThrowable(() -> userService.register(userRequest));

        // Assert
        assertThat(thrown).isInstanceOf(ValidationException.class)
                .hasMessage("First name is required.");
        verify(userRepository, never()).save(any(User.class));
    }
    @Test
    public void shouldThrowValidationExceptionWhenLastNameIsNull() {
        // Arrange
        UserRequest userRequest = new UserRequest("John", null, "john.doe@example.com", "Password1!");

        // Act
        Throwable thrown = catchThrowable(() -> userService.register(userRequest));

        // Assert
        assertThat(thrown).isInstanceOf(ValidationException.class)
                .hasMessage("Last name is required.");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void shouldThrowValidationExceptionWhenEmailIsInvalid() {
        // Arrange
        UserRequest userRequest = new UserRequest("John", "Doe", "invalid-email", "Password1!");

        // Act
        Throwable thrown = catchThrowable(() -> userService.register(userRequest));

        // Assert
        assertThat(thrown).isInstanceOf(ValidationException.class)
                .hasMessage("Invalid email format.");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void shouldThrowValidationExceptionWhenPasswordIsInvalid() {
        // Arrange
        UserRequest userRequest = new UserRequest("John", "Doe", "john.doe@example.com", "short");

        // Act
        Throwable thrown = catchThrowable(() -> userService.register(userRequest));

        // Assert
        assertThat(thrown).isInstanceOf(ValidationException.class)
                .hasMessage("Password must be at least 8 and at most 15 characters long and contain 1 special character and 1 numbers.");
        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    public void shouldLoginSuccessfully() {
        // Arrange
        UserRequest request = new UserRequest("John", "Doe", "john.doe@example.com", "Password1!");
        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("Password1!");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

        // Act
        User loggedInUser = userService.login(request);

        // Assert
        assertThat(loggedInUser).isNotNull();
        assertThat(loggedInUser.getEmail()).isEqualTo("john.doe@example.com");
        verify(userRepository, times(1)).findByEmail(request.getEmail());
    }

    @Test
    public void shouldThrowUserNotFoundExceptionWhenUserDoesNotExist() {
        // Arrange
        UserRequest request = new UserRequest("John", "Doe", "unknown@example.com", "Password1!");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.empty());

        // Act
        Throwable thrown = catchThrowable(() -> userService.login(request));

        // Assert
        assertThat(thrown).isInstanceOf(UserNotFoundException.class)
                .hasMessage("User not found");
        verify(userRepository, times(1)).findByEmail(request.getEmail());
    }

    @Test
    public void shouldThrowInvalidPasswordExceptionWhenPasswordIsIncorrect() {
        // Arrange
        UserRequest request = new UserRequest("John", "Doe", "john.doe@example.com", "WrongPassword");
        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("Password1!");

        when(userRepository.findByEmail(request.getEmail())).thenReturn(Optional.of(user));

        // Act
        Throwable thrown = catchThrowable(() -> userService.login(request));

        // Assert
        assertThat(thrown).isInstanceOf(InvalidPasswordException.class)
                .hasMessage("Incorrect password");
        verify(userRepository, times(1)).findByEmail(request.getEmail());
    }

    @Test
    public void shouldThrowValidationExceptionWhenEmailIsInvalidInLogin() {
        // Arrange
        UserRequest request = new UserRequest("John", "Doe", "invalid-email", "Password1!");

        // Act
        Throwable thrown = catchThrowable(() -> userService.login(request));

        // Assert
        assertThat(thrown).isInstanceOf(ValidationException.class)
                .hasMessage("Invalid email format.");
        verify(userRepository, never()).findByEmail(anyString());


    }
    @Test
    void changePassword() {
    }
}
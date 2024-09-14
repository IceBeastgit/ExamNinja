package com.globalitgeeks.examninja.usermanagement.service;

import com.globalitgeeks.examninja.usermanagement.dto.UserRequest;
import com.globalitgeeks.examninja.usermanagement.exception.InvalidPasswordException;
import com.globalitgeeks.examninja.usermanagement.exception.UserNotFoundException;
import com.globalitgeeks.examninja.usermanagement.exception.ValidationException;
import com.globalitgeeks.examninja.usermanagement.model.User;
import com.globalitgeeks.examninja.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register a new user
    public User register(UserRequest userRequest) {
        validateUserRequest(userRequest);
        User user = new User();
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
       return userRepository.save(user);
    }
    // Validate user request fields
    private void validateUserRequest(UserRequest userRequest) {
        if (userRequest.getFirstName() == null || userRequest.getFirstName().trim().isEmpty()) {
            throw new ValidationException("First name is required.");
        }
        if (userRequest.getLastName() == null || userRequest.getLastName().trim().isEmpty()) {
            throw new ValidationException("Last name is required.");
        }
        if (userRequest.getEmail() == null || !isValidEmail(userRequest.getEmail())) {
            throw new ValidationException("Invalid email format.");
        }
        if (userRequest.getPassword() == null || !isValidPassword(userRequest.getPassword())) {
            throw new ValidationException("Password must be at least 8 and at most 15 characters long and contain 1 special character and 1 numbers.");
        }
    }
    // Check if email format is valid
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Check if password meets the requirements
    private boolean isValidPassword(String password) {
        return password.length() >= 8 && password.length()<=15 &&
                password.chars().anyMatch(Character::isDigit) &&
                password.chars().anyMatch(ch -> "!@#$%^&*()_+{}|:<>?[];',./`~".indexOf(ch) >= 0);
    }

    // Login user
    public User login(UserRequest request){
        validateLoginRequest(request);
        Optional<User> userOptional = userRepository.findByEmail(request.getEmail());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            if (user.getPassword().equals(request.getPassword())) {
                return user;
            } else {
                throw new InvalidPasswordException("Incorrect password");
            }
        } else {
            throw new UserNotFoundException("User not found");
        }
    }

    private void validateLoginRequest(UserRequest request) {
        if (request.getEmail() == null || !isValidEmail(request.getEmail())) {
            throw new ValidationException("Invalid email format.");
        }

    }


    // Change user password
    public User changePassword(UserRequest request) throws UserNotFoundException {
        validateEmailPasswordRequest(request);
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isPresent()) {
            User userPass = userOpt.get();
            userPass.setPassword(request.getPassword());
            return userRepository.save(userPass);
        }
        else throw new UserNotFoundException("User not found with the provided email.");


    }

    //validate change password functionality
    private void validateChangePasswordRequest(UserRequest request) {
        if (request.getEmail() == null || !isValidEmail(request.getEmail())) {
            throw new ValidationException("Invalid email format.");
        }
        if (request.getPassword()== null || !isValidPassword(request.getPassword())) {
            throw new ValidationException("Password must be at least 8 characters long and contain 1 special character and 1 numbers.");
        }
    }



}

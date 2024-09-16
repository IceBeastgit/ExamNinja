package com.globalitgeeks.examninja.usermanagement.service;

import com.globalitgeeks.examninja.usermanagement.dto.UserRegisterRequest;
import com.globalitgeeks.examninja.usermanagement.dto.UserRequest;
import com.globalitgeeks.examninja.usermanagement.exception.InvalidPasswordException;
import com.globalitgeeks.examninja.usermanagement.exception.UserNotFoundException;
import com.globalitgeeks.examninja.usermanagement.model.User;
import com.globalitgeeks.examninja.usermanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register a new user
    public User register(UserRegisterRequest registerRequest) {
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(registerRequest.getPassword());
        return userRepository.save(user);
    }

    // Login user
    public User login(UserRequest request) {
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

    // Change user password
    public User changePassword(UserRequest request) throws UserNotFoundException {
        Optional<User> userOpt = userRepository.findByEmail(request.getEmail());
        if (userOpt.isPresent()) {
            User userPass = userOpt.get();
            userPass.setPassword(request.getPassword());
            return userRepository.save(userPass);
        } else {
            throw new UserNotFoundException("User not found with the provided email.");
        }
    }
}


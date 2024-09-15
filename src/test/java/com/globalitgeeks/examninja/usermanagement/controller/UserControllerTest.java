package com.globalitgeeks.examninja.usermanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.globalitgeeks.examninja.usermanagement.dto.UserRequest;
import com.globalitgeeks.examninja.usermanagement.exception.UserNotFoundException;
import com.globalitgeeks.examninja.usermanagement.exception.ValidationException;
import com.globalitgeeks.examninja.usermanagement.model.User;
import com.globalitgeeks.examninja.usermanagement.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

    @WebMvcTest(UserController.class)
    public class UserControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private UserService userService;

        @Autowired
        private ObjectMapper objectMapper; // For converting objects to JSON

        // Test case for successful registration
        @Test
        public void shouldReturn201WhenUserRegisteredSuccessfully() throws Exception {
            UserRequest userRequest = new UserRequest("John", "Doe", "john@example.com", "password");

            mockMvc.perform(post("/api/users/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(content().json("{\"status\":\"success\",\"message\":\"User Registered Successfully!\"}"));
        }

        // Test case for ValidationException
        @Test
        public void shouldReturn400WhenValidationExceptionOccurs() throws Exception {
            UserRequest userRequest = new UserRequest("John", "Doe", "john@example.com", "password");

            // Simulate ValidationException being thrown by the service layer
            doThrow(new ValidationException("Invalid email")).when(userService).register(any(UserRequest.class));

            mockMvc.perform(post("/api/users/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userRequest)))
                    .andExpect(status().isBadRequest())
                    .andExpect(content().json("{\"error\":\"Invalid email\"}"));  // Expecting 'error' instead of 'message'
        }


        // Test case for general Exception
        @Test
        public void shouldReturn500WhenGeneralExceptionOccurs() throws Exception {
            UserRequest userRequest = new UserRequest("John", "Doe", "john@example.com", "password");

            // Simulate a general exception being thrown by the service layer
            doThrow(new RuntimeException("Unexpected error")).when(userService).register(any(UserRequest.class));

            mockMvc.perform(post("/api/users/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userRequest)))
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().json("{\"error\":\"Unexpected error\"}")); // Adjust this based on your actual ErrorResponse format
        }

        @Test
        public void shouldReturn200WhenUserLoggedInSuccessfully() throws Exception {
            UserRequest userRequest = new UserRequest(null,null,"john@example.com", "password@1");

            mockMvc.perform(post("/api/users/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userRequest)))
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\"status\":\"success\",\"message\":\"User Logged in Successfully!\"}"));
        }

        // Test case for successful password change
        @Test
        public void shouldReturn200WhenPasswordChangedSuccessfully() throws Exception {
            UserRequest request = new UserRequest("John", "Doe", "john@example.com", "newPassword");

            User updatedUser = new User();  // Mock updated user object
            when(userService.changePassword(any(UserRequest.class))).thenReturn(updatedUser);

            mockMvc.perform(put("/api/users/change-password")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isOk())
                    .andExpect(content().json("{\"status\":\"success\",\"message\":\"Password changed successfully\"}"));
        }

        // Test case for UserNotFoundException
        @Test
        public void shouldReturn404WhenUserNotFoundExceptionOccurs() throws Exception {
            UserRequest request = new UserRequest("John", "Doe", "john@example.com", "newPassword");

            // Simulate UserNotFoundException being thrown by the service layer
            doThrow(new UserNotFoundException("User not found")).when(userService).changePassword(any(UserRequest.class));

            mockMvc.perform(put("/api/users/change-password")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isNotFound())
                    .andExpect(content().json("{\"error\":\"User not found\"}"));
        }

        // Test case for general Exception
        @Test
        public void shouldReturn500WhenGeneralExceptionOccursDuringPasswordChange() throws Exception {
            UserRequest request = new UserRequest("John", "Doe", "john@example.com", "newPassword");

            // Simulate general exception being thrown by the service layer
            doThrow(new RuntimeException("Unexpected error")).when(userService).changePassword(any(UserRequest.class));

            mockMvc.perform(put("/api/users/change-password")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().json("{\"error\":\"Unexpected error\"}"));
        }

}
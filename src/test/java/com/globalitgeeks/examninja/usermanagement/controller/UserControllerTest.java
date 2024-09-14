package com.globalitgeeks.examninja.usermanagement.controller;
import com.globalitgeeks.examninja.usermanagement.controller.UserController;
import com.globalitgeeks.examninja.usermanagement.dto.UserRequest;
import com.globalitgeeks.examninja.usermanagement.exception.ValidationException;
import com.globalitgeeks.examninja.usermanagement.service.UserService;
import com.globalitgeeks.examninja.usermanagement.dto.ApiResponse;
import com.globalitgeeks.examninja.usermanagement.dto.ErrorResponse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import com.fasterxml.jackson.databind.ObjectMapper;

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

            mockMvc.perform(post("/api/v1/users/register")
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

            mockMvc.perform(post("/api/v1/users/register")
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

            mockMvc.perform(post("/api/v1/users/register")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(userRequest)))
                    .andExpect(status().isInternalServerError())
                    .andExpect(content().json("{\"error\":\"Unexpected error\"}")); // Adjust this based on your actual ErrorResponse format
        }



    @Test
    void login() {
    }

    @Test
    void changePassword() {
    }
}
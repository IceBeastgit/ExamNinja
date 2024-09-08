package com.globalitgeeks.examninja.usermanagement.dto;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    private String email;
    private String newPassword;
}

package com.globalitgeeks.examninja.usermanagement.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ChangePasswordRequest {
    private String email;
    private String newPassword;

}

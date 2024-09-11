package com.globalitgeeks.examninja.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class ErrorResponse {
    private String error;
    public ErrorResponse(String error){
        this.error=error;
    }
}

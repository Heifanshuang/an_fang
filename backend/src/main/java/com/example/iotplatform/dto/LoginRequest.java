package com.example.iotplatform.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String account;
    private String password;
    private Boolean isRememberMe;
}
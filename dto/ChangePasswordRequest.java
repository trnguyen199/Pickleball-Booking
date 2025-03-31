package com.userapi.dto;

@Data
public class ChangePasswordRequest {
    private String oldPassword;
    private String  password;
}
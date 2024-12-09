package com.example.user_service.dto;

import lombok.Data;

import java.util.Date;

@Data
public class UserDto {

    private String userId;

    private String email;
    private String name;
    private String pwd;
    private Date createdAt;

    private String encryptedPwd;
}

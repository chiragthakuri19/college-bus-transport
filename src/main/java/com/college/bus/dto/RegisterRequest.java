package com.college.bus.dto;

import com.college.bus.model.User.UserRole;
import lombok.Data;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private String fullName;
    private String email;
    private UserRole role;
} 
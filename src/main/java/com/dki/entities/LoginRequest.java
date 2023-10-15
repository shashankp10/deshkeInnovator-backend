package com.dki.entities;

import lombok.Data;

@Data
public class LoginRequest {
	private String email;
    private String password;
    private UserTypeEnum userType;
}

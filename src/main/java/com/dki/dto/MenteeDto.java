package com.dki.dto;

import lombok.Data;

@Data
public class MenteeDto {
	private int id;
	private String name;
	private String email;
	private String password;
	private long phone;
	private String address;
	private String salt;
}

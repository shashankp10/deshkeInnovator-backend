package com.dki.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dki.dto.MenteeDto;
import com.dki.dto.MentorDto;
import com.dki.entities.UserTypeEnum;
import com.dki.security.PasswordEncryptor;

@Service
public class AuthUserService {
	@Autowired
    private MenteeService menteeService;

    @Autowired
    private MentorService mentorService;

    public boolean authenticateUser(UserTypeEnum userType, String email, String password) {
    	 if (userType == UserTypeEnum.MENTEE) {
    		 MenteeDto menteeDto = menteeService.getMenteeByEmail(email);
             return menteeDto != null && PasswordEncryptor.verifyPassword(password,menteeDto.getSalt(),menteeDto.getPassword());
         } else if (userType == UserTypeEnum.MENTOR) {
        	 MentorDto mentorDto = mentorService.getMentorByEmail(email);
             return mentorDto != null && PasswordEncryptor.verifyPassword(password, mentorDto.getSalt(),mentorDto.getPassword());
         }

        return false;
    }
}

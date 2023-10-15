package com.dki.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dki.dto.MenteeDto;
import com.dki.dto.MentorDto;
import com.dki.entities.LoginRequest;
import com.dki.entities.UserTypeEnum;
import com.dki.services.AuthUserService;
import com.dki.services.MenteeService;
import com.dki.services.MentorService;

@RestController
@RequestMapping("/deshkeinnovators")
public class UserController {

	@Autowired
	private MentorService mentorService;
	
	@Autowired
	private MenteeService menteeService;
	
	@Autowired
    private AuthUserService authUserService;
	
	@GetMapping("/register/mentor")
	public ResponseEntity<String> registerAsMentor(@RequestBody MentorDto mentorDto) {
	    try {
	        MentorDto createdMentor = mentorService.createMentor(mentorDto);
	        return ResponseEntity.ok("Mentor registered successfully with Name: " + createdMentor.getName());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to register : " + e.getMessage());
	    }
	}
	
	@GetMapping("/register/mentee")
	public ResponseEntity<String> registerAsMentee(@RequestBody MenteeDto menteeDto) {
	    try {
	        MenteeDto createdMentee = menteeService.createMentee(menteeDto);
	        return ResponseEntity.ok("Mentee registered successfully with Name: " + createdMentee.getName());
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to register : " + e.getMessage());
	    }
	}
	
	@PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        try {
            UserTypeEnum userType = loginRequest.getUserType();
            if (authUserService.authenticateUser(userType, loginRequest.getEmail(), loginRequest.getPassword())) {
            	return ResponseEntity.ok("Login successful");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid Credentails");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
	
				/*	 Mentor related APIs   */
	@PutMapping("/mentor/{mentorId}")
	public ResponseEntity<MentorDto> updateUser(@RequestBody MentorDto mentorDto, @PathVariable("mentorId")int  uid){
//		if(userDto.getBranch()==null || userDto.getEnrollment()==null || userDto.getPassword()==null
//				|| userDto.getName()==null || userDto.getSemester()==0) {
//	        // return message for null values
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//	    }
		MentorDto updateUser = this.mentorService.updateMentor(mentorDto, uid);
		System.out.println("Detail has been updated succesfully!!");
		return ResponseEntity.ok(updateUser);
	}
	@GetMapping("/mentor")
	public ResponseEntity<List<MentorDto>> getAllMentor(){
		return ResponseEntity.ok(this.mentorService.getAllMentor());
	}
	@GetMapping("/mentor/{spec}")
	public ResponseEntity<List<MentorDto>> getMentoBySpecification(@PathVariable String spec){
		List<MentorDto> mentorDto = mentorService.getMentorById(spec);

	    if (mentorDto != null) {
	        return ResponseEntity.ok(mentorDto);
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}
	
	@DeleteMapping("/mentor/{mentorId}")
	public ResponseEntity<String> deleteUser(@PathVariable("mentorId") int mentorId){
		try {
	        MentorDto mentorDto = this.mentorService.getMentorById(mentorId);
	        if (mentorDto != null) {
	            this.mentorService.deleteMentor(mentorId);
	            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception ex) {
	        // Handle other exceptions, e.g., log the exception or return a server error status
	        ex.printStackTrace();
	        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}
							/*	 Mentee related APIs   */
	
	@PutMapping("/mentee/{menteeId}")
	public ResponseEntity<MenteeDto> updateMentee(@RequestBody MenteeDto menteeDto, @PathVariable("menteeId")int  uid){
//		if(userDto.getBranch()==null || userDto.getEnrollment()==null || userDto.getPassword()==null
//				|| userDto.getName()==null || userDto.getSemester()==0) {
//	        // return message for null values
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//	    }
		MenteeDto updateUser = this.menteeService.updateMentee(menteeDto, uid);
		System.out.println("Detail has been updated succesfully!!");
		return ResponseEntity.ok(updateUser);
	}
	@GetMapping("/mentee")
	public ResponseEntity<List<MenteeDto>> getAllMentee(){
		return ResponseEntity.ok(this.menteeService.getAllMentee());
	}
//	@GetMapping("/mentee/{spec}")
//	public ResponseEntity<List<MentorDto>> getMentoBySpecification(@PathVariable String spec){
//		List<MentorDto> mentorDto = mentorService.getMentorById(spec);
//
//	    if (mentorDto != null) {
//	        return ResponseEntity.ok(mentorDto);
//	    } else {
//	        return ResponseEntity.notFound().build();
//	    }
//	}
	
	@DeleteMapping("/mentee/{menteeId}")
	public ResponseEntity<String> deleteMentee(@PathVariable("menteeId") int menteeId){
		try {
	        MenteeDto menteeDto = this.menteeService.getMenteeById(menteeId);
	        if (menteeDto != null) {
	            this.menteeService.deleteMentee(menteeId);
	            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
	        }
	    } catch (Exception ex) {
	        // Handle other exceptions, e.g., log the exception or return a server error status
	        ex.printStackTrace();
	        return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}

}

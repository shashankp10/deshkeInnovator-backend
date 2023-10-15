package com.dki.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dki.Repo.MenteeRepo;
import com.dki.dto.MenteeDto;
import com.dki.entities.Mentee;
import com.dki.security.PasswordEncryptor;
import com.dki.services.MenteeService;

@Service
public class MenteeImpl implements MenteeService{

	@Autowired
	private MenteeRepo menteeRepo;
	
	@Override
	public MenteeDto createMentee(MenteeDto menteeDto) {
		Mentee mentee = this.dtoToUser(menteeDto);
		Mentee savedMentee = this.menteeRepo.save(mentee);
		return this.userToDto(savedMentee);
	}

	@Override
	public MenteeDto updateMentee(MenteeDto menteeDto, int menteeId) {
		try {
	        Optional<Mentee> optionalUser = this.menteeRepo.findById(menteeId);
	        if (optionalUser.isPresent()) {
	            Mentee mentee = optionalUser.get();
	            mentee.setAddress(menteeDto.getAddress());
	            mentee.setName(menteeDto.getName());
	            mentee.setEmail(menteeDto.getEmail());
	            String salt = PasswordEncryptor.generateSalt();
	    	    String encryptedPassword = PasswordEncryptor.encryptPassword(menteeDto.getPassword(), salt);
	    	    mentee.setPassword(encryptedPassword);
	            mentee.setPhone(menteeDto.getPhone());
	    		
	            Mentee updatedUser = this.menteeRepo.save(mentee);
	            MenteeDto userDto1 = this.userToDto(updatedUser);
	    		return userDto1;
	        } else {
	            System.err.println("Mentor not found with ID: " + menteeId);
	            return null;
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return null;
	    }
	}

	@Override
	public List<MenteeDto> getAllMentee() {
		List<Mentee> mentee = this.menteeRepo.findAll();
		List<MenteeDto> menteeDtos = mentee.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());
		return menteeDtos;
	}

	@Override
	public void deleteMentee(int menteeId) {
		try {
	        Optional<Mentee> optionalUser = this.menteeRepo.findById(menteeId);
	        if (optionalUser.isPresent()) {
	            Mentee user = optionalUser.get();
	            this.menteeRepo.delete(user);
	        } else {
	            System.err.println("Mentor not found with ID: " + menteeId);
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }	
	}

	@Override
	public MenteeDto getMenteeById(int menteeId) {
		try {
	        Optional<Mentee> optionalMentee = menteeRepo.findById(menteeId);

	        if (optionalMentee.isPresent()) {
	            Mentee mentee = optionalMentee.get();
	            return userToDto(mentee);
	        } else {
	            System.err.println("Mentor not found with ID: " + menteeId);
	            return null;
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return null;
	    }
	}
	
	private Mentee dtoToUser(MenteeDto menteeDto) {
		Mentee mr = new Mentee();
		mr.setId(menteeDto.getId());
		mr.setName(menteeDto.getName());
		mr.setAddress(menteeDto.getAddress());
		mr.setEmail(menteeDto.getEmail());
		String salt = PasswordEncryptor.generateSalt();
	    String encryptedPassword = PasswordEncryptor.encryptPassword(menteeDto.getPassword(), salt);
	    mr.setPassword(encryptedPassword);
		mr.setPhone(menteeDto.getPhone());
		mr.setSalt(salt);
		return mr;
	}
	
	private MenteeDto userToDto(Mentee user) {
		MenteeDto menteeDto = new MenteeDto();
		menteeDto.setId(user.getId());
		menteeDto.setName(user.getName());
		menteeDto.setAddress(user.getAddress());
		menteeDto.setEmail(user.getEmail());
		menteeDto.setAddress(user.getAddress());
		//menteeDto.setPath(user.getPath());
	    menteeDto.setPassword(user.getPassword());
		//menteeDto.setSpecialized(user.getSpecialized());
		menteeDto.setPhone(user.getPhone());
		menteeDto.setSalt(user.getSalt());
		return menteeDto;
	}

	@Override
	public MenteeDto getMenteeByEmail(String email) {
		System.out.println(email);
		Mentee mentee = menteeRepo.findByEmail(email);
        if (mentee != null) {
            return userToDto(mentee);
        }

        return null;
	}
	
}

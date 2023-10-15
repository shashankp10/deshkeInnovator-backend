package com.dki.services.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dki.Repo.MentorRepo;
import com.dki.dto.MentorDto;
import com.dki.entities.Mentor;
import com.dki.security.PasswordEncryptor;
import com.dki.services.MentorService;

@Service
public class MentorImpl implements MentorService {

	@Autowired
	private MentorRepo mentorRepo;
	
	@Override
	public MentorDto createMentor(MentorDto mentorDto) {
		Mentor mentor = this.dtoToUser(mentorDto);
		Mentor savedMentor = this.mentorRepo.save(mentor);
		return this.userToDto(savedMentor);
	}

	@Override
	public MentorDto updateMentor(MentorDto mentorDto, int mentorId) {
		try {
	        Optional<Mentor> optionalUser = this.mentorRepo.findById(mentorId);
	        if (optionalUser.isPresent()) {
	            Mentor mentor = optionalUser.get();
	            mentor.setAddress(mentorDto.getAddress());
	            mentor.setName(mentorDto.getName());
	            mentor.setEmail(mentorDto.getEmail());
	            mentor.setSpecialized(mentorDto.getSpecialized());
	            String salt = PasswordEncryptor.generateSalt();
	    	    String encryptedPassword = PasswordEncryptor.encryptPassword(mentorDto.getPassword(), salt);
	    	    mentor.setPassword(encryptedPassword);
	            mentor.setPath(mentorDto.getPath());
	            mentor.setPhone(mentorDto.getPhone());
	    		
	            Mentor updatedUser = this.mentorRepo.save(mentor);
	            MentorDto userDto1 = this.userToDto(updatedUser);
	    		return userDto1;
	        } else {
	            System.err.println("Mentor not found with ID: " + mentorId);
	            return null;
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return null;
	    }
	}

	@Override
	public List<MentorDto> getMentorById(String specification) {
	    try {
	        List<Mentor> mentors = this.mentorRepo.findBySpecification(specification);

	        if (!mentors.isEmpty()) {
	            List<MentorDto> mentorDtos = mentors.stream()
	                    .map(mentor -> {
	                        MentorDto mentorDto = new MentorDto();
	                        mentorDto.setName(mentor.getName());
	                        mentorDto.setEmail(mentor.getEmail());
	                        mentorDto.setPhone(mentor.getPhone());
	                        mentorDto.setSpecialized(mentor.getSpecialized());
	                        mentorDto.setPath(mentor.getPath());
	                        return mentorDto;
	                    })
	                    .collect(Collectors.toList());

	            return mentorDtos;
	        } else {
	            System.err.println("No mentors found with specification: " + specification);
	            return Collections.emptyList(); 
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return Collections.emptyList();
	    }
	}

	@Override
	public List<MentorDto> getAllMentor() {
		List<Mentor> mentors = this.mentorRepo.findAll();
		List<MentorDto> mentorDtos = mentors.stream()
                .map(mentor -> {
                    MentorDto mentorDto = new MentorDto();
                    mentorDto.setName(mentor.getName());
                    mentorDto.setEmail(mentor.getEmail());
                    mentorDto.setPhone(mentor.getPhone());
                    mentorDto.setSpecialized(mentor.getSpecialized());
                    mentorDto.setPath(mentor.getPath());
                    mentorDto.setAddress(mentor.getAddress());
                    return mentorDto;
                })
                .collect(Collectors.toList());
		return mentorDtos;
	}

	@Override
	public void deleteMentor(int mentorId) {
		 try {
	        Optional<Mentor> optionalUser = this.mentorRepo.findById(mentorId);
	        if (optionalUser.isPresent()) {
	            Mentor user = optionalUser.get();
	            this.mentorRepo.delete(user);
	        } else {
	            System.err.println("Mentor not found with ID: " + mentorId);
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	}
	private Mentor dtoToUser(MentorDto mentorDto) {
		Mentor mr = new Mentor();
		mr.setId(mentorDto.getId());
		mr.setName(mentorDto.getName());
		mr.setAddress(mentorDto.getAddress());
		mr.setEmail(mentorDto.getEmail());
		String salt = PasswordEncryptor.generateSalt();
	    String encryptedPassword = PasswordEncryptor.encryptPassword(mentorDto.getPassword(), salt);
	    mr.setPassword(encryptedPassword);
		mr.setPath(mentorDto.getPath());
		mr.setSpecialized(mentorDto.getSpecialized());
		mr.setPhone(mentorDto.getPhone());
		mr.setSalt(salt);
		return mr;
	}
	private MentorDto userToDto(Mentor user) {
		MentorDto mentorDto = new MentorDto();
		mentorDto.setId(user.getId());
		mentorDto.setName(user.getName());
		mentorDto.setAddress(user.getAddress());
		mentorDto.setEmail(user.getEmail());
		mentorDto.setPath(user.getPath());
		mentorDto.setPassword(user.getPassword());
		mentorDto.setSpecialized(user.getSpecialized());
		mentorDto.setPhone(user.getPhone());
		mentorDto.setSalt(user.getSalt());
		return mentorDto;
	}	
	public MentorDto getMentorById(int mentorId) {
	    try {
	        Optional<Mentor> optionalMentor = mentorRepo.findById(mentorId);

	        if (optionalMentor.isPresent()) {
	            Mentor mentor = optionalMentor.get();
	            return userToDto(mentor);
	        } else {
	            System.err.println("Mentor not found with ID: " + mentorId);
	            return null;
	        }
	    } catch (Exception ex) {
	        ex.printStackTrace();
	        return null;
	    }
	}

	@Override
	public MentorDto getMentorByEmail(String email) {
		Mentor mentee = mentorRepo.findByEmail(email);

        if (mentee != null) {
            return userToDto(mentee);
        }

        return null;
	}
}

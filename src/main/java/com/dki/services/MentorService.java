package com.dki.services;

import java.util.List;

import com.dki.dto.MentorDto;

public interface MentorService {
	MentorDto createMentor(MentorDto mentorDto);
	MentorDto updateMentor(MentorDto mentorDto, int mentorId);
	List<MentorDto> getMentorById(String specification);
	List<MentorDto> getAllMentor();
	void deleteMentor(int mentorId);
	MentorDto getMentorById(int mentorId);
	MentorDto getMentorByEmail(String email);
}

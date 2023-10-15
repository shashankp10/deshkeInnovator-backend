package com.dki.services;

import java.util.List;

import com.dki.dto.MenteeDto;

public interface MenteeService {
	MenteeDto createMentee(MenteeDto menteeDto);
	MenteeDto updateMentee(MenteeDto user, int menteeId);
	//MenteeDto getMenteeById(String specification);
	List<MenteeDto> getAllMentee();
	void deleteMentee(int menteeId);
	MenteeDto getMenteeById(int menteeId);
	MenteeDto getMenteeByEmail(String email);
}

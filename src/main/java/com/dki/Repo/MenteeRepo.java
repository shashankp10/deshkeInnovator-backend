package com.dki.Repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.dki.entities.Mentee;

public interface MenteeRepo extends JpaRepository<Mentee, Integer> {
	@Query("SELECT m FROM Mentee m WHERE m.email = :email")
	Mentee findByEmail(String email);
}

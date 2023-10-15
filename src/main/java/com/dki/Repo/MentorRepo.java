package com.dki.Repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.dki.entities.Mentee;
import com.dki.entities.Mentor;

public interface MentorRepo extends JpaRepository<Mentor, Integer>{
	 @Query("SELECT m FROM Mentor m WHERE m.specialized = :specialized")
	 List<Mentor> findBySpecification(@Param("specialized") String spec);
	 
	 @Query("SELECT m FROM Mentor m WHERE m.email = :email")
	 Mentor findByEmail(String email);
}

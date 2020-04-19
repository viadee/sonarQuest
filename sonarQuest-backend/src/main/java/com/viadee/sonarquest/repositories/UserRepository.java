package com.viadee.sonarquest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;

public interface UserRepository extends JpaRepository<User, Long> {
	
	User findByUsername(String username);
	
	
	List<User> findByCurrentWorld(World world);

	@Query("SELECT u FROM User u WHERE LOWER(u.mail) = LOWER(:mail)")
	User findByMail(@Param("mail") String mail);
	
	

}

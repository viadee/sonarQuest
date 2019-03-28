package com.viadee.sonarquest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.viadee.sonarquest.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	@Query("SELECT u FROM User u WHERE LOWER(u.mail) = LOWER(:mail)")
	User findByMail(@Param("mail") String mail);

}

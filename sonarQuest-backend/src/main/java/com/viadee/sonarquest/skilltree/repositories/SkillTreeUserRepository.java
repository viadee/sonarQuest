package com.viadee.sonarquest.skilltree.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.viadee.sonarquest.skilltree.entities.SkillTreeUser;


public interface SkillTreeUserRepository extends JpaRepository<SkillTreeUser, Long> {
    
	@Query("SELECT u FROM SkillTreeUser u WHERE LOWER(u.mail) = LOWER(:mail)")
    public SkillTreeUser findByMail(@Param("mail") String mail);
	
}

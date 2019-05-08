package com.viadee.sonarquest.skillTree.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.viadee.sonarquest.skillTree.entities.SkillTreeUser;
import com.viadee.sonarquest.skillTree.entities.UserSkill;


public interface SkillTreeUserRepository extends JpaRepository<SkillTreeUser, Long> {
    
	@Query("SELECT u FROM SkillTreeUser u WHERE LOWER(u.mail) = LOWER(:mail)")
    public SkillTreeUser findByMail(@Param("mail") String mail);
	
}

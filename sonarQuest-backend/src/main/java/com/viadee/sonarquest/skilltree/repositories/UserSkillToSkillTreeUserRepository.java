package com.viadee.sonarquest.skilltree.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.viadee.sonarquest.skilltree.entities.UserSkill;
import com.viadee.sonarquest.skilltree.entities.UserSkillToSkillTreeUser;


public interface UserSkillToSkillTreeUserRepository extends JpaRepository<UserSkillToSkillTreeUser, Long> {
	 
	 @Query("SELECT uststu FROM UserSkillToSkillTreeUser uststu WHERE userSkill = :userSkill ")
	 List<UserSkillToSkillTreeUser> findUserSkillToSkillTreeUsersByUserSkill(@Param("userSkill") final UserSkill userSkill);
		 
    

}

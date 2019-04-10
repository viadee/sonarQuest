package com.viadee.sonarquest.skillTree.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.viadee.sonarquest.skillTree.entities.SkillTreeUser;
import com.viadee.sonarquest.skillTree.entities.UserSkill;
import com.viadee.sonarquest.skillTree.entities.UserSkillToSkillTreeUser;


public interface UserSkillToSkillTreeUserRepository extends JpaRepository<UserSkillToSkillTreeUser, Long> {
	
	
	 @Query("SELECT uststu FROM UserSkillToSkillTreeUser uststu WHERE userSkill = :userSkill AND skillTreeUser = :skilLTreeUser")
	public UserSkillToSkillTreeUser findUserSkillToSkillTreeUserByUserSkillAndUser(@Param("userSkill") final UserSkill userSkill, @Param("skilLTreeUser") final SkillTreeUser skilLTreeUser);
	 
	 @Query("SELECT uststu FROM UserSkillToSkillTreeUser uststu WHERE userSkill = :userSkill ")
		public List<UserSkillToSkillTreeUser> findUserSkillToSkillTreeUsersByUserSkill(@Param("userSkill") final UserSkill userSkill);
		 
    

}

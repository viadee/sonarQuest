package com.viadee.sonarquest.skillTree.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.viadee.sonarquest.skillTree.entities.SkillTreeUser;
import com.viadee.sonarquest.skillTree.entities.SonarRule;

public interface SonarRuleRepository extends JpaRepository<SonarRule, Long> {
	
	@Query("SELECT sr FROM SonarRule sr WHERE LOWER(sr.key) = LOWER(:key)")
	public SonarRule findSonarRuleByKey(@Param("key") String key);

}

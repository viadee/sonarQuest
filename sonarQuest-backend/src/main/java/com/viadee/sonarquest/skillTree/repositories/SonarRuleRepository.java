package com.viadee.sonarquest.skillTree.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarquest.skillTree.entities.SonarRule;

public interface SonarRuleRepository extends JpaRepository<SonarRule, Long> {

}

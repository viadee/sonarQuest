package com.viadee.sonarQuest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarQuest.entities.SonarConfig;

public interface SonarConfigRepository extends JpaRepository<SonarConfig, Long> {

    SonarConfig getByName(String name);
}

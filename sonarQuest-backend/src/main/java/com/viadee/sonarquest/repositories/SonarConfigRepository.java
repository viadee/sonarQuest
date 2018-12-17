package com.viadee.sonarquest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarquest.entities.SonarConfig;

public interface SonarConfigRepository extends JpaRepository<SonarConfig, Long> {

    SonarConfig getByName(String name);

    SonarConfig findFirstBy();
}

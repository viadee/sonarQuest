package com.viadee.sonarQuest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarQuest.entities.ServerInfo;

public interface ServerInfoRepository extends JpaRepository<ServerInfo, Long> {}

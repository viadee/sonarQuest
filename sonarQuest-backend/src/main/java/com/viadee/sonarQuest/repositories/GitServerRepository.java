package com.viadee.sonarQuest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarQuest.entities.GitServer;
import com.viadee.sonarQuest.entities.World;

public interface GitServerRepository extends JpaRepository<GitServer, Long> {
    GitServer findOneByWorld(World world);
}

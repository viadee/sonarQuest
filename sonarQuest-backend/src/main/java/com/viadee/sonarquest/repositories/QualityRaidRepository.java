package com.viadee.sonarquest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarquest.entities.QualityRaid;
import com.viadee.sonarquest.entities.World;

public interface QualityRaidRepository extends JpaRepository<QualityRaid, Long> {
    
    List<QualityRaid> findByWorld(World world);
}

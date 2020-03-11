package com.viadee.sonarquest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarquest.entities.QualityGateRaid;
import com.viadee.sonarquest.entities.World;

public interface QualityGateRaidRepository extends JpaRepository<QualityGateRaid, Long> {
    
    QualityGateRaid findTopByWorldId(Long worldId);
    
    QualityGateRaid findTopByWorld(World world);
}

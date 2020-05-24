package com.viadee.sonarquest.repositories;

import java.sql.Date;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;

import com.viadee.sonarquest.entities.QualityGateRaid;
import com.viadee.sonarquest.entities.QualityGateRaidRewardHistory;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatusType;

public interface QualityGateRaidRewardHistoryRepository extends CrudRepository<QualityGateRaidRewardHistory, Long> {

    List<QualityGateRaidRewardHistory> findByRaid(QualityGateRaid raid);
    QualityGateRaidRewardHistory findTopByRaidIdAndStatusDate(long raid_id, Date statusDate);
    QualityGateRaidRewardHistory findTopByRaidIdOrderByStatusDateDesc(long raid_id);
    List<QualityGateRaidRewardHistory> findByStatusDate(Date statusDate);
    
    List<QualityGateRaidRewardHistory> findByRaidIdAndStatusDateBetweenOrderByStatusDate(long raid_id, Date fromDate, Date toDate);
    
    /**
     * Find High Score number of error-free-days
     * @param raidId
     * @return QualityGateRaidRewardHistory ordered by number of error free days 
     */
    QualityGateRaidRewardHistory findTopByRaidIdOrderByNumberOfErrorFreeDaysDesc(long raidId);
    
    QualityGateRaidRewardHistory findTopByRaidIdAndSonarQubeStatus(long raidId, SonarQubeProjectStatusType status, Sort sort);

}
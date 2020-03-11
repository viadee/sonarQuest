package com.viadee.sonarquest.services;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;

import org.hibernate.envers.query.AuditEntity;
import org.hibernate.envers.query.AuditQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.configurations.JpaAuditingConfiguration;
import com.viadee.sonarquest.entities.QualityGateRaid;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatusType;

@Service
public class QualityGateHighScoreService {
	private static final Logger LOGGER = LoggerFactory.getLogger(QualityGateHighScoreService.class);
	 
	@Autowired
	private JpaAuditingConfiguration audit;
	
	
	/**
	 * Update QualityGateRaid HighScore (= error-free-days)
	 * Calculate actual score and compares with last HighScore
	 * 
	 * @param raid
	 */
	public void updateQualityGateHighScore(final QualityGateRaid raid) {
		Long actualScore = calculateActualScore(raid);
		if (actualScore == null)
			return;
		
		if (actualScore.compareTo(raid.getScorePoints()) == 1) {
			raid.updateHighScore(java.sql.Date.valueOf(LocalDate.now()), actualScore);
			
			LOGGER.info("QualityGateRaid highScore is updated with new score {actualScore}", actualScore);
		}
	}

	/**
	 * Calculate actual error-free-day score from QualityGateRaid.
	 * 
	 * Count dates between the last statusDate (with status=Error) and now.
	 * 
	 * @param raid
	 * @return
	 */
	public Long calculateActualScore(final QualityGateRaid raid) {
		if (raid == null || SonarQubeProjectStatusType.ERROR.equals(raid.getSonarQubeStatus()))
			return null;

		Date statusDate = null;
		
		LOGGER.info("Find last audit entry with status=Error for QualityGateRaid {id}", raid.getId());
		QualityGateRaid lastErrorResult = findQualityGateRaidAuditByIdAndStatusOrderByUpdated(raid.getId(), SonarQubeProjectStatusType.ERROR, Direction.DESC);
		
		if(lastErrorResult == null) { 
			
			LOGGER.info("There is no audit entry with status=Error! Find last audit entry with status=OK!");
			QualityGateRaid firstOkResult = findQualityGateRaidAuditByIdAndStatusOrderByUpdated(raid.getId(), SonarQubeProjectStatusType.OK, Direction.ASC);
			
			if(firstOkResult == null) {
				LOGGER.info("There is no further audit entry found!");
				
				return null;
			}
			
			statusDate = firstOkResult.getUpdated();
			
		} else {
			statusDate = lastErrorResult.getUpdated();
		}
			
		Long datesBetween = ChronoUnit.DAYS.between(LocalDate.ofInstant(statusDate.toInstant(), ZoneId.systemDefault()), LocalDate.now());
		return datesBetween;
	}
	
	public QualityGateRaid findQualityGateRaidAuditByIdAndStatusOrderByUpdated(long raidId, SonarQubeProjectStatusType status, Direction sort){
		@SuppressWarnings("unchecked")
		List<QualityGateRaid> qualityGateRaidList = buildQueryFindByIdAndStatusOrderByUpdated(raidId, status, sort).setMaxResults(1).getResultList();
		if(qualityGateRaidList == null || qualityGateRaidList.isEmpty()) {
			return null;
		}
		
		return qualityGateRaidList.get(0); 
	}

	/**
	 * Build Query: Find by 'id', 'sonarQubeStatus' order by 'updated'
	 * 
	 * @param raidId
	 * @param status
	 * @param sort ASC/DESC
	 * @return AuditQuery to get result
	 */
	private AuditQuery buildQueryFindByIdAndStatusOrderByUpdated(long raidId, SonarQubeProjectStatusType status, Direction sort) {
		AuditQuery auditQuery = audit.auditReader().createQuery().forRevisionsOfEntity(QualityGateRaid.class, true, false);
		auditQuery.add(AuditEntity.id().eq(raidId));
		auditQuery.add(AuditEntity.property(QualityGateRaid.sonarQubeStatusField).eq(status));

		if(Direction.ASC.equals(sort)) {
			auditQuery.addOrder(AuditEntity.property(QualityGateRaid.udatedField).asc());
			
		} else if (Direction.DESC.equals(sort)) {
			auditQuery.addOrder(AuditEntity.property(QualityGateRaid.udatedField).desc());
		}
		return auditQuery;
	}
}

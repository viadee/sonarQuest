package com.viadee.sonarquest.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarquest.entities.QualityGateRaid;
import com.viadee.sonarquest.entities.QualityGateRaidRewardHistory;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatusType;
import com.viadee.sonarquest.interfaces.HighScore;
import com.viadee.sonarquest.repositories.QualityGateRaidRewardHistoryRepository;

@Service
public class QualityGateRaidRewardHistoryService {
	private static final Logger LOGGER = LoggerFactory.getLogger(QualityGateRaidRewardHistoryService.class);

	@Autowired
	private QualityGateRaidRewardHistoryRepository qualityGateRaidRewardHistoryRepository;

	public List<QualityGateRaidRewardHistory> findQualityGateRaidRewards(LocalDate fromDate, LocalDate toDate,
			final Long raidId) {
		List<QualityGateRaidRewardHistory> result = new ArrayList<QualityGateRaidRewardHistory>();
		try {
			result = qualityGateRaidRewardHistoryRepository.findByRaidIdAndStatusDateBetweenOrderByStatusDate(raidId,
					Date.valueOf(fromDate), Date.valueOf(toDate));
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
	
	public List<QualityGateRaidRewardHistory> findAllQualityGateRaidRewardsByStatusDate(LocalDate fromDate) {
		List<QualityGateRaidRewardHistory> result = new ArrayList<QualityGateRaidRewardHistory>();
		try {
			result = qualityGateRaidRewardHistoryRepository.findByStatusDate(Date.valueOf(fromDate));	
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
	
	public QualityGateRaidRewardHistory findLastQualityGateRaidRewardHistory(final long qualityGateID) {
		QualityGateRaidRewardHistory result = null;
		try {
			result = qualityGateRaidRewardHistoryRepository.findTopByRaidIdOrderByStatusDateDesc(qualityGateID);
		} catch (Exception e) {
			System.out.println(e);
		}
		return result;
	}
	

	public HighScore findHighScoreNumberOfErrorFreeDays(final long qualityGateID) {
		HighScore errorFreeDayScore = null;
		try {
			errorFreeDayScore = qualityGateRaidRewardHistoryRepository.findTopByRaidIdOrderByNumberOfErrorFreeDaysDesc(qualityGateID);
		} catch (Exception e) {
			System.out.println(e);
		}
		
		return errorFreeDayScore;
	}

	/**
	 * Update or create new QualityGateRaidRewardHistory:
	 * Create -> if no entry with current statusDate already exists!
	 * Update ->
	 * 		if QualityGateRaid Status changed to error (Set reward null) 
	 * 	  	or gate has been fixed (Revert changes)
	 * 
	 * @param gate
	 * @return
	 */
	@Transactional
	public QualityGateRaidRewardHistory updateQualityGateRaidRewardHistory(final QualityGateRaid gate) {
		QualityGateRaidRewardHistory newHistoryEntry = createQualityGateRaidRewardHistory(gate);

		if (newHistoryEntry == null) 
			return updateLastQualityGateRaidRewardHistory(gate);

		return newHistoryEntry;
	}
	
	/**
	 * Create new QualityGateRaidRewardHistory: 
	 * Only if no entry with current statusDate already exists!
	 * 
	 * @param QualityGateRaid
	 * @return saved QualityGateRaidRewardHistory or null
	 */
	@Transactional
	private QualityGateRaidRewardHistory createQualityGateRaidRewardHistory(final QualityGateRaid gate) {
		QualityGateRaidRewardHistory lastHistoryEntry = qualityGateRaidRewardHistoryRepository
				.findTopByRaidIdOrderByStatusDateDesc(gate.getId());

		QualityGateRaidRewardHistory toSave = null;
		
		if (lastHistoryEntry == null) {
			toSave = createQualityGateRaidReward(gate, 0, 0, 1);
			
		} else if (LocalDate.now().isAfter(lastHistoryEntry.getStatusDate().toLocalDate())) { // Verify if entry with statusDate not exists:
			toSave = createQualityGateRaidReward(gate, lastHistoryEntry.getGold(), lastHistoryEntry.getXp(), lastHistoryEntry.getNumberOfErrorFreeDays() + 1);
		}
		
		if(toSave != null) {
			LOGGER.info("Create new QualityGateRaidRewardHistory entry with Gold: " + toSave.getGold() +"/ xp:" + toSave.getXp() + " /NumberOfErrorFreeDays: " + toSave.getNumberOfErrorFreeDays());
			
			return qualityGateRaidRewardHistoryRepository.save(toSave);
		}
		
		return null;
	}
	
	public QualityGateRaidRewardHistory createQualityGateRaidReward(final QualityGateRaid gate, final long lastGold, final long lastXp, final long lastErrorFreeDays) {
		boolean isErrorStatus = SonarQubeProjectStatusType.ERROR.equals(gate.getSonarQubeStatus());
		long gold = isErrorStatus ? 0 : gate.getGold() + lastGold;
		long xp = isErrorStatus ? 0 : gate.getXp() + lastXp;
		long numberOfErrorFreeDays = isErrorStatus ? 0 : lastErrorFreeDays;
		
		return new QualityGateRaidRewardHistory(Date.valueOf(LocalDate.now()), gate.getSonarQubeStatus(), gate, gold, xp, numberOfErrorFreeDays);
	}
	

	/**
	 * Update last QualityGateRaidRewardHistory
	 * Verify if the QualityGateRaid Status changed to error or the gate has been fixed
	 * 
	 * If the status changed to error -> Set (=Gold/XP) = 0
	 * If the gate has been fixed -> Revert last changes: Set Reward back to last state
	 * 
	 * @param QualityGateRaid
	 * @return saved(QualityGateRaidRewardHistory) or null
	 */
	@Transactional
	private QualityGateRaidRewardHistory updateLastQualityGateRaidRewardHistory(final QualityGateRaid gate) {
		QualityGateRaidRewardHistory actualReward = qualityGateRaidRewardHistoryRepository.findTopByRaidIdOrderByStatusDateDesc(gate.getId());

		if (actualReward == null)
			return null;

		long gold = actualReward.getGold();
		long xp = actualReward.getXp();
		long numberOfErrorFreeDays = actualReward.getNumberOfErrorFreeDays();
		
		if (SonarQubeProjectStatusType.ERROR.equals(gate.getSonarQubeStatus())) {
			LOGGER.info("Updated QualityGateRaidRewardHistory with status=Error and set reward (=Gold/XP) to 0/0.");
			
			gold =  0;
			xp = 0;
			numberOfErrorFreeDays = 0l;
			
		} else if (SonarQubeProjectStatusType.OK.equals(gate.getSonarQubeStatus())
				&& SonarQubeProjectStatusType.ERROR.equals(actualReward.getSonarQubeStatus())) {
			LOGGER.info("QualityGateRaid fixed! Revert changes: set reward back to last state!");
			
			QualityGateRaidRewardHistory lastEntry = qualityGateRaidRewardHistoryRepository
					.findTopByRaidIdAndStatusDate(gate.getId(), Date.valueOf(LocalDate.now().minusDays(1)));
			
			gold =  lastEntry.getGold();
			xp = lastEntry.getXp();
			numberOfErrorFreeDays = lastEntry.getNumberOfErrorFreeDays();
		}

		actualReward.setGold(gold);
		actualReward.setXp(xp);
		actualReward.setNumberOfErrorFreeDays(numberOfErrorFreeDays);
		actualReward.setSonarQubeStatus(gate.getSonarQubeStatus());
		
		return qualityGateRaidRewardHistoryRepository.save(actualReward);
	}
}

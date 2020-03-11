package com.viadee.sonarquest.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarquest.entities.QualityGateRaid;
import com.viadee.sonarquest.entities.QualityGateRaidRewardHistory;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatusType;
import com.viadee.sonarquest.interfaces.Reward;
import com.viadee.sonarquest.repositories.QualityGateRaidRewardHistoryRepository;

@Service
public class QualityGateRaidRewardHistoryService {
	private static final Logger LOGGER = LoggerFactory.getLogger(QualityGateRaidRewardHistoryService.class);

	@Autowired
	private QualityGateRaidRewardHistoryRepository qualityGateRaidRewardHistoryRepository;

	public List<QualityGateRaidRewardHistory> readQualityGateRaidRewardHistories(LocalDate fromDate, LocalDate toDate,
			final Long raidId) {
		return qualityGateRaidRewardHistoryRepository.findByRaidIdAndStatusDateBetweenOrderByStatusDate(raidId,
				Date.valueOf(fromDate), Date.valueOf(toDate));
	}

	/**
	 * Update or create new QualityGateRaidStatusHistory:
	 * Create -> if no entry with current statusDate already exists!
	 * Update -> if status changed to error!
	 * 
	 * @param gate
	 * @return
	 */
	public QualityGateRaidRewardHistory updateQualityGateRaidRewardHistory(final QualityGateRaid gate) {
		QualityGateRaidRewardHistory newHistoryEntry = createQualityGateRaidRewardHistoryForDay(gate);

		if (newHistoryEntry == null) // verify and update existing entry!
			return verifyAndUpdateRewardFromToday(gate);

		return newHistoryEntry;
	}

	/**
	 * Create new QualityGateRaidRewardHistory for day: 
	 * Only if no entry with current statusDate already exists!
	 * 
	 * Calculate reward from lastEntry.
	 * 
	 * @param gate
	 * @return saved QualityGateRaidStatusHistory or null
	 */
	@Transactional
	private QualityGateRaidRewardHistory createQualityGateRaidRewardHistoryForDay(final QualityGateRaid gate) {
		QualityGateRaidRewardHistory lastHistoryEntry = qualityGateRaidRewardHistoryRepository
				.findTopByRaidIdOrderByStatusDateDesc(gate.getId());

		if (lastHistoryEntry == null) {
			Reward bonus = calculateReward(gate, 0, 0);
			
			LOGGER.info("Create new QualityGateRaidStatusHistory entry with Gold:" + bonus.getGold() +"/ xp:" + bonus.getXp());
			return qualityGateRaidRewardHistoryRepository.save(new QualityGateRaidRewardHistory(
					Date.valueOf(LocalDate.now()), gate.getSonarQubeStatus(), gate, bonus.getGold(), bonus.getXp()));
		}
			
		// Verify if historyEntry with statusDate already exists:
		if (LocalDate.now().isAfter(lastHistoryEntry.getStatusDate().toLocalDate())) {
			Reward bonus = calculateReward(gate, lastHistoryEntry.getGold(), lastHistoryEntry.getXp());
			
			LOGGER.info("Create new QualityGateRaidStatusHistory entry with Gold:" + bonus.getGold() +"/ xp:" + bonus.getXp());
			return qualityGateRaidRewardHistoryRepository.save(new QualityGateRaidRewardHistory(
					Date.valueOf(LocalDate.now()), gate.getSonarQubeStatus(), gate, bonus.getGold(), bonus.getXp()));
		}
		return null;
	}
	
	private Reward calculateReward(final QualityGateRaid gate, final long lastGold, final long lastXp) {
		boolean isErrorStatus = SonarQubeProjectStatusType.ERROR.equals(gate.getSonarQubeStatus());
		long gold = isErrorStatus ? 0 : gate.getGold() + lastGold;
		long xp = isErrorStatus ? 0 : gate.getXp() + lastXp;
		
		return new QualityGateRaidRewardHistory(gold, xp);
	}

	/**
	 * Verify if the gate status from TODAY changed to error.
	 * If the status changed->update reward (=Gold/XP) = 0.
	 * 
	 * @param QualityGateRaid
	 * @return
	 */
	@Transactional
	private QualityGateRaidRewardHistory verifyAndUpdateRewardFromToday(final QualityGateRaid gate) {
		QualityGateRaidRewardHistory historyEntry = qualityGateRaidRewardHistoryRepository
				.findTopByRaidIdAndStatusDate(gate.getId(), Date.valueOf(LocalDate.now()));

		if (historyEntry == null)
			return null;

		if (SonarQubeProjectStatusType.ERROR.equals(gate.getSonarQubeStatus())) {
			historyEntry.setGold(0l);
			historyEntry.setXp(0l);
			historyEntry.setSonarQubeStatus(gate.getSonarQubeStatus());

			LOGGER.info("Updated QualityGateRaidStatusHistory with status=Error.");
			return qualityGateRaidRewardHistoryRepository.save(historyEntry);
		}

		return historyEntry;
	}
}

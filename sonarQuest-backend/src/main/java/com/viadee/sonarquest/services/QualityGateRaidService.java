package com.viadee.sonarquest.services;

import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.viadee.sonarquest.entities.QualityGateRaid;
import com.viadee.sonarquest.entities.QualityGateRaidRewardHistory;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatus;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatusType;
import com.viadee.sonarquest.interfaces.HighScore;
import com.viadee.sonarquest.repositories.QualityGateRaidRepository;

@Service
public class QualityGateRaidService {
	private static final Logger LOGGER = LoggerFactory.getLogger(QualityGateRaidService.class);

	@Autowired
	private QualityGateRaidRepository qualityGateRaidRepository;

	@Autowired
	private ExternalRessourceService externalRessourceService;

	@Autowired
	private QualityGateRaidRewardHistoryService qualityGateRaidRewardHistoryService;

	@Autowired
	private WorldService worldService;

	@Autowired
	private ConditionService conditionService;
	
	@Autowired 
	private GratificationService gratificationService; 
	
	
	@Transactional
	public QualityGateRaid save(final QualityGateRaid raid) {
		QualityGateRaid qualityGateRaidDAO = qualityGateRaidRepository.save(raid);

		qualityGateRaidRewardHistoryService.updateQualityGateRaidRewardHistory(raid);
		
		HighScore numberOfErrorFreeDaysScore = qualityGateRaidRewardHistoryService.findHighScoreNumberOfErrorFreeDays(raid.getId());
		qualityGateRaidDAO.updateHighScore(numberOfErrorFreeDaysScore.getScoreDay(), numberOfErrorFreeDaysScore.getScorePoints());
		
		qualityGateRaidRepository.flush();
		return qualityGateRaidDAO;
	}

	public QualityGateRaid findByWorld(final Long worldId) {
		// !There is only one quality gate for a world - but maybe that will be expanded!
		QualityGateRaid raid = qualityGateRaidRepository.findTopByWorldId(worldId);
		return raid;
	}
	
	/**
	 * Update or create new QualityGateRaid from SonarQube
	 * 
	 * !There is only one quality gate for a world - but maybe that will be
	 * expanded!
	 * 
	 * @param raid
	 */
	@Transactional
	public QualityGateRaid updateQualityGateRaid(final QualityGateRaid raid) {
		if (raid == null) {
			LOGGER.error("QualityGateRaid is null");
			return null;
		}

		QualityGateRaid savedRaid = qualityGateRaidRepository.findTopByWorld(raid.getWorld());
		if (savedRaid == null)
			return createQualityRaid(raid.getWorld().getId(), raid.getTitle(), raid.getGold(), raid.getXp());

		savedRaid.updateBaseRaid(raid);
		updateQualityGateStatusAndConditions(savedRaid);
		return savedRaid;
	}
	
	public void updateAllQualityGates() {
		List<QualityGateRaid> qualityGates = qualityGateRaidRepository.findAll();
		qualityGates.forEach(gate -> {
			updateQualityGateStatusAndConditions(gate);
		});
	}

	/**
	 * Create or update QualityGateRaid from world
	 * With default reward (Gold/XP) = 2
	 * 
	 * @param world
	 * @return
	 */
	public QualityGateRaid updateDefaultQualityGateRaidFromWorld(final World world) {
		if (world == null) {
			LOGGER.error("World is null!");
			return null;
		}

		QualityGateRaid savedRaid = qualityGateRaidRepository.findTopByWorld(world);
		if (savedRaid == null)
			return createQualityRaid(world.getId(), "Default generated Quality Gate", 2l, 2l);
		
		updateQualityGateStatusAndConditions(savedRaid);
		return savedRaid;
	}

	
	/**
	 * Update and save QualityGateRaid status and conditions from SonarQube
	 * 
	 * @param qualityGateRaid not null
	 * 
	 * @see SonarQubeProjectStatus
	 */
	@Transactional
	private void updateQualityGateStatusAndConditions(final QualityGateRaid qualityGateRaid) {
		// Call SonarQubeProjectStatus (= QualityGate)
		SonarQubeProjectStatus sonarQubeProjectStatus = externalRessourceService
				.generateSonarQubeProjectStatusFromWorld(qualityGateRaid.getWorld());
		qualityGateRaid.setSonarQubeStatus(SonarQubeProjectStatusType.fromString(sonarQubeProjectStatus.getStatus()));
		save(qualityGateRaid);
		conditionService.updateQualityGateConditionFromSonarQube(sonarQubeProjectStatus, qualityGateRaid); // generate or update conditions
	}

	/**
	 * Create new QualityGateRaid from sonarQube (= SonarQubeProjectStatus)
	 * 
	 */
	@Transactional
	private QualityGateRaid createQualityRaid(final Long worldId, final String titel, final Long gold, final Long xp) {
		World world = worldService.findById(worldId);
		if (world == null) {
			LOGGER.info("World not found.");
			return null;
		}
		LOGGER.info("Create new QualityGateRaid");
		final QualityGateRaid qualityGateRaid = new QualityGateRaid(world);
		qualityGateRaid.setTitle(titel);
		qualityGateRaid.setGold(gold);
		qualityGateRaid.setXp(xp);
		qualityGateRaid.setMonsterImage("assets/images/monster/monster1.png"); // Default image

		updateQualityGateStatusAndConditions(qualityGateRaid);
		return qualityGateRaid;
	}
	
	
	
	// ------------------ Scheduled functions ---------------------------------
	// ------------------------------------------------------------------------
	
	/**
	 * Update QualityGateRaids every hour (from 6-22 o'clock) 
	 */
	@Scheduled(cron="0 0/30 6-22 * * MON-FRI")
	protected void updateAllQualityGatesScheduled() {
		LOGGER.info("Update all QualityGateRaids scheduled: ...");
		updateAllQualityGates();
	}
	
	/**
	 * Reward all users of a world at midnight for PASSED QualityGateRaid
	 */
	@Scheduled(cron="0 0 0 * * MON-FRI" )
	protected void rewardUserForPassedGate() {
		LOGGER.info("Update all QualityGateRaids reward scheduled: ...");
		List<QualityGateRaidRewardHistory> rewardHistoryList = qualityGateRaidRewardHistoryService.findAllQualityGateRaidRewardsByStatusDate(LocalDate.now().minusDays(1)); // find from yesterday
		rewardHistoryList.forEach(reward -> {
			if(!SonarQubeProjectStatusType.ERROR.equals(reward.getSonarQubeStatus())) { // Reward user for Passed QualityGateRaid
				gratificationService.rewardUsersForPassedQualityGate(reward.getRaid().getWorld(), reward);
			}
		});
		
		updateAllQualityGates();
	}
}

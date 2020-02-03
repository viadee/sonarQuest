package com.viadee.sonarquest.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarquest.entities.QualityRaid;
import com.viadee.sonarquest.entities.ConditionTask;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.externalressources.SonarQubeConditionOperatorType;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatus;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatusType;
import com.viadee.sonarquest.repositories.QualityRaidRepository;
import com.viadee.sonarquest.repositories.ConditionTaskRepository;
import com.viadee.sonarquest.rules.SonarQubeStatusMapper;

@Service
public class QualityRaidService {
	private static final Logger LOGGER = LoggerFactory.getLogger(QualityRaidService.class);
	
	@Autowired
    private QualityRaidRepository qualityRaidRepository;
	
	@Autowired
	private WorldService worldService;
	
	@Autowired
	private ConditionTaskRepository qualityTaskRepository;
	
	@Autowired
	private ExternalRessourceService externalRessourceService;
	
	@Autowired
	private SonarQubeStatusMapper statusMapper;
	
	@Transactional
	public QualityRaid save(final QualityRaid raid) {
		return qualityRaidRepository.save(raid);
	}
	
	@Transactional
	public QualityRaid findByWorld(final Long worldId) {
		World world = worldService.findById(worldId);
		List<QualityRaid> raids = qualityRaidRepository.findByWorld(world);
		return raids.get(0);
	}
	
	/**
	 *	Create a quality raid from SonarQube Quality Gate 
	 */
	public QualityRaid createQualityRaid(final Long worldId, final String titel, final Long gold, final Long xp) {
		LOGGER.info("Create new Quality Raid");
		World world = worldService.findById(worldId);
		if(world == null) {
			LOGGER.info("World not found.");
			return null;
		}
		// Call SonarQubeProjectStatus (= QualityGate)
		SonarQubeProjectStatus qualityGate = externalRessourceService.generateSonarQubeProjectStatusFromWorld(world);
		
		// Create and save QualityGate
		final QualityRaid qualityRaid = new QualityRaid();
		qualityRaid.setTitle(titel);
		qualityRaid.setGold(gold);
		qualityRaid.setXp(xp);
		qualityRaid.setWorld(world);
		// TODO map image/ monster from condition state
		qualityRaid.setMonsterName("mel");
		qualityRaid.setMonsterImage("assets/images/monster/monster1.png");
		qualityRaid.setSonarQubeStatus(SonarQubeProjectStatusType.fromString(qualityGate.getStatus()));
		
		final QualityRaid saved = save(qualityRaid);
		
		// Generate QualityTasks and setRaid
		List<ConditionTask> qualityTasks = generateQualityRaidTasks(qualityGate);
		for (ConditionTask qualityTask : qualityTasks) {
			qualityTask.setRaid(saved);
			qualityTask.setWorld(world);
			qualityTaskRepository.save(qualityTask);
		}
		return qualityRaid;
	}
	
	/**
	 * Generate Quality Tasks (= SonarQube Gate conditions) from SonarQubeProjectStatus
	 * @param projectStatus SonarQube Quality Gate
	 * @return List of SonarQube Quality Conditions 
	 */
	public List<ConditionTask> generateQualityRaidTasks(SonarQubeProjectStatus projectStatus) {
		LOGGER.info("Generate Tasks from SonarQubeProjectStatus");
		if(projectStatus.getConditions() == null) {
			LOGGER.info("No quests to generate.");
			return null;
		}
		final String seperator = " ";
		List<ConditionTask> tasks = new ArrayList<ConditionTask>();
		
		projectStatus.getConditions().forEach(sonarQubeCondition -> {
			// generate task title from metric, status and errorThreshold
			String titelText = "";
			titelText += sonarQubeCondition.getMetricKey() + seperator;
			titelText += SonarQubeConditionOperatorType.generateText(sonarQubeCondition.getComparator()) + seperator;
			titelText += sonarQubeCondition.getErrorThreshold();
			
			ConditionTask qualityTask = new ConditionTask(sonarQubeCondition.getMetricKey(), 
					sonarQubeCondition.getComparator(), 
					sonarQubeCondition.getActualValue(), 
					sonarQubeCondition.getErrorThreshold());
			qualityTask.setTitle(titelText);
			qualityTask.setStatus(statusMapper.mapSonarQubeProjectStatus(SonarQubeProjectStatusType.fromString(sonarQubeCondition.getStatus())));
			tasks.add(qualityTask);
		});
		return tasks;
	}
}

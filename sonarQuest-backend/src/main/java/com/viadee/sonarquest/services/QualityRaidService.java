package com.viadee.sonarquest.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarquest.constants.AdventureState;
import com.viadee.sonarquest.constants.QuestState;
import com.viadee.sonarquest.entities.QualityRaid;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.externalressources.SonarQubeConditionOperatorType;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatus;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatusType;
import com.viadee.sonarquest.repositories.QualityRaidRepository;

@Service
public class QualityRaidService {
	private static final Logger LOGGER = LoggerFactory.getLogger(QualityRaidService.class);
	
	@Autowired
    private QualityRaidRepository qualityRaidRepository;
	
	@Autowired
	private WorldService worldService;
	
	@Autowired
	private ExternalRessourceService externalRessourceService;
	
	@Transactional
	public QualityRaid save(final QualityRaid raid) {
		return qualityRaidRepository.save(raid);
	}
	
	@Transactional
	public QualityRaid createAndSaveQualityRaid(final Long worldId, final String titel, final String story, final Long gold, final Long xp) {
		QualityRaid qualityRaidDAO = createQualityRaid(worldId, titel, story, gold, xp);
		return save(qualityRaidDAO);
	}
	
	public QualityRaid createQualityRaid(final Long worldId, final String titel, final String story, final Long gold, final Long xp) {
		LOGGER.info("Create new Quality Raid");
		World world = worldService.findById(worldId);
		if(world == null) {
			LOGGER.info("World not found.");
			return null;
		}
		
		SonarQubeProjectStatus sonarQubeProjectStatus = externalRessourceService.generateSonarQubeProjectStatusFromWorld(world);
		
		QualityRaid qualityRaid = new QualityRaid(titel, story, AdventureState.OPEN, gold, xp, world);
		qualityRaid.setSonarQubeStatus(SonarQubeProjectStatusType.fromString(sonarQubeProjectStatus.getStatus()));
		qualityRaid.setQuests(generateQualityRaidQuests(sonarQubeProjectStatus));
		return qualityRaid;
	}	
	
	private List<Quest> generateQualityRaidQuests(SonarQubeProjectStatus projectStatus) {
		LOGGER.info("Generate Quests from SonarQubeProjectStatus");
		if(projectStatus.getConditions() == null) {
			LOGGER.info("No quests to generate.");
			return null;
		}
		final String seperator = " ";
		List<Quest> quests = new ArrayList<Quest>();
		
		projectStatus.getConditions().forEach(sonarQubeCondition -> {
			// generate quest title from metric, status and value
			String titelText = "";
			titelText += sonarQubeCondition.getMetricKey() + seperator; // TODO use service to get metric description
			titelText += SonarQubeConditionOperatorType.generateText(sonarQubeCondition.getComparator()) + seperator;
			titelText += sonarQubeCondition.getErrorThreshold();
			
			// generate quest status from sonarqube 
			SonarQubeProjectStatusType conditionStatus = SonarQubeProjectStatusType.fromString(sonarQubeCondition.getStatus());
			QuestState questState = SonarQubeProjectStatusType.OK.equals(conditionStatus) ?  QuestState.SOLVED : QuestState.OPEN;
			
			quests.add(new Quest(titelText, "Story of your live", questState, 1L, 1L, null, true));
		});
		return quests;
	}
}

package com.viadee.sonarquest.services;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarquest.entities.Condition;
import com.viadee.sonarquest.entities.QualityGateRaid;
import com.viadee.sonarquest.externalressources.SonarQubeConditionOperatorType;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatus;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatusType;
import com.viadee.sonarquest.repositories.ConditionRepository;
import com.viadee.sonarquest.rules.SonarQubeStatusMapper;

@Service
public class ConditionService {
	private static final Logger LOGGER = LoggerFactory.getLogger(ConditionService.class);
	
	@Autowired
	private ConditionRepository conditionRepository;

	@Autowired
	private SonarQubeStatusMapper statusMapper;

	/**
	 * Generate quality conditions from
	 * SonarQubeProjectStatus
	 * 
	 * @param projectStatus (= SonarQube Quality Gate)
	 * @return List of SonarQube Quality Conditions
	 * 
	 * @see SonarQubeProjectStatus
	 */
	public List<Condition> generateQualityGateConditions(SonarQubeProjectStatus projectStatus) {
		LOGGER.info("Generate Tasks from SonarQubeProjectStatus");
		if (projectStatus.getConditions() == null) {
			LOGGER.info("No conditions to generate!");
			return null;
		}
		final String seperator = " ";
		List<Condition> conditions = new ArrayList<Condition>();

		projectStatus.getConditions().forEach(sonarQubeCondition -> {
			// generate condition from metric, status and errorThreshold
			String titelText = "";
			titelText += sonarQubeCondition.getMetricKey() + seperator;
			titelText += SonarQubeConditionOperatorType.generateText(sonarQubeCondition.getComparator()) + seperator;
			titelText += sonarQubeCondition.getErrorThreshold();

			Condition qualityCondition = new Condition(sonarQubeCondition.getMetricKey(),
					sonarQubeCondition.getComparator(), sonarQubeCondition.getActualValue(),
					sonarQubeCondition.getErrorThreshold());
			qualityCondition.setTitle(titelText);
			qualityCondition.setStatus(statusMapper.mapSonarQubeProjectStatus(SonarQubeProjectStatusType.fromString(sonarQubeCondition.getStatus())));
			
			conditions.add(qualityCondition);
		});
		
		LOGGER.info("Generated {} conditions from SonarQubeProjectStatus", conditions.size());
		return conditions;
	}
	
	public List<Condition> updateQualityGateConditionFromSonarQube(final SonarQubeProjectStatus projectStatus, final QualityGateRaid qualityGate) {
		final List<Condition> generatedConditions = generateQualityGateConditions(projectStatus);
		generatedConditions.forEach(condition -> {
			condition.setQualityGateRaid(qualityGate);
			updateCondition(condition);
		});
		return generatedConditions;
	}
	
	@Transactional
	public Condition updateCondition(final Condition condition) {
		Condition lastCondition = conditionRepository.findByMetricKeyAndQualityGateRaid(condition.getMetricKey(), condition.getQualityGateRaid());
		if (lastCondition != null) {
			lastCondition.setActualValue(condition.getActualValue());
			lastCondition.setErrorThreshold(condition.getErrorThreshold());
			lastCondition.setComparator(condition.getComparator());
		}else {
			lastCondition = condition;
		}
		
		return conditionRepository.save(lastCondition);
	}
}

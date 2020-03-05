package com.viadee.sonarquest.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.viadee.sonarquest.entities.Condition;
import com.viadee.sonarquest.entities.QualityGateRaid;
import com.viadee.sonarquest.externalressources.SonarQubeCondition;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatus;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatusType;
import com.viadee.sonarquest.repositories.ConditionRepository;
import com.viadee.sonarquest.rules.SonarQubeStatusMapper;
import com.viadee.sonarquest.rules.SonarQuestStatus;

@RunWith(MockitoJUnitRunner.class)
public class ConditionServiceTest {

	@InjectMocks
	private ConditionService underTest;

	@Mock
	private ConditionRepository conditionRepositoryMock;
	
	@Mock
	private SonarQubeStatusMapper statusMapper; 

	@Test
	public void generateQualityGateConditions_VerifyAttributes() {
		SonarQubeProjectStatus projectStatus = createSonarQubeProjectStatus(SonarQubeProjectStatusType.OK, 
				new SonarQubeCondition(SonarQubeProjectStatusType.OK.toString(), "METRICKEY", "LT", 2, 3, 1));
		
		mockMapSonarQubeProjectStatus(SonarQuestStatus.CLOSED);
		
		List<Condition> result = underTest.generateQualityGateConditions(projectStatus);
		
		assertEquals("LT", result.get(0).getComparator());
		assertEquals("METRICKEY", result.get(0).getMetricKey());
		assertEquals("METRICKEY is lower than 3.0", result.get(0).getTitle());
		assertTrue(SonarQuestStatus.CLOSED.equals(result.get(0).getStatus()));
	}
	
	@Test
	public void generateQualityGateConditions_NULL() {
		// Given
		SonarQubeProjectStatus projectStatus = new SonarQubeProjectStatus();
		
		// test
		List<Condition> result = underTest.generateQualityGateConditions(projectStatus);
		
		// verify
		assertNull(result);
	}
	
	@Test
	public void updateQualityGateConditionFromSonarQube_Verify_SetQualityGateRaid() {
		// Given
		Condition condition = new Condition("METRICKEY", "LT", 2, 3);
		SonarQubeProjectStatus projectStatus = createSonarQubeProjectStatus(SonarQubeProjectStatusType.OK, 
				new SonarQubeCondition(SonarQubeProjectStatusType.OK.toString(), "METRICKEY", "LT", 2, 3, 1));
		QualityGateRaid qualityGate = new QualityGateRaid();
		
		mockMapSonarQubeProjectStatus(SonarQuestStatus.CLOSED);
		mockFindByMetricKeyAndQualityGateRaid(condition);
		
		// test
		List<Condition> result = underTest.updateQualityGateConditionFromSonarQube(projectStatus, qualityGate);
		
		// verify
		assertEquals(qualityGate, result.get(0).getQualityGateRaid());
	}
	
	
	
	// ----------------------- GIVEN ----------------------------------------------------------------------------
	private SonarQubeProjectStatus createSonarQubeProjectStatus(SonarQubeProjectStatusType status, SonarQubeCondition condition) {
		SonarQubeProjectStatus projectStatus = new SonarQubeProjectStatus();
		projectStatus.setStatus(status.toString());
		List<SonarQubeCondition> conditions =  new ArrayList<SonarQubeCondition>();
		conditions.add(condition);
		projectStatus.setConditions(conditions);
		return projectStatus;
	}
	
	private void mockMapSonarQubeProjectStatus(SonarQuestStatus status) {
		when(statusMapper.mapSonarQubeProjectStatus(any(SonarQubeProjectStatusType.class))).thenReturn(status);
	}
	
	private void mockFindByMetricKeyAndQualityGateRaid(Condition condition) {
		when(conditionRepositoryMock.findByMetricKeyAndQualityGateRaid(anyString(), any(QualityGateRaid.class))).thenReturn(condition);
	}
}

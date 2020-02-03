package com.viadee.sonarquest.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.viadee.sonarquest.entities.QualityRaid;
import com.viadee.sonarquest.entities.ConditionTask;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.externalressources.SonarQubeCondition;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatus;
import com.viadee.sonarquest.repositories.QualityRaidRepository;
import com.viadee.sonarquest.repositories.ConditionTaskRepository;
import com.viadee.sonarquest.rules.SonarQubeStatusMapper;
import com.viadee.sonarquest.rules.SonarQuestStatus;

@RunWith(SpringRunner.class)
public class CodeQualityRaidServiceTest {
	
	@TestConfiguration	
	static class CodeQualityRaidServiceTestContextConfiguration {
		@Bean
		public QualityRaidService qualityRaidService() {
			return new QualityRaidService();
		}
	}
	
	@TestConfiguration
	static class SonarQubeStatusMapperTestContextConfiguration {
		@Bean
		public SonarQubeStatusMapper statusMapper() {
			return new SonarQubeStatusMapper();
		}
	}
	
	@Autowired 
	private QualityRaidService qualityRaidService;
	
	@MockBean
	private WorldService worldService;
	
	@MockBean
	private ExternalRessourceService externalResourceService;
	
	@MockBean
	private QualityRaidRepository qualitiyRaidRepository;
	
	@MockBean
	private ConditionTaskRepository qualityTaskService;
	
	@Before
	public void setUp() {
		Mockito.when(qualitiyRaidRepository.save(any(QualityRaid.class))).thenReturn(createQualityRaidData());
		Mockito.when(qualityTaskService.save(any(ConditionTask.class))).thenReturn(new ConditionTask());
	}
	
	@Test
	public void test_saveQualityRaid() {
		QualityRaid toSaveObject = createQualityRaidData();
		QualityRaid result = qualityRaidService.save(toSaveObject);
		assertThat(toSaveObject.getDescription()).isEqualTo(result.getDescription());
	}
	
	@Test
	public void test_createQualityRaid_WorldNull() {
		// given
		givenWorld(null);
		// Test
		QualityRaid result = qualityRaidService.createQualityRaid(null, null, null, null);
		// verify
		assertNull(result);
	}
	
	@Test
	public void test_generateQualityRaidTasks_VerifyConditionStatus_Closed() {
		// given
		World world = createWorldData();
		List<SonarQubeCondition> conditions =  new ArrayList<SonarQubeCondition>();
		conditions.add(new SonarQubeCondition("OK", "METRICKEY", "LT", 2, 3, 1));
		
		SonarQubeProjectStatus projectStatus = createSonarQubeProjectStatus("OK", conditions);
		givenWorld(world);
		givenExternalResource(projectStatus);
		// test
		List<ConditionTask> result = qualityRaidService.generateQualityRaidTasks(projectStatus);
		// verify
		ConditionTask task = result.get(0);
		assertTrue(SonarQuestStatus.CLOSED.equals(task.getStatus()));
	}
	
	// ----------------------- GIVEN ----------------------------------------------------------------------------
	private void givenWorld(World world) {
		Mockito.when(worldService.findById(any())).thenReturn(world);
	}
	
	private void givenExternalResource(SonarQubeProjectStatus sonarQubeProjectStatus) {
		Mockito.when(externalResourceService.generateSonarQubeProjectStatusFromWorld(any(World.class))).thenReturn(sonarQubeProjectStatus);
	}
	
	// ------------------------ TESTDATA SECTION ------------------------------------------------------------------- 
	
	private QualityRaid createQualityRaidData() {
		QualityRaid qualityRaid = new QualityRaid();
		qualityRaid.setWorld(createWorldData());
		return qualityRaid;
	}
	
	private World createWorldData() {
		return new World("AssertJ fluent assertions", "org.assertj:assertj-core", true, true);
	}
	
	private SonarQubeProjectStatus createSonarQubeProjectStatus(String status, List<SonarQubeCondition> conditions) {
		SonarQubeProjectStatus projectStatus = new SonarQubeProjectStatus();
		projectStatus.setStatus(status);
		projectStatus.setConditions(conditions);
		return projectStatus;
	}
	
}

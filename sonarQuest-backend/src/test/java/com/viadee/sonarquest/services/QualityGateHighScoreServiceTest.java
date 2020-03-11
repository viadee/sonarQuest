package com.viadee.sonarquest.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.data.domain.Sort.Direction;

import com.viadee.sonarquest.configurations.JpaAuditingConfiguration;
import com.viadee.sonarquest.entities.QualityGateRaid;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatusType;

@RunWith(MockitoJUnitRunner.class)
public class QualityGateHighScoreServiceTest {

	@PersistenceUnit
	private EntityManagerFactory entityManagerFactory;

	@Spy
	@InjectMocks
	private QualityGateHighScoreService underTest;

	@Mock
	private JpaAuditingConfiguration auditConfigMock;

	@Test
	public void calculateActualScore_WithStatusError_ActualScoreIsNull() {
		QualityGateRaid raid = givenQualityGateRaid(SonarQubeProjectStatusType.ERROR);

		Long highScoreResult = underTest.calculateActualScore(raid);
		assertNull(highScoreResult);
	}
	
	@Test
	public void calculateActualScore_WithoutRaid_ActualScoreIsNull() {
		Long highScoreResult = underTest.calculateActualScore(null);
		assertNull(highScoreResult);
	}

	@Test
	public void calculateActualScore_WithStatusOK_LastErrorBefore2Days_ActualScoreIs2Days() {
		QualityGateRaid raid = givenQualityGateRaid(SonarQubeProjectStatusType.OK);
		mockFindByRaidIdAndStatusError(givenAuditQualityGateRaid(SonarQubeProjectStatusType.ERROR, LocalDate.now().minusDays(2)));

		Long highScoreResult = underTest.calculateActualScore(raid);

		assertEquals(2l, highScoreResult.longValue());
	}

	@Test
	public void calculateActualScore_WithStatusOK_WithoutLastError_WithFirstOkBefore2Days_ActualScoreIs2Days() {
		QualityGateRaid raid = givenQualityGateRaid(SonarQubeProjectStatusType.OK);
		mockFindByRaidIdAndStatusError(null);
		mockFindByRaidIdAndStatusOK(givenAuditQualityGateRaid(SonarQubeProjectStatusType.OK, LocalDate.now().minusDays(2)));

		Long highScoreResult = underTest.calculateActualScore(raid);
		
		assertEquals(2l, highScoreResult.longValue());
	}

	@Test
	public void calculateActualScore_WithStatusOK_WithoutLastError_AND_WithoutFirstOk_ActualScoreIsNull() {
		QualityGateRaid raid = givenQualityGateRaid(SonarQubeProjectStatusType.OK);
		mockFindByRaidIdAndStatusError(null);
		mockFindByRaidIdAndStatusOK(null);
		
		Long highScoreResult = underTest.calculateActualScore(raid);

		assertNull(highScoreResult);
	}
	
	@Test
	public void updateQualityGateHighScore_ActualScoreIsNull_DONT_UpdateHighScore() {
		// Given QualityGateRaid with status=Error and HighScore with 20 Points
		java.sql.Date lastHighScoreDate = java.sql.Date.valueOf( LocalDate.now().minusDays(30) );
		long lastHighScorePoints = 20l;
		
		QualityGateRaid raid = givenQualityGateRaid(SonarQubeProjectStatusType.ERROR);
		raid.setScorePoints(lastHighScorePoints);
		raid.setScoreDay(lastHighScoreDate);
		
		underTest.updateQualityGateHighScore(raid);
		
		assertEquals(lastHighScoreDate, raid.getScoreDay());
		assertEquals(lastHighScorePoints, raid.getScorePoints().longValue());
	}
	
	@Test
	public void updateQualityGateHighScore_ActualScoreIsHigherThanHighScore_UpdateHighScore() {
		// HighScore with 1 Point
		java.sql.Date lastHighScoreDate = java.sql.Date.valueOf( LocalDate.now().minusDays(30) );
		long lastHighScorePoints = 1l;
		
		QualityGateRaid raid = givenQualityGateRaid(SonarQubeProjectStatusType.OK);
		raid.setScorePoints(lastHighScorePoints);
		raid.setScoreDay(lastHighScoreDate);
		
		long daysWithoutError = 12;
		mockFindByRaidIdAndStatusError(givenAuditQualityGateRaid(SonarQubeProjectStatusType.ERROR, LocalDate.now().minusDays(daysWithoutError)));
		
		underTest.updateQualityGateHighScore(raid);
		
		// Verify QualityGateRaid update with actual score
		assertEquals(java.sql.Date.valueOf(LocalDate.now()), raid.getScoreDay());
		assertEquals(daysWithoutError, raid.getScorePoints().longValue());
	}
	

// ----------------------- GIVEN -----------------------------------------------------------------------------------
	private QualityGateRaid givenQualityGateRaid(SonarQubeProjectStatusType projectStatusType) {
		QualityGateRaid qualityGateRaid = new QualityGateRaid();
		qualityGateRaid.setId(1L);
		qualityGateRaid.setSonarQubeStatus(projectStatusType);
		return qualityGateRaid;
	}

	private QualityGateRaid givenAuditQualityGateRaid(SonarQubeProjectStatusType status, LocalDate date) {
		QualityGateRaid gate = givenQualityGateRaid(status);
		Date updated = Date.from(date.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
		gate.setUpdated(updated);
		return gate;
	}

// ----------------------- MOCK -----------------------------------------------------------------------------------
	
	private void mockFindByRaidIdAndStatusError(QualityGateRaid qualityGateRaid) {
		doReturn(qualityGateRaid).when(underTest).findQualityGateRaidAuditByIdAndStatusOrderByUpdated(anyLong(), eq(SonarQubeProjectStatusType.ERROR),
				any(Direction.class));
	}
	
	private void mockFindByRaidIdAndStatusOK(QualityGateRaid qualityGateRaid) {
		doReturn(qualityGateRaid).when(underTest).findQualityGateRaidAuditByIdAndStatusOrderByUpdated(anyLong(), eq(SonarQubeProjectStatusType.OK),
				any(Direction.class));
	}
}

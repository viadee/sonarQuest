package com.viadee.sonarquest.services;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.argThat;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.LocalDate;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.viadee.sonarquest.entities.QualityGateRaid;
import com.viadee.sonarquest.entities.QualityGateRaidRewardHistory;
import com.viadee.sonarquest.externalressources.SonarQubeProjectStatusType;
import com.viadee.sonarquest.repositories.QualityGateRaidRewardHistoryRepository;

@RunWith(MockitoJUnitRunner.class)
public class QualityGateRaidRewardHistoryServiceTest {
	
	@Mock
	private QualityGateRaidRewardHistoryRepository qualityGateRaidHistoryRepositoryMock;
	
	@InjectMocks 
	private QualityGateRaidRewardHistoryService underTest;
	
	// -------------------- Test: CREATE NEW ENTRY: No historyEntryExits -------------------------
	@Test
	public void updateQualityGateRaidRewardHistory_NoHistoryEntryExists_CreateNewEntry_WithoutBonus() {
		// given
		QualityGateRaid gate = givenQualityGateRaid(SonarQubeProjectStatusType.ERROR, 0, 0);
		givenFindTopByRaidIdAndStatusDate(null);

		underTest.updateQualityGateRaidRewardHistory(gate);
		
		verify(qualityGateRaidHistoryRepositoryMock).save(argThat(saveArgumentMatcher(SonarQubeProjectStatusType.ERROR, 0l, 0l)));
	}
	@Test
	public void updateQualityGateRaidRewardHistory_NoHistoryEntryExists_CreateNewEntry_WithBonus() {
		// given
		QualityGateRaid gate = givenQualityGateRaid(SonarQubeProjectStatusType.OK, 2, 2);
		givenFindTopByRaidIdAndStatusDate(null);

		underTest.updateQualityGateRaidRewardHistory(gate);
		
		verify(qualityGateRaidHistoryRepositoryMock).save(argThat(saveArgumentMatcher(SonarQubeProjectStatusType.OK, 2l, 2l)));
	}
	
	// -------------------- Test: CREATE NEW ENTRY: Last HistoryEntryExits from yesterday or later -------------------------
	@Test
	public void updateQualityGateRaidRewardHistory_LastHistoryEntryExists_GateIsErrorStatus_CreateNewEntry_WithoutBonus() {
		// given
		QualityGateRaid gate = givenQualityGateRaid(SonarQubeProjectStatusType.ERROR, 2, 2);
		QualityGateRaidRewardHistory lastHistory = givenQualityGateRaidStatusHistory(LocalDate.now().minusDays(1), SonarQubeProjectStatusType.ERROR, 0l, 0l);
		givenFindTopByRaidIdOrderByStatusDateDesc(lastHistory);

		underTest.updateQualityGateRaidRewardHistory(gate);
		
		verify(qualityGateRaidHistoryRepositoryMock).save(argThat(saveArgumentMatcher(SonarQubeProjectStatusType.ERROR, 0l, 0l)));
	}
	@Test
	public void updateQualityGateRaidRewardHistory_LastHistoryEntryExists_GateIsOKStatus_CreateNewEntry_AddingBonus() {
		// given
		QualityGateRaid gate = givenQualityGateRaid(SonarQubeProjectStatusType.OK, 2, 2);
		QualityGateRaidRewardHistory lastHistory = givenQualityGateRaidStatusHistory(LocalDate.now().minusDays(1), SonarQubeProjectStatusType.OK, 2l, 2l);
		givenFindTopByRaidIdOrderByStatusDateDesc(lastHistory);

		underTest.updateQualityGateRaidRewardHistory(gate);
		
		verify(qualityGateRaidHistoryRepositoryMock).save(argThat(saveArgumentMatcher(SonarQubeProjectStatusType.OK, 4l, 4l)));
	}
	
	// -------------------- Test: DONT CREATE NEW ENTRY: Last HistoryEntryExits from today -------------------------
	@Test
	public void updateQualityGateRaidRewardHistory_LastHistoryEntryExists_FromTODAY_GateIsOKStatus_DONT_CreateNewEntry() {
		// given
		QualityGateRaid gate = givenQualityGateRaid(SonarQubeProjectStatusType.OK, 2, 2);
		QualityGateRaidRewardHistory lastHistory = givenQualityGateRaidStatusHistory(LocalDate.now(), SonarQubeProjectStatusType.OK, 2l, 2l);
		givenFindTopByRaidIdOrderByStatusDateDesc(lastHistory);

		underTest.updateQualityGateRaidRewardHistory(gate);
		
		verify(qualityGateRaidHistoryRepositoryMock, never()).save(any(QualityGateRaidRewardHistory.class));
	}
	
	//	-------------------- Test: UPDATE ENTRY: -------------------------
	@Test
	public void updateQualityGateRaidRewardHistory_LastHistoryEntryExists_FromTODAY_And_GateStatusChangedToError_UpdateEntry() {
		// given
		QualityGateRaid gate = givenQualityGateRaid(SonarQubeProjectStatusType.ERROR, 2, 2);
		QualityGateRaidRewardHistory actualHistory = givenQualityGateRaidStatusHistory(LocalDate.now(), SonarQubeProjectStatusType.OK, 2l, 2l);
		givenFindTopByRaidIdOrderByStatusDateDesc(actualHistory);
		givenFindTopByRaidIdAndStatusDate(actualHistory);

		underTest.updateQualityGateRaidRewardHistory(gate);
		
		verify(qualityGateRaidHistoryRepositoryMock).save(argThat(saveArgumentMatcher(SonarQubeProjectStatusType.ERROR, 0l, 0l)));
	}
	
	@Test
	public void updateQualityGateRaidRewardHistory_LastHistoryEntryExists_FromTODAY_And_GateStatusIsOK_Dont_Update() {
		// given
		QualityGateRaid gate = givenQualityGateRaid(SonarQubeProjectStatusType.OK, 2, 2);
		QualityGateRaidRewardHistory actualHistory = givenQualityGateRaidStatusHistory(LocalDate.now(), SonarQubeProjectStatusType.OK, 2l, 2l);
		givenFindTopByRaidIdOrderByStatusDateDesc(actualHistory);
		givenFindTopByRaidIdAndStatusDate(actualHistory);

		underTest.updateQualityGateRaidRewardHistory(gate);
		
		verify(qualityGateRaidHistoryRepositoryMock, never()).save(any(QualityGateRaidRewardHistory.class));
	}

// ----------------------- GIVEN -----------------------------------------------------------------------------------

	private QualityGateRaid givenQualityGateRaid(SonarQubeProjectStatusType projectStatusType, long gold, long xp) {
		QualityGateRaid qualityGateRaid = new QualityGateRaid();
		qualityGateRaid.setId(1L);
		qualityGateRaid.setSonarQubeStatus(projectStatusType);
		qualityGateRaid.setGold(gold);
		qualityGateRaid.setXp(xp);
		return qualityGateRaid;
	}
	
	private QualityGateRaidRewardHistory givenQualityGateRaidStatusHistory(LocalDate date, SonarQubeProjectStatusType status, long gold, long xp) {
		return new QualityGateRaidRewardHistory(Date.valueOf(date), status, null, gold, xp);
	}
	
	private void givenFindTopByRaidIdAndStatusDate(QualityGateRaidRewardHistory historyEntry) {
		when(qualityGateRaidHistoryRepositoryMock.findTopByRaidIdAndStatusDate(anyLong(), any())).thenReturn(historyEntry);
	}

	private void givenFindTopByRaidIdOrderByStatusDateDesc(QualityGateRaidRewardHistory historyEntry) {
		when(qualityGateRaidHistoryRepositoryMock.findTopByRaidIdOrderByStatusDateDesc(anyLong())).thenReturn(historyEntry);
	}
	
	private ArgumentMatcher<QualityGateRaidRewardHistory> saveArgumentMatcher(SonarQubeProjectStatusType status, Long gold, Long xp) {
		ArgumentMatcher<QualityGateRaidRewardHistory> matcher = new ArgumentMatcher<QualityGateRaidRewardHistory>() {
			@Override
			public boolean matches(Object argument) {
				QualityGateRaidRewardHistory history = (QualityGateRaidRewardHistory) argument;
				return status.equals(history.getSonarQubeStatus()) &&
						gold.equals(history.getGold()) &&
						xp.equals(history.getXp());
			}
		};
		return matcher;
	}
}

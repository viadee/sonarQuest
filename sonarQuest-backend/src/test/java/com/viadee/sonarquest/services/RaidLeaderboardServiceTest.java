package com.viadee.sonarquest.services;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.argThat;
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

import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.entities.RaidLeaderboard;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.RaidLeaderboardRepository;

@RunWith(MockitoJUnitRunner.class)
public class RaidLeaderboardServiceTest {

	@InjectMocks
	RaidLeaderboardService underTest;

	@Mock
	private RaidLeaderboardRepository raidLeaderboardRepMock;

	@Test
	public void updateLeaderboard_verifySavedEntry() {
		// GET
		Task task = new Task();
		task.setGold(5l);
		task.setXp(5l);

		User user = new User();
		Quest quest = new Quest();
		quest.setRaid(new Raid());
		task.setParticipation(new Participation(quest, user));
		task.setQuest(quest);

		when(raidLeaderboardRepMock.findTopByRaidAndUser(any(Raid.class), any(User.class))).thenReturn(null);

		// TEST
		underTest.updateLeaderboard(task);

		// VERIFY
		verify(raidLeaderboardRepMock).save(argThat(saveArgumentMatcher(1l, 5l, 5l, Date.valueOf(LocalDate.now()))));
	}
	
	private ArgumentMatcher<RaidLeaderboard> saveArgumentMatcher(Long scoreSolvedTasks, Long gold, Long xp, Date date) {
		ArgumentMatcher<RaidLeaderboard> matcher = new ArgumentMatcher<RaidLeaderboard>() {
			@Override
			public boolean matches(Object argument) {
				RaidLeaderboard history = (RaidLeaderboard) argument;
				return scoreSolvedTasks.equals(history.getScoreSolvedTasks()) && gold.equals(history.getScoreGold())
						&& xp.equals(history.getScoreXp());
			}
		};
		return matcher;
	}

}

package com.viadee.sonarquest.services;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.rules.SonarQuestStatus;

@RunWith(MockitoJUnitRunner.class)
public class RaidServiceTest {
	
    @InjectMocks
    private RaidService underTest;
	
	@Test
	public void calculateFinishedTaskHistory() {
		// Given
		LocalDate todayLocalDate = LocalDate.now();
		Date yesterday = Date.valueOf(todayLocalDate.minusDays(1));
		Date today = Date.valueOf(todayLocalDate);
		Date dateFor4Days = Date.valueOf(todayLocalDate.minusDays(4));
		
		Task openTask = new Task.TaskBuilder().status(SonarQuestStatus.OPEN).build();
		Task solvedTaskToday = new Task.TaskBuilder().status(SonarQuestStatus.SOLVED).enddate(today).build();
		Task solvedTaskToday2 = new Task.TaskBuilder().status(SonarQuestStatus.SOLVED).enddate(today).build();
		Task solvedTaskYesterday = new Task.TaskBuilder().status(SonarQuestStatus.SOLVED).enddate(yesterday).build();
		Task closedTaskFor4Days = new Task.TaskBuilder().status(SonarQuestStatus.CLOSED).enddate(dateFor4Days).build();
		
		Raid raid = givenRaidWithQuests(givenQuestWithTasks(openTask, solvedTaskToday, solvedTaskToday2, solvedTaskYesterday, closedTaskFor4Days));
		
		Map<Date, Long> map = underTest.calculateFinishedTaskHistoryFromRaid(LocalDate.now().minusDays(7), todayLocalDate, raid);
		
		assertTrue(map.get(yesterday).equals(1l));
		assertTrue(map.get(today).equals(2l));
		assertTrue(map.get(dateFor4Days).equals(1l));
		
		assertEquals(8, map.size());
	}
	
	private Raid givenRaidWithQuests(Quest... quests) {
		Raid raid = new Raid();
		List<Quest> raidQuests = new ArrayList<Quest>();
		for (Quest quest : quests) {
			raidQuests.add(quest);
		}
		raid.setQuests(raidQuests);
		return raid;
	}
	
	private Quest givenQuestWithTasks(Task... tasks) {
		Quest quest = new Quest();
		List<Task> questTasks = new ArrayList<Task>();
		for (Task task : tasks) {
			questTasks.add(task);
		}
		
		quest.setTasks(questTasks);
		return quest;
	}

}

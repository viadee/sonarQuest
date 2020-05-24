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

import com.viadee.sonarquest.dto.SolvedTaskHistoryDTO;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.rules.SonarQuestStatus;

@RunWith(MockitoJUnitRunner.class)
public class SolvedTaskHistoryServiceTest {

	@InjectMocks
	private SolvedTaskHistoryService underTest;

	@Test
	public void calculateFinishedTaskProgress() {
		// Given: Solved Tasks: 2 today, 1 yesterday, 1 for 4 Days 
		LocalDate todayLocalDate = LocalDate.now();
		Date yesterday = Date.valueOf(todayLocalDate.minusDays(1));
		Date today = Date.valueOf(todayLocalDate);
		Date dateFor4Days = Date.valueOf(todayLocalDate.minusDays(4));

		Task openTask = new Task.TaskBuilder().status(SonarQuestStatus.OPEN).build();
		Task solvedTaskToday = new Task.TaskBuilder().status(SonarQuestStatus.SOLVED).enddate(today).build();
		Task solvedTaskToday2 = new Task.TaskBuilder().status(SonarQuestStatus.SOLVED).enddate(today).build();
		Task solvedTaskYesterday = new Task.TaskBuilder().status(SonarQuestStatus.SOLVED).enddate(yesterday).build();
		Task closedTaskFor4Days = new Task.TaskBuilder().status(SonarQuestStatus.CLOSED).enddate(dateFor4Days).build();
		List<Task> tasks = givenTasks(openTask, solvedTaskToday, solvedTaskToday2, solvedTaskYesterday,
				closedTaskFor4Days);

		Map<Date, Long> map = underTest.mapSolvedTasks(tasks);

		assertTrue(map.get(yesterday).equals(1l));
		assertTrue(map.get(today).equals(2l));
		assertTrue(map.get(dateFor4Days).equals(1l));
		// Verify gaps inserted
		assertEquals(5, map.size());
	}

	@Test
	public void getSolvedTaskProgress_verifyProgress() {
		// Given
		final LocalDate todayLocalDate = LocalDate.now();
		final Date today = Date.valueOf(todayLocalDate);
		final Date yesterday = Date.valueOf(todayLocalDate.minusDays(1));

		Task solvedTaskToday = new Task.TaskBuilder().status(SonarQuestStatus.SOLVED).enddate(today).build();
		Task closedTaskFor4Days = new Task.TaskBuilder().status(SonarQuestStatus.CLOSED).enddate(yesterday).build();

		List<Task> tasks = givenTasks(solvedTaskToday, closedTaskFor4Days);

		List<SolvedTaskHistoryDTO> result = underTest.getSolvedTaskHistory(tasks);

		// Verify
		assertTrue(result.get(0).getDate().equals(yesterday));
		assertEquals(1, result.get(0).getSolvedTasksForDay());
		assertEquals(1, result.get(0).getTotalSolvedTasks());

		assertTrue(result.get(1).getDate().equals(today));
		assertEquals(1, result.get(1).getSolvedTasksForDay());
		assertEquals(2, result.get(1).getTotalSolvedTasks());

	}

	private List<Task> givenTasks(Task... tasks) {
		List<Task> result = new ArrayList<Task>();
		for (Task task : tasks) {
			result.add(task);
		}
		return result;
	}

}

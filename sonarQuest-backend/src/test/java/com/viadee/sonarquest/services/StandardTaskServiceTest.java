package com.viadee.sonarquest.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.viadee.sonarquest.entities.StandardTask;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.externalressources.SonarQubeSeverity;
import com.viadee.sonarquest.repositories.StandardTaskRepository;
import com.viadee.sonarquest.rules.SonarQuestStatus;

@RunWith(MockitoJUnitRunner.class)
public class StandardTaskServiceTest {

    @Mock
    private StandardTaskRepository standardTaskRepository;

    @Mock
    private GratificationService gratificationService;

    @Mock
    private NamedParameterJdbcTemplate template;

    @InjectMocks
    private StandardTaskService standardTaskService;

    @Test
    public void testUpdateStandardTask() {
        StandardTask task = new StandardTask();
        task.setKey("Color of Magic!");
        task.setStatus(SonarQuestStatus.OPEN);
        when(standardTaskRepository.save(Matchers.any(StandardTask.class))).thenReturn(task);
        when(standardTaskRepository.saveAndFlush(Matchers.any(StandardTask.class))).thenReturn(task);
        when(standardTaskRepository.findByKey(task.getKey())).thenReturn(task);
        when(template.queryForObject(Matchers.anyString(),
                Matchers.any(SqlParameterSource.class),
                Matchers.<Class<String>>any())).thenReturn("OPEN");

        task = standardTaskService.updateStandardTask(task);

        assertEquals(SonarQuestStatus.OPEN, task.getStatus());

        // case: existing created task -> no external changes
        final StandardTask createdStandardTask = new StandardTask();
        createdStandardTask.setKey("createdStandardTask");
        createdStandardTask.setStatus(SonarQuestStatus.OPEN);

        when(standardTaskRepository.findByKey(createdStandardTask.getKey())).thenReturn(createdStandardTask);
        standardTaskService.updateStandardTask(createdStandardTask);

        assertEquals(SonarQuestStatus.OPEN, createdStandardTask.getStatus());

        // case: existing created task -> no external changes
    }

    @Test
    public void testGetLastState() throws Exception {
        StandardTask task = new StandardTask();
        SonarQuestStatus lastState = standardTaskService.getLastState(task);
        assertEquals(SonarQuestStatus.OPEN, lastState);
    }

	@Test
	public void testFindByWorld() throws Exception {
		// Given
		World world = new World();
		List<StandardTask> unsortedTasks = new ArrayList<>();
		unsortedTasks.add(mockStandardTask("BLOCKER"));
		unsortedTasks.add(mockStandardTask("MAJOR"));
		unsortedTasks.add(mockStandardTask("CRITICAL"));
		unsortedTasks.add(mockStandardTask("MAJOR"));
		when(standardTaskService.findByWorld(world)).thenReturn(unsortedTasks);
		// When
		List<StandardTask> tasks = standardTaskService.findByWorld(world);
		// Then
		assertEquals(SonarQubeSeverity.BLOCKER.name(), tasks.get(0).getSeverity());
		assertEquals(SonarQubeSeverity.CRITICAL.name(), tasks.get(1).getSeverity());
		assertEquals(SonarQubeSeverity.MAJOR.name(), tasks.get(2).getSeverity());
		assertEquals(SonarQubeSeverity.MAJOR.name(), tasks.get(3).getSeverity());
	}

	private StandardTask mockStandardTask(String severity) {
		StandardTask task = new StandardTask();
		task.setSeverity(severity);
		return task;
	}

}

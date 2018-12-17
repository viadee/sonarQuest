package com.viadee.sonarquest.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.viadee.sonarquest.entities.StandardTask;
import com.viadee.sonarquest.repositories.StandardTaskRepository;
import com.viadee.sonarquest.rules.SonarQuestStatus;
import com.viadee.sonarquest.services.GratificationService;
import com.viadee.sonarquest.services.StandardTaskService;

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

}

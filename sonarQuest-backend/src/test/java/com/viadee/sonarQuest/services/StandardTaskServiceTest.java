package com.viadee.sonarQuest.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.repositories.StandardTaskRepository;
import com.viadee.sonarQuest.rules.SonarQuestStatus;

@RunWith(MockitoJUnitRunner.class)
public class StandardTaskServiceTest {

    @Mock
    private StandardTaskRepository standardTaskRepository;

    @Mock
    private GratificationService gratificationService;

    @InjectMocks
    private StandardTaskService standardTaskService;

    @Test
    public void testUpdateStandardTask() {

        // case: new task
        final StandardTask newStandardTask = new StandardTask();
        newStandardTask.setKey("newStandardTask");

        when(standardTaskRepository.findByKey(newStandardTask.getKey())).thenReturn(null);

        standardTaskService.updateStandardTask(newStandardTask);

        assertEquals(SonarQuestStatus.CREATED.getText(), newStandardTask.getStatus());

        // case: existing created task -> no external changes
        final StandardTask createdStandardTask = new StandardTask();
        createdStandardTask.setKey("createdStandardTask");
        createdStandardTask.setStatus(SonarQuestStatus.CREATED.getText());

        when(standardTaskRepository.findByKey(createdStandardTask.getKey())).thenReturn(createdStandardTask);

        standardTaskService.updateStandardTask(createdStandardTask);

        assertEquals(SonarQuestStatus.CREATED.getText(), createdStandardTask.getStatus());

        // case: existing created task -> no external changes
    }
}

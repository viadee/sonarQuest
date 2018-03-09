package com.viadee.sonarQuest.services;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.viadee.sonarQuest.constants.TaskStates;
import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.repositories.StandardTaskRepository;

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

        assertEquals(TaskStates.CREATED, newStandardTask.getStatus());

        // case: existing created task -> no external changes
        final StandardTask createdStandardTask = new StandardTask();
        createdStandardTask.setKey("createdStandardTask");
        createdStandardTask.setStatus(TaskStates.CREATED);

        when(standardTaskRepository.findByKey(createdStandardTask.getKey())).thenReturn(createdStandardTask);

        standardTaskService.updateStandardTask(createdStandardTask);

        assertEquals(TaskStates.CREATED, createdStandardTask.getStatus());

        // case: existing created task -> no external changes
    }
}

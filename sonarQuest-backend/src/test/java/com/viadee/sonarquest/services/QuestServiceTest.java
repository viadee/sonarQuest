package com.viadee.sonarquest.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.viadee.sonarquest.dto.ProgressDTO;
import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.ParticipationRepository;
import com.viadee.sonarquest.repositories.QuestRepository;
import com.viadee.sonarquest.rules.SonarQuestStatus;

@RunWith(MockitoJUnitRunner.class)
public class QuestServiceTest {

    @Mock
    private QuestRepository questRepository;

    @Mock
    private ParticipationRepository participationRepository;

    @InjectMocks
    private QuestService questService;

    @Test
    public void testGetAllQuestsForWorldAndDeveloper() {

        // create mock developer
        final User mockDeveloper = new User();
        mockDeveloper.setUsername("mock");

        // create mock world
        final World mockWorld = new World();

        // create mock quests
        final Quest mockQuest1 = new Quest();
        final Quest mockQuest2 = new Quest();
        final Quest mockQuest3 = new Quest();
        mockQuest1.setTitle("mockQuest1");
        mockQuest2.setTitle("mockQuest2");
        mockQuest3.setTitle("mockQuest3");
        mockQuest1.setWorld(mockWorld);
        mockQuest2.setWorld(mockWorld);
        mockQuest3.setWorld(mockWorld);

        final List<Quest> mockQuests = new ArrayList<>();
        mockQuests.add(mockQuest1);
        mockQuests.add(mockQuest2);
        mockQuests.add(mockQuest3);

        // create mock participations
        final Participation mockParticipation1 = new Participation(mockQuest1, mockDeveloper);
        final Participation mockParticipation2 = new Participation(mockQuest2, mockDeveloper);
        final List<Participation> mockParticipations = new ArrayList<>();
        mockParticipations.add(mockParticipation1);
        mockParticipations.add(mockParticipation2);

        // init mock repos
        when(participationRepository.findByUser(mockDeveloper)).thenReturn(mockParticipations);
        when(questRepository.findByWorld(mockWorld)).thenReturn(mockQuests);

        // call method to be tested
        final List<List<Quest>> result = questService.getAllQuestsForWorldAndUser(mockWorld, mockDeveloper);

        // verify result
        assertTrue(result.get(0).contains(mockQuest1));
        assertTrue(result.get(0).contains(mockQuest2));
        assertTrue(result.get(1).contains(mockQuest3));
    }
    
    @Test
    public void calculateQuestProgress() {
    	// given quest with tasks
    	Quest quest = new Quest();
    	
    	List<Task> tasks = new ArrayList<Task>();
    	tasks.add(createTask(SonarQuestStatus.CLOSED));
    	tasks.add(createTask(SonarQuestStatus.CLOSED));
    	tasks.add(createTask(SonarQuestStatus.OPEN));
    	quest.setTasks(tasks);
    	
    	
    	// call test method
    	ProgressDTO progressResult = questService.calculateQuestProgress(quest);
    	
    	// verify
    	assertTrue(progressResult.getCalculatedProgress() == 67);
    	assertTrue(progressResult.getNumberOfVariable() == 1);
    	assertTrue(progressResult.getTotalAmount() == 3);
    }
    
    private Task createTask(SonarQuestStatus status) {
    	Task task = new Task();
    	task.setStatus(status);
    	return task;
    }
}

package com.viadee.sonarquest.services;

import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.viadee.sonarquest.dto.TaskDTO;
import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.StandardTask;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.ParticipationRepository;
import com.viadee.sonarquest.repositories.QuestRepository;
import com.viadee.sonarquest.services.QuestService;

@RunWith(MockitoJUnitRunner.class)
public class QuestServiceTest {

	@Mock
	private QuestRepository questRepository;

	@Mock
	private ParticipationRepository participationRepository;

	@Mock
	private TaskService taskService;

	@InjectMocks
	private QuestService questService;

	@Test
	public void testGetAllQuestsForWorldAndDeveloper() {

		// create mock developer
		final User mockDeveloper = new User();
		mockDeveloper.setUsername("mock");
		mockDeveloper.setMail("mock@mock.de");

		// create mock world
		final World mockWorld = new World();

		// create mock tasks
		final Task mockTask1 = new Task();
		mockTask1.setTitle("MockTask1");
		final Task mockTask2 = new Task();
		mockTask2.setTitle("MockTask2");
		final Task mockTask3 = new Task();
		mockTask3.setTitle("MockTask3");
		final List<Task> mocktasks = new ArrayList<Task>();
		mocktasks.add(mockTask1);
		mocktasks.add(mockTask2);
		mocktasks.add(mockTask3);

		// create mock tasks dtos
		final TaskDTO mockTaskDto1 = new TaskDTO();
		mockTaskDto1.setTitle("MockTask1");
		final TaskDTO mockTaskDto2 = new TaskDTO();
		mockTaskDto2.setTitle("MockTask2");
		final TaskDTO mockTaskDto3 = new TaskDTO();
		mockTaskDto3.setTitle("MockTask3");
		final List<TaskDTO> mocktaskDtos = new ArrayList<TaskDTO>();
		mocktaskDtos.add(mockTaskDto1);
		mocktaskDtos.add(mockTaskDto2);
		mocktaskDtos.add(mockTaskDto3);

		// create mock quests
		final Quest mockQuest1 = new Quest();
		final Quest mockQuest2 = new Quest();
		final Quest mockQuest3 = new Quest();
		mockQuest1.setTitle("mockQuest1");
		mockQuest1.setId(1L);
		mockQuest2.setTitle("mockQuest2");
		mockQuest2.setId(2L);
		mockQuest3.setTitle("mockQuest3");
		mockQuest3.setId(3L);
		mockQuest1.setWorld(mockWorld);
		mockQuest2.setWorld(mockWorld);
		mockQuest3.setWorld(mockWorld);
		mockQuest1.setTasks(mocktasks);
		mockQuest2.setTasks(mocktasks);
		mockQuest3.setTasks(mocktasks);

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
		when(taskService.getTasksForQuest(anyLong(), anyString())).thenReturn(mocktaskDtos);

		// call method to be tested
		final List<List<Quest>> result = questService.getAllQuestsForWorldAndUser(mockWorld, mockDeveloper);

		// verify result
		assertTrue(result.get(0).contains(mockQuest1));
		assertTrue(result.get(0).contains(mockQuest2));
		assertTrue(result.get(1).contains(mockQuest3));
	}

}

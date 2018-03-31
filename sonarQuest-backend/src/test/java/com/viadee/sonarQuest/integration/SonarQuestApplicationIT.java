package com.viadee.sonarQuest.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.viadee.sonarQuest.constants.TaskStates;
import com.viadee.sonarQuest.controllers.ParticipationController;
import com.viadee.sonarQuest.controllers.TaskController;
import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.QuestRepository;
import com.viadee.sonarQuest.repositories.TaskRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "simulateSonarServer=true")
public class SonarQuestApplicationIT {

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskController taskController;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ParticipationController participationController;


	@Test
	public void testWorldStructure() {
		final World sonarDungeon = worldRepository.findOne((long) 1);

		assertNotNull("Demo data not loaded properly", sonarDungeon);
		assertEquals("Demo data not loaded properly", Long.valueOf(1), sonarDungeon.getId());
		assertEquals("This is not the expected world data set", "SonarQuest", sonarDungeon.getName());
	}

	/**
	 * Walk through the whole gameplay on the backend with a developer perspective.
	 * This test assumes a spring environment including a simulated sonar server.
	 */
    @Test
	public void fromIssueToSolvedTask() {

		// Join in on a quest.
		// Add Participation sonarHero123, Quest1
		participationController.createParticipation((long) 1, (long) 1);
		final Quest epicQuest = questRepository.findOne((long) 1);
		final List<Participation> participations = epicQuest.getParticipations();
		assertEquals("createParticipation does not work (Quest)", "Quest1",
				participations.get(0).getQuest().getTitle());
		assertEquals("createParticipation does not work (Developer)", "sonarHero123",
				participations.get(0).getDeveloper().getUsername());

		// Get to work on issue 1
		Task issue1 = taskRepository.findOne((long) 1);
		assertNull("addParticipationToTask does not work (Quest)", issue1.getParticipation());
		taskController.addParticipation((long) 1, (long) 1, (long) 1);
		issue1 = taskRepository.findOne((long) 1);
		assertEquals("addParticipation does not work (Quest)", "Quest1",
				issue1.getParticipation().getQuest().getTitle());
		assertEquals("addParticipation does not work (Developer)", "sonarHero123",
				issue1.getParticipation().getDeveloper().getUsername());
		assertEquals("addParticipation does not work (Status)", TaskStates.PROCESSED, issue1.getStatus());

		// TODO: Solve an issue
    }

}

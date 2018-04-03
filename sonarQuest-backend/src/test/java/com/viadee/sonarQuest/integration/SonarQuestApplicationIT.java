package com.viadee.sonarQuest.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.List;

import com.viadee.sonarQuest.constants.QuestStates;
import com.viadee.sonarQuest.entities.*;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.services.ExternalRessourceService;
import com.viadee.sonarQuest.services.StandardTaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.viadee.sonarQuest.constants.TaskStates;
import com.viadee.sonarQuest.controllers.ParticipationController;
import com.viadee.sonarQuest.controllers.TaskController;
import com.viadee.sonarQuest.repositories.QuestRepository;
import com.viadee.sonarQuest.repositories.TaskRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.services.AdventureService;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "simulateSonarServer=true")
public class SonarQuestApplicationIT {

	@Autowired
	private ExternalRessourceService externalRessourceService;

	@Autowired
	private DeveloperRepository developerRepository;

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

	@Autowired
	private AdventureService adventureService;

	@Autowired
	private StandardTaskService standardTaskService;

	@Test(timeout = 1000) // There is hardly any data to fetch - this should be quick
	public void testWorldStructure() {
		final World sonarDungeon = worldRepository.findOne((long) 1);

		assertNotNull("Demo data not loaded properly", sonarDungeon);
		assertEquals("Demo data not loaded properly", Long.valueOf(1), sonarDungeon.getId());
		assertEquals("This is not the expected world data set", "SonarQuest", sonarDungeon.getName());
	}

	/**
	 * Walk through the participation on the backend with a developer perspective.
	 * This test assumes a spring environment including a simulated sonar server and
	 * database access.
	 */
	@Test(timeout = 1000000) // There is hardly any data to fetch - this should be quick, altough there are
							// write operations included
	public void developersCanParticipateInQuestsAndIssues() {
		// Join in on a quest.
		// Add Participation sonarWarrior, Quest1
		participationController.createParticipation((long) 1, (long) 3);
		Quest epicQuest = questRepository.findOne((long) 1);
		final List<Participation> participations = epicQuest.getParticipations();
		assertEquals("createParticipation does not work (Quest)", "Quest1",
				participations.get(1).getQuest().getTitle());
		assertEquals("createParticipation does not work (Developer)", "sonarWarrior",
				participations.get(1).getDeveloper().getUsername());

		// Get to work on issue 1
		Task issue1 = taskRepository.findOne((long) 1);
		assertNull("addParticipationToTask does not work (Quest)", issue1.getParticipation());
		taskController.addParticipation((long) 1, (long) 1, (long) 3);
		issue1 = taskRepository.findOne((long) 1);
		assertEquals("addParticipation does not work (Quest)", "Quest1",
				issue1.getParticipation().getQuest().getTitle());
		assertEquals("addParticipation does not work (Developer)", "sonarWarrior",
				issue1.getParticipation().getDeveloper().getUsername());
		assertEquals("addParticipation does not work (Status)", TaskStates.PROCESSED, issue1.getStatus());

		adventureService.updateAdventures(); // no effect expected
		Developer sonarWarrior = developerRepository.findOne((long) 3);
		assertEquals("Gratification during updateAdventures does not work (Gold)", Long.valueOf(18), sonarWarrior.getGold());
		assertEquals("Gratification during updateAdventures does not work (XP)", Long.valueOf(15), sonarWarrior.getXp());

		// Solve task (close issue 1)
		List<SonarQubeIssue> issueList = externalRessourceService.getIssuesForSonarQubeProject("15054");
		issueList.get(0).setStatus("CLOSED");

		final World sonarDungeon = worldRepository.findOne((long) 1);
		standardTaskService.updateStandardTasks(sonarDungeon);
		issue1 = taskRepository.findOne((long) 1);
		assertEquals("updateStandardTasks does not work (Title)", "2 duplicated blocks of code must be removed.", issue1.getTitle());
		assertEquals("updateStandardTasks does not work (Status)", TaskStates.SOLVED, issue1.getStatus());

		// Check Gratification
        // Gold = 18 (initial value) + 3 (Task) = 21
        // XP = 22 (inital value) + 2 (Task ) + 2 (Warrior) = 22
		sonarWarrior = developerRepository.findOne((long) 3);
		assertEquals("Gratification during updateStandardTasks does not work (Gold)", Long.valueOf(21), sonarWarrior.getGold());
		assertEquals("Gratification during updateStandardTasks does not work (XP)", Long.valueOf(22), sonarWarrior.getXp());

        // Check Status of epicQuest
        epicQuest = questRepository.findOne((long) 1);
        assertEquals("QuestStatus not correct", QuestStates.OPEN, epicQuest.getStatus());
	}
}

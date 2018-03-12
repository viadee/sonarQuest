package com.viadee.sonarQuest.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.viadee.sonarQuest.constants.AdventureStates;
import com.viadee.sonarQuest.constants.QuestStates;
import com.viadee.sonarQuest.constants.TaskStates;
import com.viadee.sonarQuest.controllers.AdventureController;
import com.viadee.sonarQuest.controllers.ParticipationController;
import com.viadee.sonarQuest.controllers.QuestController;
import com.viadee.sonarQuest.controllers.TaskController;
import com.viadee.sonarQuest.dtos.AdventureDto;
import com.viadee.sonarQuest.dtos.QuestDto;
import com.viadee.sonarQuest.dtos.SpecialTaskDto;
import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.AdventureRepository;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.repositories.QuestRepository;
import com.viadee.sonarQuest.repositories.TaskRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.services.ExternalRessourceService;
import com.viadee.sonarQuest.services.StandardTaskService;
import com.viadee.sonarQuest.services.WorldService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SonarQuestApplicationIT {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private WorldService worldService;

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private StandardTaskService standardTaskService;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskController taskController;

    @Autowired
    private QuestController questController;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ParticipationController participationController;

    @Autowired
    private AdventureController adventureController;

    @Autowired
    private AdventureRepository adventureRepository;

    @Test
    @DirtiesContext
    public void fromIssueToSolvedTask() {

        // Update Worlds
        final ExternalRessourceService mockedExternalRessourceService = mock(ExternalRessourceService.class);

        final World externalMockedWorld = new World("SonarDungeon", "com.viadee:sonarQuest", false);
        final List<World> externalMockedWorlds = new ArrayList<>();
        assertTrue(externalMockedWorlds.add(externalMockedWorld));
        when(mockedExternalRessourceService.generateWorldsFromSonarQubeProjects()).thenReturn(externalMockedWorlds);

        worldService.setExternalRessourceService(mockedExternalRessourceService);
        worldService.updateWorlds();
        final World sonarDungeon = worldRepository.findOne((long) 1);
        assertEquals("updateWorlds does not work", "SonarDungeon", sonarDungeon.getName());

        // Update StandardTasks
        StandardTask externalMockedStandardTask = new StandardTask("Issue1", TaskStates.OPEN, (long) 10, (long) 7, null,
                sonarDungeon, "12345", "component", "CRITICAL", "CODE_SMELL", 100);
        final List<StandardTask> externalMockedStandardTasks = new ArrayList<>();
        assertTrue(externalMockedStandardTasks.add(externalMockedStandardTask));
        when(mockedExternalRessourceService.generateStandardTasksFromSonarQubeIssuesForWorld(sonarDungeon))
                .thenReturn(externalMockedStandardTasks);

        standardTaskService.setExternalRessourceService(mockedExternalRessourceService);
        standardTaskService.updateStandardTasks(sonarDungeon);
        Task issue1 = taskRepository.findById(1);
        assertEquals("updateStandardTasks does not work (Title)", "Issue1", issue1.getTitle());
        assertEquals("updateStandardTasks does not work (Status)", TaskStates.CREATED, issue1.getStatus());

        // Create Sonderaufgabe
        final SpecialTaskDto sonderAufgabeDto = new SpecialTaskDto(null, "TestSonderaufgabe", null, (long) 11, (long) 7,
                null, null, "Löst diese Aufgabe möglichst schnell!");
        taskController.createTask(sonderAufgabeDto);
        Task sonderAufgabe = taskRepository.findById(2);
        assertEquals("createSpecialTask does not work (Title)", "TestSonderaufgabe", sonderAufgabe.getTitle());
        assertEquals("createSpecialTask does not work (Status)", TaskStates.CREATED, sonderAufgabe.getStatus());

        // Create Quest
        final QuestDto questDto = new QuestDto(null, "EpicQuest", "Dies ist eine epische Quest", null, (long) 20,
                (long) 30, null, null, null, null);
        questController.createQuest(questDto);
        Quest epicQuest = questRepository.findOne((long) 1);
        assertEquals("createQuest does not work (Title)", "EpicQuest", epicQuest.getTitle());
        assertEquals("createQuest does not work (Status)", QuestStates.OPEN, epicQuest.getStatus());

        // Add World to Quest
        questController.addWorld((long) 1, (long) 1);
        epicQuest = questRepository.findOne((long) 1);
        assertEquals("addWorld does not work", "SonarDungeon", epicQuest.getWorld().getName());

        // Create Adventure
        final AdventureDto adventureDto = new AdventureDto(null, "TestAbenteuer", "Dies ist ein gefährliches Abenteuer",
                null, (long) 30, (long) 40, null, null, null);
        adventureController.createAdventure(adventureDto);
        Adventure testAbenteuer = adventureRepository.findOne((long) 1);
        assertEquals("createAdventure does not work (Title)", "TestAbenteuer", testAbenteuer.getTitle());
        assertEquals("createAdventure does not work (Status)", AdventureStates.OPEN, testAbenteuer.getStatus());

        // Add issue1 to Quest
        taskController.addToQuest((long) 1, (long) 1);
        epicQuest = questRepository.findOne((long) 1);
        List<Task> epicQuestTasks = epicQuest.getTasks();
        assertEquals("addToQuest does not work (Title)", "Issue1", epicQuestTasks.get(0).getTitle());
        assertEquals("addToQuest does not work (Status)", TaskStates.OPEN, epicQuestTasks.get(0).getStatus());

        // Add Sonderaufgabe to Quest
        taskController.addToQuest((long) 2, (long) 1);
        epicQuest = questRepository.findOne((long) 1);
        epicQuestTasks = epicQuest.getTasks();
        assertEquals("addToQuest does not work (Title)", "TestSonderaufgabe", epicQuestTasks.get(1).getTitle());
        assertEquals("addToQuest does not work (Status)", TaskStates.OPEN, epicQuestTasks.get(1).getStatus());

        // Add Adventure to Quest
        adventureController.addQuest((long) 1, (long) 1);
        epicQuest = questRepository.findOne((long) 1);
        assertEquals("addQuestToAdventure does not work", "TestAbenteuer", epicQuest.getAdventure().getTitle());

        // Add sonarHero123 to TestAbenteuer
        adventureController.addDeveloper((long) 1, (long) 1);
        testAbenteuer = adventureRepository.findOne((long) 1);
        final Developer sonarHero123 = developerRepository.findById((long) 1);
        assertEquals("addDeveloperToAdventure does not work", "sonarHero123", sonarHero123);

        // Add Participation sonarHero123, EpicQuest
        participationController.createParticipation((long) 1, (long) 1);
        epicQuest = questRepository.findOne((long) 1);
        final List<Participation> participations = epicQuest.getParticipations();
        assertEquals("createParticipation does not work (Quest)", "EpicQuest",
                participations.get(0).getQuest().getTitle());
        assertEquals("createParticipation does not work (Developer)", "sonarHero123",
                participations.get(0).getDeveloper().getUsername());

        // Add Participation to issue1
        issue1 = taskRepository.findOne((long) 1);
        assertNull("addParticipationToTask does not work (Quest)", issue1.getParticipation());
        taskController.addParticipation((long) 1, (long) 1, (long) 1);
        issue1 = taskRepository.findOne((long) 1);
        assertEquals("addParticipation does not work (Quest)", "EpicQuest",
                issue1.getParticipation().getQuest().getTitle());
        assertEquals("addParticipation does not work (Developer)", "sonarHero123",
                issue1.getParticipation().getDeveloper().getUsername());
        assertEquals("addParticipation does not work (Status)", TaskStates.PROCESSED, issue1.getStatus());

        // Add Participation to sonderAufgabe
        sonderAufgabe = taskRepository.findOne((long) 2);
        assertNull("addParticipationToTask does not work (Quest)", sonderAufgabe.getParticipation());
        taskController.addParticipation((long) 2, (long) 1, (long) 1);
        sonderAufgabe = taskRepository.findOne((long) 2);
        assertEquals("addParticipation does not work (Quest)", "EpicQuest",
                sonderAufgabe.getParticipation().getQuest().getTitle());
        assertEquals("addParticipation does not work (Developer)", "sonarHero123",
                sonderAufgabe.getParticipation().getDeveloper().getUsername());
        assertEquals("addParticipation does not work (Status)", TaskStates.PROCESSED, sonderAufgabe.getStatus());

        // Set Sonderaufgabe to SOLVED
        taskController.solveSpecialTask((long) 2);
        sonderAufgabe = taskRepository.findOne((long) 2);
        assertEquals("solveSpecialTask does not work (Status)", TaskStates.SOLVED, sonderAufgabe.getStatus());

        // Check Gratification
        // Gold= 10 (Ausgangswert) + 11 (Sonderaufgabe) + 2 (Magier) + 1(Schwert) = 24
        // Xp = 5 (Ausgangswert) + 7 (Sonderaufgabe) = 12
        Developer developer = developerRepository.findOne((long) 1);
        assertEquals("Gratification does not work (Gold)", Long.valueOf(24), developer.getGold());
        assertEquals("Gratification does not work (XP)", Long.valueOf(12), developer.getXp());

        // Update StandardTasks (again)
        externalMockedStandardTask = new StandardTask("Issue1", TaskStates.SOLVED, (long) 10, (long) 7, null,
                sonarDungeon, "12345", "component", "CRITICAL", "CODE_SMELL", 100);
        externalMockedStandardTasks.clear();
        assertTrue(externalMockedStandardTasks.add(externalMockedStandardTask));
        when(mockedExternalRessourceService.generateStandardTasksFromSonarQubeIssuesForWorld(sonarDungeon))
                .thenReturn(externalMockedStandardTasks);

        standardTaskService.setExternalRessourceService(mockedExternalRessourceService);
        standardTaskService.updateStandardTasks(sonarDungeon);
        issue1 = taskRepository.findById(1);
        assertEquals("updateStandardTasks does not work (Title)", "Issue1", issue1.getTitle());
        assertEquals("updateStandardTasks does not work (Status)", TaskStates.SOLVED, issue1.getStatus());

        // Check Gratification
        // Gold= 24 (Ausgangswert) + 10 (Aufgabe) + 2(Magier) + 1(Schwert) + 20 (Quest)
        // + 30 (Abenteuer) = 87
        // Xp = 12 (Ausgangswert) + 7 (Aufgabe) + 30 (Quest) + 40 (Abenteuer) = 89
        developer = developerRepository.findOne((long) 1);
        assertEquals("Gratification does not work (Gold)", Long.valueOf(87), developer.getGold());
        assertEquals("Gratification does not work (XP)", Long.valueOf(89), developer.getXp());

        // Check Status of epicQuest and testAbenteuer
        epicQuest = questRepository.findOne((long) 1);
        testAbenteuer = adventureRepository.findOne((long) 1);
        assertEquals("QuestStatus not correct", QuestStates.SOLVED, epicQuest.getStatus());
        assertEquals("AdventureStatus not correct", AdventureStates.SOLVED, testAbenteuer.getStatus());

    }

}

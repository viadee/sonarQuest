package com.viadee.sonarQuest.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarQuest.constants.QuestStates;
import com.viadee.sonarQuest.controllers.ParticipationController;
import com.viadee.sonarQuest.controllers.TaskController;
import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.RoleName;
import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.repositories.QuestRepository;
import com.viadee.sonarQuest.repositories.TaskRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.rules.SonarQuestStatus;
import com.viadee.sonarQuest.services.AdventureService;
import com.viadee.sonarQuest.services.ExternalRessourceService;
import com.viadee.sonarQuest.services.RoleService;
import com.viadee.sonarQuest.services.StandardTaskService;
import com.viadee.sonarQuest.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SonarQuestApplicationIT {

    private static final String WORLD_NAME = "Discworld";

	private static final String QUEST_NAME = "The Colour of Magic";

	private static final String USERNAME = "Rincewind";

	@Autowired
    private ExternalRessourceService externalRessourceService;

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

    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleService roleService;

    @Test(timeout = 1000) // There is hardly any data to fetch - this should be quick
    public void testWorldStructure() {
        final World sonarDungeon = worldRepository.findOne(1L);

        assertNotNull("Demo data not loaded properly", sonarDungeon);
        assertEquals("Demo data not loaded properly", Long.valueOf(1), sonarDungeon.getId());
    }

    /**
     * Walk through the participation on the backend with a developer perspective. This test assumes a spring
     * environment including a simulated sonar server and database access.
     */
    @Transactional
    @Test(timeout = 1000000) // There is hardly any data to fetch - this should be quick, altough there are
                             // write operations included
    public void developersCanParticipateInQuestsAndIssues() {
    	World discWorld = createWorld();
        Quest magicQuest = createQuest(discWorld);
        User rinceWind = createUser(discWorld);
       
        Participation epicParticipation = participationController.createParticipation(() -> USERNAME, magicQuest.getId());
        final List<Participation> epicParticipations = new ArrayList<Participation>();
        epicParticipations.add(epicParticipation);        
        magicQuest.setParticipations(epicParticipations);
        magicQuest = questRepository.save(magicQuest);
        
        //User can participate in quest
        assertNotNull("quest without any participations", magicQuest.getParticipations());
        Participation activeParticipation = magicQuest.getParticipations().get(0);
        assertNotNull("participation not added to quest", activeParticipation);
        assertEquals("quest not properly mapped to quest participation", QUEST_NAME,
        		activeParticipation.getQuest().getTitle());
        assertEquals("user not properly mapped to participation", USERNAME,
        		activeParticipation.getUser().getUsername());

        //User can work on task
        StandardTask deathFromRetirementTask = createTask(discWorld, magicQuest);
        deathFromRetirementTask = (StandardTask) taskController.addParticipation(() -> USERNAME, deathFromRetirementTask.getId(), magicQuest.getId());
                
        assertNotNull("task without any participations", deathFromRetirementTask.getParticipation());
        Participation taskParticipation = deathFromRetirementTask.getParticipation();
        assertNotNull("participation not added to task", taskParticipation);
        assertEquals("user not properly mapped to task participation", USERNAME,
        		taskParticipation.getUser().getUsername());

//        deathFromRetirementTask.setStatus(SonarQuestStatus.SOLVED.getText());
//        standardTaskService.updateStandardTask(deathFromRetirementTask);
//        
//        rinceWind = userService.findByUsername(USERNAME);
//        assertEquals("reward: Gold not awarded to user", deathFromRetirementTask.getGold(), rinceWind.getGold());
//        assertEquals("reward: XP not awarded to user", deathFromRetirementTask.getXp(), rinceWind.getXp());
        }

	private StandardTask createTask(World discWorld, Quest magicQuest) {
		StandardTask task = new StandardTask();
		task.setGold(10L);
		task.setXp(20L);
		task.setKey("12345");
		task.setTitle("coercing Death out of his impromptu retirement");
        task.setWorld(discWorld);
        task.setQuest(magicQuest);
        task.setStatus(SonarQuestStatus.OPEN.getText());
        return taskRepository.save(task);
	}

	private User createUser(World discWorld) {
		User user = new User();
        user.setUsername(USERNAME);
        user.setPassword("test");
        user.setRole(roleService.findByName(RoleName.DEVELOPER));
        user.setCurrentWorld(discWorld);
        return userService.save(user);
	}

	private Quest createQuest(World epicWorld) {
		Quest quest = new Quest();
        quest.setTitle(QUEST_NAME);
        quest.setWorld(epicWorld);
        return questRepository.save(quest);
	}

	private World createWorld() {
		World world = new World();
    	world.setName(WORLD_NAME);
    	world.setActive(true);
    	return worldRepository.save(world);
	}
}

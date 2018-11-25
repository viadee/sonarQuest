package com.viadee.sonarQuest.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarQuest.controllers.ParticipationController;
import com.viadee.sonarQuest.controllers.TaskController;
import com.viadee.sonarQuest.entities.Level;
import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.RoleName;
import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.AvatarClassRepository;
import com.viadee.sonarQuest.repositories.AvatarRaceRepository;
import com.viadee.sonarQuest.repositories.LevelRepository;
import com.viadee.sonarQuest.repositories.QuestRepository;
import com.viadee.sonarQuest.repositories.TaskRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.rules.SonarQuestStatus;
import com.viadee.sonarQuest.services.LevelService;
import com.viadee.sonarQuest.services.RoleService;
import com.viadee.sonarQuest.services.StandardTaskService;
import com.viadee.sonarQuest.services.UserService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SonarQuestApplicationIT {

    private static final String USER_AVATAR_CLASS = "Magician";

    private static final String USER_AVATAR_RACE = "Human";

    private static final String WORLD_NAME = "Discworld";

    private static final String QUEST_NAME = "The Colour of Magic";

    private static final String USERNAME = "Rincewind";

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskController taskController;

    @Autowired
    private LevelRepository levelRepository;

    @Autowired
    private LevelService levelService;

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ParticipationController participationController;

    @Autowired
    private StandardTaskService standardTaskService;

    @Autowired
    private UserService userService;

    @Autowired
    private AvatarClassRepository avatarClassRepository;

    @Autowired
    private AvatarRaceRepository avatarRaceRepository;

    @Autowired
    private RoleService roleService;

    /**
     * Walk through the participation on the backend with a developer perspective. This test assumes a spring
     * environment including a simulated sonar server and database access.
     */
    @Transactional
    @Test(timeout = 1000000) // There is hardly any data to fetch - this should be quick, altough there are
                             // write operations included
    public void developersCanParticipateInQuestsAndIssues() {
        final World discWorld = createWorld();
        Quest magicQuest = createQuest(discWorld);
        User rinceWind = createUser(discWorld);
        createInitialLevel();

        // XXX add test for adventure-participation

        final Participation epicParticipation = participationController.createParticipation(() -> USERNAME,
                magicQuest.getId());
        final List<Participation> epicParticipations = new ArrayList<>();
        epicParticipations.add(epicParticipation);
        magicQuest.setParticipations(epicParticipations);
        magicQuest = questRepository.save(magicQuest);

        // User can participate in quest
        assertNotNull("quest without any participations", magicQuest.getParticipations());
        final Participation activeParticipation = magicQuest.getParticipations().get(0);
        assertNotNull("participation not added to quest", activeParticipation);
        assertEquals("quest not properly mapped to quest participation", QUEST_NAME,
                activeParticipation.getQuest().getTitle());
        assertEquals("user not properly mapped to participation", USERNAME,
                activeParticipation.getUser().getUsername());

        // User can work on task
        StandardTask coerceDeathOutOfRetirementTask = createTask(discWorld, magicQuest);
        coerceDeathOutOfRetirementTask = (StandardTask) taskController.addParticipation(() -> USERNAME,
                coerceDeathOutOfRetirementTask.getId(), magicQuest.getId());

        assertNotNull("task without any participations", coerceDeathOutOfRetirementTask.getParticipation());
        final Participation taskParticipation = coerceDeathOutOfRetirementTask.getParticipation();
        assertNotNull("participation not added to task", taskParticipation);
        assertEquals("user not properly mapped to task participation", USERNAME,
                taskParticipation.getUser().getUsername());

        coerceDeathOutOfRetirementTask.setStatus(SonarQuestStatus.SOLVED);
        standardTaskService.updateStandardTask(coerceDeathOutOfRetirementTask);

        rinceWind = userService.findByUsername(USERNAME);
        // since Rincewind is a magician he gets 2 extra gold coins for this task
        assertEquals("reward: Gold not awarded to user", Long.valueOf(12l), rinceWind.getGold());
        assertEquals("reward: XP not awarded to user", coerceDeathOutOfRetirementTask.getXp(), rinceWind.getXp());
    }

    private void createInitialLevel() {
        final Level level = new Level();
        level.setLevel(1);
        level.setMinXp(0l);
        level.setMaxXp(10l);
        levelRepository.save(level);
    }

    private StandardTask createTask(final World discWorld, final Quest magicQuest) {
        final StandardTask task = new StandardTask();
        task.setGold(10L);
        task.setXp(20L);
        task.setKey("12345");
        task.setTitle("coercing Death out of his impromptu retirement");
        task.setWorld(discWorld);
        task.setQuest(magicQuest);
        task.setStatus(SonarQuestStatus.OPEN);
        return taskRepository.save(task);
    }

    private User createUser(final World discWorld) {
        final User user = new User();
        user.setUsername(USERNAME);
        user.setPassword("test");
        user.setRole(roleService.findByName(RoleName.DEVELOPER));
        user.setAvatarClass(avatarClassRepository.findByName(USER_AVATAR_CLASS));
        user.setAvatarRace(avatarRaceRepository.findByName(USER_AVATAR_RACE));
        user.setCurrentWorld(discWorld);
        user.setLevel(levelService.getLevelByUserXp(0l));
        return userService.save(user);
    }

    private Quest createQuest(final World epicWorld) {
        final Quest quest = new Quest();
        quest.setTitle(QUEST_NAME);
        quest.setWorld(epicWorld);
        return questRepository.save(quest);
    }

    private World createWorld() {
        final World world = new World();
        world.setName(WORLD_NAME);
        world.setActive(true);
        return worldRepository.save(world);
    }
}

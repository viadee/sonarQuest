package com.viadee.sonarquest.integration;

import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertNull;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarquest.controllers.ParticipationController;
import com.viadee.sonarquest.controllers.TaskController;
import com.viadee.sonarquest.entities.Level;
import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.RoleName;
import com.viadee.sonarquest.entities.StandardTask;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.AvatarClassRepository;
import com.viadee.sonarquest.repositories.AvatarRaceRepository;
import com.viadee.sonarquest.repositories.LevelRepository;
import com.viadee.sonarquest.repositories.QuestRepository;
import com.viadee.sonarquest.repositories.StandardTaskRepository;
import com.viadee.sonarquest.repositories.TaskRepository;
import com.viadee.sonarquest.repositories.WorldRepository;
import com.viadee.sonarquest.rules.SonarQuestTaskStatus;
import com.viadee.sonarquest.services.LevelService;
import com.viadee.sonarquest.services.RoleService;
import com.viadee.sonarquest.services.StandardTaskService;
import com.viadee.sonarquest.services.UserService;

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

    @Autowired
    private StandardTaskRepository standardTaskRepository;

    /**
     * Walk through the participation on the backend with a developer perspective. This test assumes a spring
     * environment including a simulated sonar server and database access.
     */
    @Transactional
    @Test // There is hardly any data to fetch - this should be quick, altough there are
                             // write operations included
    public void developersCanParticipateInQuestsAndIssues() {
        assertTimeout(Duration.ofSeconds(1000), () -> {
            final World discWorld = createWorld();
            Quest magicQuest = createQuest(discWorld);
            User rinceWind;
            createUser(discWorld);
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

            coerceDeathOutOfRetirementTask.setStatus(SonarQuestTaskStatus.SOLVED);
            standardTaskService.updateStandardTask(coerceDeathOutOfRetirementTask);

            rinceWind = userService.findByUsername(USERNAME);
            // since Rincewind is a magician he gets 2 extra gold coins for this task
            assertEquals("reward: Gold not awarded to user", 12L, rinceWind.getGold());
            assertEquals("reward: XP not awarded to user", coerceDeathOutOfRetirementTask.getXp(), rinceWind.getXp());
        });

    }

    private void createInitialLevel() {
        final Level level = new Level();
        level.setLevelNumber(1);
        level.setMinXp(0L);
        level.setMaxXp(10L);
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
        task.setStatus(SonarQuestTaskStatus.OPEN);
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
        user.setLevel(levelService.getLevelByUserXp(0L));
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

    /**
     * Regression test for issue #162.
     */
    @Test
    @Transactional
    public void caseSensitiveTaskKeys() {

        final World discWorld = createWorld();
        final Quest magicQuest = createQuest(discWorld);

        final StandardTask task = new StandardTask();
        task.setGold(10L);
        task.setXp(20L);
        task.setKey("AWc3A2KEoXX3DuVBrVTc");
        task.setTitle("coercing Death out of his impromptu retirement");
        task.setWorld(discWorld);
        task.setQuest(magicQuest);
        task.setStatus(SonarQuestTaskStatus.OPEN);
        taskRepository.save(task);

        assertNull("unexpected Task", standardTaskRepository.findByKey("AWc3A2KEoXX3DuVBrVTC"));
        standardTaskRepository.findAll().forEach(t -> System.out.println(">>>" + t.getId() + " " + t.getKey()));
        Assertions.assertNotNull(standardTaskRepository.findByKey("AWc3A2KEoXX3DuVBrVTc"));

    }
}

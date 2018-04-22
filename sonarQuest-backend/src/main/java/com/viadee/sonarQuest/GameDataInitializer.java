package com.viadee.sonarQuest;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import com.viadee.sonarQuest.controllers.AdventureController;
import com.viadee.sonarQuest.controllers.ParticipationController;
import com.viadee.sonarQuest.controllers.QuestController;
import com.viadee.sonarQuest.controllers.TaskController;
import com.viadee.sonarQuest.dtos.AdventureDto;
import com.viadee.sonarQuest.dtos.QuestDto;
import com.viadee.sonarQuest.dtos.WorldDto;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.services.WorldService;

public class GameDataInitializer implements InitializingBean {

    @Autowired
    private WorldService worldService;

    @Autowired
    private TaskController taskController;

    @Autowired
    private QuestController questController;

    @Autowired
    private AdventureController adventureController;

    @Autowired
    private ParticipationController participationController;

    @Autowired
    private WorldRepository worldRepository;

    public GameDataInitializer() {
    }
    
    @ConditionalOnProperty(value = "simulateSonarServer", havingValue = "true")
    private void createSimData() {
    	createWorldOfDragons();
    }

    /**
     * World of Dragons SimData
     */
    //XXX IDs of entities need to be passed back from Controllers, so the don't end up being hardcoded here...
    private void createWorldOfDragons() {
        // Create Quests
        final QuestDto quest1 = new QuestDto(null, "Hidden danger in the woods!", "There is something in the woods, people mumble. Something creeping through the darkness, waiting for its time - or brave heroes to reveal the secret.", null,
                5L, 10L, "assets/images/quest/hero1.jpg", null, null, null, null);
        final QuestDto quest2 = new QuestDto(null, "The gold in the black Tower",
                "The orcs have robbed all gold from the people of Sourcera. Can you bring it back from the black tower? But beware of the creature that is sleeping there.", null, 10L, 20L,
                "assets/images/quest/hero6.jpg", null, null, null, null);
        final QuestDto quest3 = new QuestDto(null, "Through the night",
                "The summoner has to deliver a message to Queen Cyclomatica until tomorrow. Can you guide him through the night?", null, 20L, 30L,
                "assets/images/quest/hero10.jpg", null, null, null, null);
        final QuestDto quest4 = new QuestDto(null, "From dusk till dawn",
                "The moon is changing tonight. So the ghostriders will travel. Can you keep Developia safe from harm until dawn?",
                null, 30L, 50L, "assets/images/quest/hero5.jpg", null, null, null, null);
        questController.createQuest(quest1);
        questController.createQuest(quest2);
        questController.createQuest(quest3);
        questController.createQuest(quest4);

        questController.addWorld(1L, 1L);
        questController.addWorld(2L, 1L);
        questController.addWorld(3L, 1L);
        questController.addWorld(4L, 1L);

        // Add Tasks to Quests
        taskController.addToQuest(1L, 1L);
        taskController.addToQuest(2L, 1L);
        taskController.addToQuest(3L, 1L);
        taskController.addToQuest(4L, 1L);

        taskController.addToQuest(10L, 2L);
        taskController.addToQuest(11L, 2L);
        taskController.addToQuest(12L, 2L);
        taskController.addToQuest(13L, 2L);

        taskController.addToQuest(20L, 3L);
        taskController.addToQuest(21L, 3L);
        taskController.addToQuest(22L, 3L);
        taskController.addToQuest(23L, 3L);

        taskController.addToQuest(30L, 4L);
        taskController.addToQuest(31L, 4L);
        taskController.addToQuest(32L, 4L);
        taskController.addToQuest(33L, 4L);

        // Create Adventure
        AdventureDto adventure = new AdventureDto(null, "Dance of the shadows", "Moon is changing. Unsteady times lie ahead of the people of Sourcera. Can you help them pass through?", null, 30L, 40L, null, null, null);
        adventureController.createAdventure(adventure);

        adventureController.addQuest(1L, 1L);
        adventureController.addQuest(1L, 2L);

        adventureController.addDeveloper(1L, 1L);
        adventureController.addDeveloper(3L, 1L);

        participationController.createParticipation(1L, 1L);
        participationController.createParticipation(2L, 1L);
    }

    @Override
    //XXX IDs of entities need to be passed back from Controllers, so the don't end up being hardcoded here...
    public void afterPropertiesSet() throws Exception {
        worldService.updateWorlds();
        final World firstWorld = worldRepository.findOne((long) 1);
        firstWorld.setActive(true);
        worldRepository.save(firstWorld);
        taskController.updateStandardTasksForWorld((long) 1);
        createWorldOfDragons();
    }
}

package com.viadee.sonarQuest;

import com.viadee.sonarQuest.controllers.AdventureController;
import com.viadee.sonarQuest.controllers.ParticipationController;
import com.viadee.sonarQuest.controllers.QuestController;
import com.viadee.sonarQuest.controllers.TaskController;
import com.viadee.sonarQuest.dtos.AdventureDto;
import com.viadee.sonarQuest.dtos.QuestDto;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.services.WorldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SonarQuestApplication implements CommandLineRunner{

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

	public static void main(String[] args) {
		SpringApplication.run(SonarQuestApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
		//Please comment the following lines for running the test case
		worldService.updateWorlds();
		World firstWorld = worldRepository.findOne((long)1);
		firstWorld.setActive(true);
		worldRepository.save(firstWorld);
		taskController.updateStandardTasksForWorld((long)1);

		//Create Quests

		QuestDto quest1 = new QuestDto(null,"Quest1","Dies ist eine Quest",null,(long)5,(long) 10,null,null,null,null);
		QuestDto quest2 = new QuestDto(null,"Quest2","Dies ist auch eine Quest",null,(long)10,(long) 20,null,null,null,null);
		QuestDto quest3 = new QuestDto(null,"Quest3","Dies ist eine epische Quest",null,(long)20,(long) 30,null,null,null,null);
		QuestDto quest4 = new QuestDto(null,"Quest4","Die Quest der Quests",null,(long)20,(long) 30,null,null,null,null);
		questController.createQuest(quest1);
		questController.createQuest(quest2);
		questController.createQuest(quest3);
		questController.createQuest(quest4);

		questController.addWorld((long)1,(long)1);
		questController.addWorld((long)2,(long)1);
		questController.addWorld((long)3,(long)1);
		questController.addWorld((long)4,(long)1);

		//Add Tasks to Quests
		taskController.addToQuest((long)1,(long)1);
		taskController.addToQuest((long)2,(long)1);
		taskController.addToQuest((long)3,(long)1);
		taskController.addToQuest((long)4,(long)1);

		taskController.addToQuest((long)10,(long)2);
		taskController.addToQuest((long)11,(long)2);
		taskController.addToQuest((long)12,(long)2);
		taskController.addToQuest((long)13,(long)2);

		taskController.addToQuest((long)20,(long)3);
		taskController.addToQuest((long)21,(long)3);
		taskController.addToQuest((long)22,(long)3);
		taskController.addToQuest((long)23,(long)3);
		
		taskController.addToQuest((long)30,(long)4);
		taskController.addToQuest((long)31,(long)4);
		taskController.addToQuest((long)32,(long)4);
		taskController.addToQuest((long)33,(long)4);

		//Create Adventure
		AdventureDto adventure = new AdventureDto(null,"Abenteuer1", "Dies ist ein gefährliches Abenteuer", null, (long)30,(long)40,null,null,null);
		adventureController.createAdventure(adventure);
		adventure = new AdventureDto(null,"Abenteuer2", "Das große Abenteuer", null, (long)25,(long)50,null,null,null);
		adventureController.createAdventure(adventure);
		adventure = new AdventureDto(null,"Abenteuer3", "Ein letztes Abenteuer", null, (long)25,(long)50,null,null,null);
		adventureController.createAdventure(adventure);

		adventureController.addQuest((long)1,(long)1);
		adventureController.addQuest((long)1,(long)2);
		adventureController.addQuest((long)2,(long)3);
		adventureController.addQuest((long)3,(long)4);

		adventureController.addDeveloper((long)1,(long)1);
		adventureController.addDeveloper((long)3,(long)1);

		participationController.createParticipation((long)1,(long)1);
		participationController.createParticipation((long)2,(long)1);
		participationController.createParticipation((long)4,(long)1);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("http://localhost:4200");
			}
		};
	}
}

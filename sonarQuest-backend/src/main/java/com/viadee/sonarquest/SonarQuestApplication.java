package com.viadee.sonarquest;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.viadee.sonarquest.skillTree.entities.SkillTreeUser;
import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.entities.UserSkill;
import com.viadee.sonarquest.skillTree.entities.UserSkillToSkillTreeUser;
import com.viadee.sonarquest.skillTree.repositories.SkillTreeUserRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillRepository;
import com.viadee.sonarquest.skillTree.repositories.UserSkillToSkillTreeUserRepository;
import com.viadee.sonarquest.skillTree.utils.export.ExportService;

@SpringBootApplication
public class SonarQuestApplication implements CommandLineRunner {
	/*
	 * @Autowired UserSkillRepositroy userSkillRepositroy;
	 * 
	 * @Autowired SkillTreeUserRepository skillTreeUserRepository;
	 * 
	 * @Autowired UserSkillToSkillTreeUserRepository
	 * userSkillToSkillTreeUserRepository;
	 */
	
	@Autowired
	private ExportService exportService;

    public static void main(String[] args) {
        SpringApplication.run(SonarQuestApplication.class, args);
    }

    @Override
    public void run(String... strings) {
        // Application is started via main()
		/*
		 * System.out.println("User"); UserSkill userSkill =
		 * userSkillRepositroy.findOne((long) 1); SkillTreeUser skillTreeUser = new
		 * SkillTreeUser(); skillTreeUser.setMail("test@test.de");
		 * skillTreeUserRepository.save(skillTreeUser);
		 * 
		 * System.out.println("Mathc"); UserSkillToSkillTreeUser
		 * userSkillToSkillTreeUser = new UserSkillToSkillTreeUser();
		 * userSkillToSkillTreeUser.setRepeats(0);
		 * userSkillToSkillTreeUser.setSkillTreeUser(skillTreeUser);
		 * userSkillToSkillTreeUser.setUserSkill(userSkill);
		 * userSkillToSkillTreeUserRepository.save(userSkillToSkillTreeUser);
		 * System.out.println("Skill");
		 * 
		 * userSkill.addUserSkillToSkilLTreeUsers(userSkillToSkillTreeUser);
		 * userSkillRepositroy.save(userSkill);
		 * 
		 * skillTreeUser.addUserSkillToSkillTreeUser(userSkillToSkillTreeUser);
		 * skillTreeUserRepository.save(skillTreeUser);
		 */
    	
		/*
		 * SkillTreeUser skillTreeUser = skillTreeUserRepository.findOne((long) 1);
		 * 
		 * for(UserSkillToSkillTreeUser entry:
		 * skillTreeUser.getUserSkillToSkillTreeUser()) { for(SonarRule
		 * rule:entry.getUserSkill().getSonarRules()) {
		 * System.out.println(rule.getName()); } }
		 */
    	//exportService.exportUserSkills();
    	//exportService.exportSonarRules();
    	//exportService.createSonarRuleSQLScript();
    	
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("http://localhost:4200")
						.allowedOrigins("http://localhost:4200");
            }
        };
    }
    

}

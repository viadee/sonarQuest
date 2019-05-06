package com.viadee.sonarquest.skillTree.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.viadee.sonarquest.skillTree.services.SonarRuleService;

@Component
public class SonarRuleTask {

	@Autowired
	private SonarRuleService sonarRuleService;

	//TODO as sonarquest.properties
	@Scheduled(cron = "0 0 6,12,18 * * *")
	public void updateSonarRulesFromServer() {
		sonarRuleService.update("Java");
	}

}

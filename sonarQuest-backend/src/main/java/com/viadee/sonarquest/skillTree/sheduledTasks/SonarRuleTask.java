package com.viadee.sonarquest.skillTree.sheduledTasks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.viadee.sonarquest.controllers.WebSocketController;
import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.services.SonarRuleService;

@Component
public class SonarRuleTask {

	@Autowired
	private SonarRuleService sonarRuleService;

	@Autowired
	private WebSocketController webSocketController;

	// TODO as sonarquest.properties
	@Scheduled(cron = "0 0 6,12,18 * * *")
	public void updateSonarRulesFromServer() {
		List<SonarRule> sonarRules = sonarRuleService.update("Java");
		sonarRules.forEach(sonarRule -> webSocketController.onUpdateSonarRule(sonarRule));
	}

}

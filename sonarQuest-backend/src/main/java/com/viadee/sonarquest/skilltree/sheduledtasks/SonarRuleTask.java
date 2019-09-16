package com.viadee.sonarquest.skilltree.sheduledtasks;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.viadee.sonarquest.controllers.WebSocketController;
import com.viadee.sonarquest.skilltree.entities.SonarRule;
import com.viadee.sonarquest.skilltree.services.SonarRuleService;

@Component
public class SonarRuleTask {

	@Autowired
	private SonarRuleService sonarRuleService;

	@Autowired
	private WebSocketController webSocketController;

	@Scheduled(cron = "${cron.expression.sonarrule.update.rate}")
	public void updateSonarRulesFromServer() {
		List<SonarRule> sonarRules = sonarRuleService.update("Java");
		sonarRules.forEach(sonarRule -> webSocketController.onUpdateSonarRule(sonarRule));
	}

}

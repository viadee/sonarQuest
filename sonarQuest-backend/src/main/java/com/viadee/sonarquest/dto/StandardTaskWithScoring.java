package com.viadee.sonarquest.dto;

import javax.persistence.Column;

import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.StandardTask;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.rules.SonarQuestStatus;

public class StandardTaskWithScoring extends StandardTask {

	private Double scoring;

	public StandardTaskWithScoring() {
		
	}

	public StandardTaskWithScoring(final String title, final SonarQuestStatus status, final Long gold, final Long xp,
			final Quest quest, final World world, final String key, final String component, final String severity,
			final String type, final Integer debt, final String issueKey, String issueRule, Double scoring) {
		this.setTitle(title);
		this.setStatus(status);
		this.setGold(gold);
		this.setXp(xp);
		this.setQuest(quest);
		this.setWorld(world);
		this.setKey(key);
		this.setComponent(component);
		this.setSeverity(severity);
		this.setType(type);
		this.setDebt(debt);
		this.setIssueKey(issueKey);
		this.setIssueRule(issueRule);
		this.scoring = scoring;
	}

	public Double getScoring() {
		return scoring;
	}

	public void setScoring(Double scoring) {
		this.scoring = scoring;
	}

}

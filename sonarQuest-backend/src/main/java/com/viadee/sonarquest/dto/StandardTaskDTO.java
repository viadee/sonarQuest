package com.viadee.sonarquest.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Transient;

import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.rules.SonarQuestStatus;

public class StandardTaskDTO extends TaskDTO {

	private String component;

	private String severity;

	private String type;

	private Integer debt;

	protected String issueKey;

	private String issueRule;

	private Double scoring;

	public StandardTaskDTO() {
	}

	public StandardTaskDTO(Long id, Date startdate, Date enddate, final String title, final SonarQuestStatusDTO status,
			final Long gold, final Long xp, final WorldDTO world, final String key, final String component,
			final String severity, final String type, final Integer debt, final String issueKey, String issueRule) {
		this.setId(id);
		this.setStartdate(startdate);
		this.setEnddate(enddate);
		this.setTitle(title);
		this.setStatus(status);
		this.setGold(gold);
		this.setXp(xp);
		this.setWorld(world);
		this.setKey(key);
		this.component = component;
		this.severity = severity;
		this.type = type;
		this.debt = debt;
		this.issueKey = issueKey;
		this.issueRule = issueRule;

	}

	public String getComponent() {
		return component;
	}

	public void setComponent(String component) {
		this.component = component;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Integer getDebt() {
		return debt;
	}

	public void setDebt(Integer debt) {
		this.debt = debt;
	}

	public String getIssueKey() {
		return issueKey;
	}

	public void setIssueKey(String issueKey) {
		this.issueKey = issueKey;
	}

	public String getIssueRule() {
		return issueRule;
	}

	public void setIssueRule(String issueRule) {
		this.issueRule = issueRule;
	}

	public Double getScoring() {
		return scoring;
	}

	public void setScoring(Double scoring) {
		this.scoring = scoring;
	}

}

package com.viadee.sonarquest.dto;

public class StandardTaskDTO extends TaskDTO{

	private String component;

	private String severity;

	private String type;

	private Integer debt;

	private String issueKey;

	private String issueRule;

	private Double userSkillScoring;

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

	public Double getUserSkillScoring() {
		return userSkillScoring;
	}

	public void setUserSkillScoring(Double userSkillScoring) {
		this.userSkillScoring = userSkillScoring;
	}

}

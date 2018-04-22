package com.viadee.sonarQuest.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("STANDARD")
public class StandardTask extends Task {

    @Column(name = "key")
    private String key;

    @Column(name = "component")
    private String component;

    @Column(name = "severity")
    private String severity;

    @Column(name = "type")
    private String type;

    @Column(name = "debt")
    private Integer debt;

	@Column(name = "issue_key")
	protected String issueKey;

    public StandardTask() {
    }

    public StandardTask(final String title, final String status, final Long gold, final Long xp, final Quest quest,
            final World world, final String key,
            final String component, final String severity, final String type, final Integer debt,
            final String issueKey) {
        this.setTitle(title);
        this.setStatus(status);
        this.setGold(gold);
        this.setXp(xp);
        this.setQuest(quest);
        this.setWorld(world);
        this.key = key;
        this.component = component;
        this.severity = severity;
        this.type = type;
        this.debt = debt;
        this.issueKey = issueKey;
    }

    public String getKey() {
        return key;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getComponent() {
        return component;
    }

    public void setComponent(final String component) {
        this.component = component;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(final String severity) {
        this.severity = severity;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Integer getDebt() {
        return debt;
    }

    public void setDebt(final Integer debt) {
        this.debt = debt;
    }


    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(final String issueKey) {
        this.issueKey = issueKey;
    }
    
}

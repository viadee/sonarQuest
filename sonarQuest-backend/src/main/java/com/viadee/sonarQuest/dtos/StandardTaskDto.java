package com.viadee.sonarQuest.dtos;

import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.World;

public class StandardTaskDto extends TaskDto {

    private String key;

    private String component;

    private String severity;

    private String type;

    private Integer debt;

    public StandardTaskDto() {
    }

    public StandardTaskDto(final Long id, final String title, final String status, final Long gold, final Long xp,
            final Quest quest, final World world,
            final Participation participation, final String key, final String component, final String severity,
            final String type, final Integer debt,
            final String issueKey) {
        this.setId(id);
        this.setTitle(title);
        this.setStatus(status);
        this.setGold(gold);
        this.setXp(xp);
        this.setQuest(quest);
        this.setWorld(world);
        this.setParticipation(participation);
        this.setTaskType("STANDARD");
        this.key = key;
        this.component = component;
        this.severity = severity;
        this.type = type;
        this.debt = debt;
        this.setIssueKey(issueKey);
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
}

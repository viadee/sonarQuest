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

    public StandardTaskDto(Long id, String title, String status, Long gold, Long xp, Quest quest, World world, Participation participation,String key, String component, String severity, String type, Integer debt) {
        this.setId(id);
        this.setTitle(title);
        this.setStatus(status);
        this.setGold(gold);
        this.setXp(xp);
        this.setQuest(quest);
        this.setWorld(world);
        this.setParticipation(participation);
        this.setTaskType("STANDARD");
        this.key=key;
        this.component = component;
        this.severity = severity;
        this.type = type;
        this.debt = debt;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
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
}

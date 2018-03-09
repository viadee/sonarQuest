package com.viadee.sonarQuest.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("STANDARD")
public class StandardTask extends Task{

    @Column(name = "key")
    private String key;

    @Column(name="component")
    private String component;

    @Column(name="severity")
    private String severity;

    @Column(name="type")
    private String type;

    @Column(name="debt")
    private Integer debt;

    public StandardTask() {
    }

    public StandardTask(String title, String status, Long gold, Long xp, Quest quest, World world,String key, String component, String severity, String type, Integer debt) {
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

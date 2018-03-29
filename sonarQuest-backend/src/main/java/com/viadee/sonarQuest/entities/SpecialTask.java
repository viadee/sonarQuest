package com.viadee.sonarQuest.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SPECIAL")
public class SpecialTask extends Task {

    @Column(name="message")
    private String message;

    public SpecialTask() {
    }

    public SpecialTask(String title, String status, Long gold, Long xp,Quest quest,String message, World world) {
        this.setTitle(title);
        this.setStatus(status);
        this.setGold(gold);
        this.setXp(xp);
        this.setQuest(quest);
        this.message = message;
        this.setWorld(world);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

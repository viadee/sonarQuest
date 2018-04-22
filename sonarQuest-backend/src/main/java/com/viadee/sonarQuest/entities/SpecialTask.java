package com.viadee.sonarQuest.entities;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("SPECIAL")
public class SpecialTask extends Task {

    @Column(name = "message")
    private String message;

    public SpecialTask() {
    }

    public SpecialTask(final String title, final String status, final Long gold, final Long xp, final Quest quest,
            final String message, final World world) {
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

    public void setMessage(final String message) {
        this.message = message;
    }
}

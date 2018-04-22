package com.viadee.sonarQuest.dtos;

import javax.persistence.Column;

import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.World;

public class SpecialTaskDto extends TaskDto {

    @Column(name = "message")
    private String message;

    public SpecialTaskDto() {
    }

    public SpecialTaskDto(final Long id, final String title, final String status, final Long gold, final Long xp,
            final Quest quest, final Participation participation, final String message, final World world) {
        this.setId(id);
        this.setTitle(title);
        this.setStatus(status);
        this.setGold(gold);
        this.setXp(xp);
        this.setQuest(quest);
        this.setParticipation(participation);
        this.setTaskType("SPECIAL");
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

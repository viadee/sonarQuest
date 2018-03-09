package com.viadee.sonarQuest.dtos;

import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;

import javax.persistence.Column;

public class SpecialTaskDto extends TaskDto {

    @Column(name="message")
    private String message;

    public SpecialTaskDto() {
    }

    public SpecialTaskDto(Long id, String title, String status, Long gold, Long xp, Quest quest, Participation participation, String message) {
        this.setId(id);
        this.setTitle(title);
        this.setStatus(status);
        this.setGold(gold);
        this.setXp(xp);
        this.setQuest(quest);
        this.setParticipation(participation);
        this.setTaskType("SPECIAL");
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

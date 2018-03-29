package com.viadee.sonarQuest.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.viadee.sonarQuest.entities.*;

@JsonDeserialize(as = SpecialTaskDto.class)
public  abstract class TaskDto {

    private Long id;

    private String title;

    private String status;

    private Long gold;

    private Long xp;

    private Quest quest;

    private World world;

    private String taskType;

    private Participation participation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(Long gold) {
        this.gold = gold;
    }

    public Long getXp() {
        return xp;
    }

    public void setXp(Long xp) {
        this.xp = xp;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Participation getParticipation() {
        return participation;
    }

    public void setParticipation(Participation participation) {
        this.participation = participation;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
    }

    public static TaskDto toTaskDto(Task task) {
        TaskDto taskDto = null;
        if (task instanceof StandardTask) {
            StandardTaskDto standardTaskDto = new StandardTaskDto(
                    task.getId(),
                    task.getTitle(),
                    task.getStatus(),
                    task.getGold(),
                    task.getXp(),
                    task.getQuest(),
                    task.getWorld(),
                    task.getParticipation(),
                    ((StandardTask) task).getKey(),
                    ((StandardTask) task).getComponent(),
                    ((StandardTask) task).getSeverity(),
                    ((StandardTask) task).getType(),
                    ((StandardTask) task).getDebt());
            taskDto = standardTaskDto;
        }
        if (task instanceof SpecialTask) {
            SpecialTaskDto specialTaskDto = new SpecialTaskDto(
                    task.getId(),
                    task.getTitle(),
                    task.getStatus(),
                    task.getGold(),
                    task.getXp(),
                    task.getQuest(),
                    task.getParticipation(),
                    ((SpecialTask) task).getMessage(),
                    task.getWorld());
            taskDto = specialTaskDto;
        }
        return taskDto;
    }
}

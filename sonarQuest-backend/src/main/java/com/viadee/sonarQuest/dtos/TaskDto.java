package com.viadee.sonarQuest.dtos;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.SpecialTask;
import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.World;

@JsonDeserialize(as = SpecialTaskDto.class)
public abstract class TaskDto {

    private Long id;

    private String title;

    private String status;

    private Long gold;

    private Long xp;

    private Quest quest;

    private World world;

    private String taskType;

    private Participation participation;

    private String issueKey;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(final String status) {
        this.status = status;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(final Long gold) {
        this.gold = gold;
    }

    public Long getXp() {
        return xp;
    }

    public void setXp(final Long xp) {
        this.xp = xp;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(final Quest quest) {
        this.quest = quest;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(final World world) {
        this.world = world;
    }

    public Participation getParticipation() {
        return participation;
    }

    public void setParticipation(final Participation participation) {
        this.participation = participation;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(final String taskType) {
        this.taskType = taskType;
    }

    public String getIssueKey() {
        return issueKey;
    }

    public void setIssueKey(final String issueKey) {
        this.issueKey = issueKey;
    }

    public static TaskDto toTaskDto(final Task task) {
        TaskDto taskDto = null;
        if (task instanceof StandardTask) {
            final StandardTaskDto standardTaskDto = new StandardTaskDto(
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
                    ((StandardTask) task).getDebt(),
                    ((StandardTask) task).getIssueKey());
            taskDto = standardTaskDto;
        }
        if (task instanceof SpecialTask) {
            final SpecialTaskDto specialTaskDto = new SpecialTaskDto(
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

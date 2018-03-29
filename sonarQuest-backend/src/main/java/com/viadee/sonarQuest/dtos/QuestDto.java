package com.viadee.sonarQuest.dtos;

import com.viadee.sonarQuest.entities.*;

import java.util.List;

public class QuestDto {


    private Long id;


    private String title;


    private String story;


    private String status;


    private Long gold;


    private Long xp;

    private String image;


    private World world;

    private Adventure adventure;

    private List<Task> tasks;

    private List<Participation> participations;

    public QuestDto() {
    }

    public QuestDto(Long id, String title, String story, String status, Long gold, Long xp, String image, World world, Adventure adventure, List<Task> tasks, List<Participation> participations) {
        this.id = id;
        this.title = title;
        this.story = story;
        this.status = status;
        this.gold = gold;
        this.xp = xp;
        this.image = image;
        this.world = world;
        this.adventure = adventure;
        this.tasks = tasks;
        this.participations = participations;
    }

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

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public Adventure getAdventure() {
        return adventure;
    }

    public void setAdventure(Adventure adventure) {
        this.adventure = adventure;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }

    public static QuestDto toQuestDto(Quest quest) {
        QuestDto questDto = null;
        if (quest != null) {
            questDto = new QuestDto(quest.getId(), quest.getTitle(),quest.getStory(),quest.getStatus(),quest.getGold(),quest.getXp(), quest.getImage(), quest.getWorld(), quest.getAdventure(), quest.getTasks(),quest.getParticipations());
        }
        return questDto;
    }
}

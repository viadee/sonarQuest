package com.viadee.sonarQuest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Quest")
public class Quest {


    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name="story")
    private String story;

    @Column(name="status")
    private String status;

    @Column(name="gold")
    private Long gold;

    @Column(name="xp")
    private Long xp;

    @Column(name="image")
    private String image;

    @ManyToOne()
    @JoinColumn(name="world_id")
    private World world;

    @ManyToOne()
    @JoinColumn(name="adventure_id")
    private Adventure adventure;

    @OneToMany(mappedBy="quest")
    private List<Task> tasks;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="quest")
    private List<Participation> participations;

    public Quest() {
    }

    public Quest(String title, String story, String status, Long gold, Long xp, String image) {
        this.title = title;
        this.story = story;
        this.status = status;
        this.gold = gold;
        this.xp = xp;
        this.image = image;
    }

    public Quest(String title, String story, String status, Long gold, Long xp, String image, World world, Adventure adventure, List<Task> tasks, List<Participation> participations) {
        this.title = title;
        this.story = story;
        this.status = status;
        this.gold = gold;
        this.xp = xp;
        this.world = world;
        this.adventure = adventure;
        this.tasks = tasks;
        this.participations = participations;
        this.image = image;
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

    @JsonIgnore
    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    @JsonIgnore
    public Adventure getAdventure() {
        return adventure;
    }

    public void setAdventure(Adventure adventure) {
        this.adventure = adventure;
    }

    @JsonIgnore
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @JsonIgnore
    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(List<Participation> participations) {
        this.participations = participations;
    }
}

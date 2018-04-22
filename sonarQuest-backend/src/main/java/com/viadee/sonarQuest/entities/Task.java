package com.viadee.sonarQuest.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "task_type")
@Table(name = "Task")
@JsonDeserialize(as = SpecialTask.class)
public abstract class Task {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    private String status;

    @Column(name = "gold")
    private Long gold;

    @Column(name = "xp")
    private Long xp;

    @ManyToOne()
    @JoinColumn(name = "quest_id")
    private Quest quest;

    @ManyToOne
    @JoinColumn(name = "world_id")
    private World world;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "participation_id")
    private Participation participation;

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

    @JsonIgnore
    public Quest getQuest() {
        return quest;
    }

    public void setQuest(final Quest quest) {
        this.quest = quest;
    }

    @JsonIgnore
    public Participation getParticipation() {
        return participation;
    }

    public void setParticipation(final Participation participation) {
        this.participation = participation;
    }

    @JsonIgnore
    public World getWorld() {
        return world;
    }

    public void setWorld(final World world) {
        this.world = world;
    }

}

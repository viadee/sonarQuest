package com.viadee.sonarQuest.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Participation")
public class Participation {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy = "participation", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "quest_id")
    private Quest quest;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Participation() {
    }

    public Participation(final Quest quest, final User user) {
        this.quest = quest;
        this.user = user;
    }

    public Participation(final Long id, final List<Task> tasks, final Quest quest, final User user) {
        this.id = id;
        this.tasks = tasks;
        this.quest = quest;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(final List<Task> tasks) {
        this.tasks = tasks;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(final Quest quest) {
        this.quest = quest;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

}

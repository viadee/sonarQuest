package com.viadee.sonarQuest.entities;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Participation")
public class Participation {

    @Id
    @GeneratedValue
    private Long id;

    @OneToMany(mappedBy="participation",cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name="quest_id")
    private Quest quest;

    @ManyToOne
    @JoinColumn(name="developer_id")
    private Developer developer;

    public Participation() {
    }

    public Participation(Quest quest, Developer developer) {
        this.quest = quest;
        this.developer = developer;
    }

    public Participation(Long id, List<Task> tasks, Quest quest, Developer developer) {
        this.id = id;
        this.tasks = tasks;
        this.quest = quest;
        this.developer = developer;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    public Quest getQuest() {
        return quest;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }

    public Developer getDeveloper() {
        return developer;
    }

    public void setDeveloper(Developer developer) {
        this.developer = developer;
    }
}

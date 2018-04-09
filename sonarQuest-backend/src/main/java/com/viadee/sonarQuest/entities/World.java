package com.viadee.sonarQuest.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "World")
public class World {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "project")
    private String project;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy="world")
    private List<Adventure> adventures;

    @OneToMany(mappedBy="world")
    private List<Quest> quests;

    @OneToMany(mappedBy="world",cascade = CascadeType.ALL)
    private List<Task> tasks;

    public World() {
    }

    public World(String name, String project) {
        this.name = name;
        this.project = project;
    }

    public World(String name, String project, Boolean active) {
        this.name = name;
        this.project = project;
        this.active = active;
    }

	public World(Long id, String name, String project, Boolean active, List<Quest> quests, List<Task> tasks) {
		this.id = id;
		this.name = name;
		this.project = project;
		this.active = active;
		this.quests = quests;
		this.tasks = tasks;
	}

	public World(Long id, String name, String project, Boolean active, List<Adventure> adventures, List<Quest> quests, List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.project = project;
        this.active = active;
        this.adventures = adventures;
        this.quests = quests;
        this.tasks = tasks;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
		this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    @JsonIgnore
    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @JsonIgnore
    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
}

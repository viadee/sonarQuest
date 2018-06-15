package com.viadee.sonarQuest.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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

    @Column(name = "image")
    private String image;

    @Column(name = "active")
    private Boolean active;

    @JsonIgnore
    @OneToMany(mappedBy = "world")
    private List<Adventure> adventures;

    @JsonIgnore
    @OneToMany(mappedBy = "world")
    private List<Quest> quests;

    @JsonIgnore
    @OneToMany(mappedBy = "world", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @JsonIgnore
    @ManyToMany
    @JoinTable(name = "User_To_World", joinColumns = @JoinColumn(name = "world_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> users;

    public World() {
    }

    public World(final String name, final String project) {
        this.name = name;
        this.project = project;
    }

    public World(final String name, final String project, final Boolean active) {
        this.name = name;
        this.project = project;
        this.active = active;
    }

    public World(final Long id, final String name, final String project, final Boolean active, final List<Quest> quests,
            final List<Task> tasks) {
        this.id = id;
        this.name = name;
        this.project = project;
        this.active = active;
        this.quests = quests;
        this.tasks = tasks;
    }

    public World(final Long id, final String name, final String project, final Boolean active,
            final List<Adventure> adventures, final List<Quest> quests,
            final List<Task> tasks) {
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

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getProject() {
        return project;
    }

    public void setProject(final String project) {
        this.project = project;
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void setQuests(final List<Quest> quests) {
        this.quests = quests;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(final Boolean active) {
        this.active = active;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(final List<Task> tasks) {
        this.tasks = tasks;
    }

    public String getImage() {
        return image;
    }

    public void setImage(final String image) {
        this.image = image;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }

}

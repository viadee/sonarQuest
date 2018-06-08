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
@Table(name = "Level")
public class Level {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "min")
    private Long min;

    @Column(name = "max")
    private Long max;

    @JsonIgnore
    @OneToMany(mappedBy = "level", cascade = CascadeType.ALL)
    private List<User> users;

    @JsonIgnore
    @OneToMany(mappedBy = "minLevel", cascade = CascadeType.ALL)
    private List<Artefact> artefacts;

    protected Level() {
    }

    public Level(final Long min, final Long max) {
        this.min = min;
        this.max = max;
    }

    public Level(final Long min, final Long max, final List<User> users, final List<Artefact> artefacts) {
        this.min = min;
        this.max = max;
        this.users = users;
        this.artefacts = artefacts;
    }

    public Level(final Long minLevel) {
        this.min = minLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getMin() {
        return min;
    }

    public void setMin(final Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(final Long max) {
        this.max = max;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }

    public List<Artefact> getArtefacts() {
        return artefacts;
    }

    public void setArtefacts(final List<Artefact> artefacts) {
        this.artefacts = artefacts;
    }
}

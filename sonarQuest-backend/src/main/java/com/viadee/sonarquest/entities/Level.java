package com.viadee.sonarquest.entities;

import java.util.List;

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

    @Column(name = "level")
    private int level;
    
    @Column(name = "min_xp")
    private Long minXp;

    @Column(name = "max_xp")
    private Long maxXp;

    @JsonIgnore
    @OneToMany(mappedBy = "level")
    private List<User> users;

    @JsonIgnore
    @OneToMany(mappedBy = "minLevel")
    private List<Artefact> artefacts;

    public Level() {
    }

    public Level(final Long min, final Long max) {
        this.minXp = min;
        this.maxXp = max;
    }

    public Level(final Long min, final Long max, final List<User> users, final List<Artefact> artefacts) {
        this.minXp = min;
        this.maxXp = max;
        this.users = users;
        this.artefacts = artefacts;
    }

    public Level(final Long minLevel) {
        this.minXp = minLevel;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public Long getMinXp() {
        return minXp;
    }

    public void setMinXp(final Long min) {
        this.minXp = min;
    }

    public Long getMaxXp() {
        return maxXp;
    }

    public void setMaxXp(final Long max) {
        this.maxXp = max;
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
    
    public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}

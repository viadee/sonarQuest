package com.viadee.sonarquest.entities;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "SQLevel")
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sqlevel")
    private int levelNumber;

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
        minXp = min;
        maxXp = max;
    }

    public Level(final Long min, final Long max, final List<User> users, final List<Artefact> artefacts) {
        minXp = min;
        maxXp = max;
        this.users = users;
        this.artefacts = artefacts;
    }

    public Level(final Long minLevel) {
        minXp = minLevel;
    }
}

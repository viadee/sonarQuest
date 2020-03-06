package com.viadee.sonarquest.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viadee.sonarquest.constants.AdventureState;

import lombok.Data;

@Data
@Entity
@Table(name = "Adventure")
public class Adventure {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "startdate")
    private Date startdate;

    @Column(name = "enddate")
    private Date enddate;

    @Column(name = "visible")
    private Boolean visible;

    @Column(name = "title")
    private String title;

    @Column(name = "story")
    private String story;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AdventureState status;

    @Column(name = "gold")
    private Long gold;

    @Column(name = "xp")
    private Long xp;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "world_id")
    private World world;

    @OneToMany(mappedBy = "adventure", cascade = CascadeType.ALL)
    private List<Quest> quests;

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Adventure_User", joinColumns = @JoinColumn(name = "adventure_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
    private List<User> users;

    public Adventure() {
    }

    public Adventure(final String title, final String story, final AdventureState status, final Long gold,
            final Long xp) {
        this.title = title;
        this.story = story;
        this.status = status;
        this.gold = gold;
        this.xp = xp;
    }

    public Adventure(final Long id, final String title, final String story, final AdventureState status,
            final Long gold,
            final Long xp, final World world,
            final List<Quest> quests, final List<User> users) {
        this.id = id;
        this.title = title;
        this.story = story;
        this.status = status;
        this.gold = gold;
        this.xp = xp;
        this.world = world;
        this.quests = quests;
        this.users = users;
    }

    public synchronized void addUser(final User user) {
        if (users == null) {
            users = new ArrayList<>();
        }
        users.add(user);
    }

    public synchronized void removeUser(final User user) {
        users.remove(user);
    }

}

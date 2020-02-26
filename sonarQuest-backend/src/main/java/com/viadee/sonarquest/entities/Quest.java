package com.viadee.sonarquest.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viadee.sonarquest.constants.QuestState;
import lombok.Data;

@Data
@Entity
@Table(name = "Quest")
public class Quest {

    @Id
    @GeneratedValue
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

    @Column(name = "creator_name")
    private String creatorName;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private QuestState status;

    @Column(name = "gold")
    private Long gold;

    @Column(name = "xp")
    private Long xp;

    @Column(name = "image")
    private String image;

    @ManyToOne()
    @JoinColumn(name = "world_id")
    private World world;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "adventure_id")
    private Adventure adventure;

    @JsonIgnore
    @OneToMany(mappedBy = "quest")
    private List<Task> tasks;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "quest")
    private List<Participation> participations;

    public Quest() {
    }

    public Quest(final String title, final String story, final QuestState status, final Long gold, final Long xp,
            final String image, final Boolean visible) {
        this.title = title;
        this.story = story;
        this.status = status;
        this.gold = gold;
        this.xp = xp;
        this.image = image;
        this.visible = visible;
    }

    public Quest(final String title, final String story, final QuestState status, final Long gold, final Long xp,
            final String image, final World world, final Boolean visible, final Adventure adventure,
            final List<Task> tasks, final List<Participation> participations) {
        this.title = title;
        this.story = story;
        this.status = status;
        this.gold = gold;
        this.xp = xp;
        this.world = world;
        this.visible = visible;
        this.adventure = adventure;
        this.tasks = tasks;
        this.participations = participations;
        this.image = image;
    }

    /**
     * Looks up the usernames of all participants in this quests and returns them in a list.
     */
    public List<String> getParticipants() {
        if (participations != null) {
            return participations.stream().map(Participation::getUser).map(User::getUsername)
                    .collect(Collectors.toList());
        } else {
            return new ArrayList<>();
        }
    }
}

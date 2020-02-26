package com.viadee.sonarquest.entities;

import java.sql.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viadee.sonarquest.rules.SonarQuestTaskStatus;
import lombok.Data;

@Data
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "task_type")
@Table(name = "Task")
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "startdate")
    private Date startdate;

    @Column(name = "enddate")
    private Date enddate;

    @Column(name = "title")
    private String title;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private SonarQuestTaskStatus status;

    @Column(name = "gold")
    private Long gold;

    @Column(name = "xp")
    private Long xp;

    @Column(name = "task_key")
    private String key;

    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "quest_id")
    private Quest quest;

    @ManyToOne
    @JoinColumn(name = "world_id")
    private World world;

    @JsonIgnore
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "participation_id")
    private Participation participation;
}

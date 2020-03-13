package com.viadee.sonarquest.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "Participation")
public class Participation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "participation", cascade = CascadeType.ALL)
    private List<Task> tasks;

    @ManyToOne
    @JoinColumn(name = "quest_id")
    private Quest quest;

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
}

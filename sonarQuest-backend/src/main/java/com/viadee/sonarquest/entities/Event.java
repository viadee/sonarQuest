package com.viadee.sonarquest.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.viadee.sonarquest.constants.EventState;
import com.viadee.sonarquest.constants.EventType;
import lombok.*;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Event")
public class Event {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "type")
    private EventType type;

    @Column(name = "title")
    private String title;

    @Column(name = "story")
    private String story;

    @Column(name = "state")
    private EventState state;

    @Column(name = "image")
    private String image;

    @Column(name = "headline")
    private String headline;

    @ManyToOne()
    @JoinColumn(name = "world_id")
    private World world;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "timestamp")
    private Timestamp timestamp;

    public Event(String title) {
        this.title = title;
    }

    public Event(EventType type, String title, String story, EventState state, String image, World world,
            String headline) {
        this.type = type;
        this.title = title;
        this.story = story;
        this.state = state;
        this.image = image;
        this.world = world;
        timestamp = new Timestamp(System.currentTimeMillis());
        this.headline = headline;
    }

    public Event(EventType type, String title, String story, EventState state, String image, World world,
            String headline, User user) {
        this.type = type;
        this.title = title;
        this.story = story;
        this.state = state;
        this.image = image;
        this.world = world;
        timestamp = new Timestamp(System.currentTimeMillis());
        this.headline = headline;
        this.user = user;
    }

    public Event(EventType type, String title, String story, EventState state, World world, String headline) {
        this.type = type;
        this.title = title;
        this.story = story;
        this.state = state;
        this.world = world;
        timestamp = new Timestamp(System.currentTimeMillis());
        this.headline = headline;
    }

    public Event(EventType type, String story, World world, User user) {
        this.type = type;
        this.story = story;
        this.world = world;
        this.user = user;
        image = user.getPicture();
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public Event(EventType type, String title, String story, EventState state, String image, User user) {
        this.type = type;
        this.story = story;
        this.title = title;
        this.image = image;
        this.state = state;
        timestamp = new Timestamp(System.currentTimeMillis());
        this.user = user;
        world = null;
    }
}

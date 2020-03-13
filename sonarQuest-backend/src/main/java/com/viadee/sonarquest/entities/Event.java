package com.viadee.sonarquest.entities;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.viadee.sonarquest.constants.EventState;
import com.viadee.sonarquest.constants.EventType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "Event")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    public Event(final String title) {
        this.title = title;
    }

    public Event(final EventType type, final String title, final String story, final EventState state, final String image, final World world,
            final String headline) {
        this.type = type;
        this.title = title;
        this.story = story;
        this.state = state;
        this.image = image;
        this.world = world;
        timestamp = new Timestamp(System.currentTimeMillis());
        this.headline = headline;
    }

    public Event(final EventType type, final String title, final String story, final EventState state, final String image, final World world,
            final String headline, final User user) {
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

    public Event(final EventType type, final String title, final String story, final EventState state, final World world, final String headline) {
        this.type = type;
        this.title = title;
        this.story = story;
        this.state = state;
        this.world = world;
        timestamp = new Timestamp(System.currentTimeMillis());
        this.headline = headline;
    }

    public Event(final EventType type, final String story, final World world, final User user) {
        this.type = type;
        this.story = story;
        this.world = world;
        this.user = user;
        image = user.getPicture();
        timestamp = new Timestamp(System.currentTimeMillis());
    }

    public Event(final EventType type, final String title, final String story, final EventState state, final String image, final User user) {
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

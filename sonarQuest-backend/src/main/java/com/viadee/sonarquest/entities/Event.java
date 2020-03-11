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

@Entity
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

    public Event() {
    }

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public EventType getType() {
        return type;
    }

    public void setType(EventType type) {
        this.type = type;
    }

    public String getStory() {
        return story;
    }

    public void setStory(String story) {
        this.story = story;
    }

    public EventState getState() {
        return state;
    }

    public void setState(EventState state) {
        this.state = state;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((title == null) ? 0 : title.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        Event other = (Event) obj;
        if (title == null) {
            if (other.title != null) {
                return false;
            }
        } else if (!title.equals(other.title)) {
            return false;
        }
        return true;
    }

}

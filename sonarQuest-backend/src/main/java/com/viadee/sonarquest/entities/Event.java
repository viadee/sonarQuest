package com.viadee.sonarquest.entities;

import java.sql.Timestamp;
import java.util.Objects;

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
		super();
		this.title = title;
	}

	public Event(EventType type, String title, String story, EventState state, String image, World world, String headline) {
		this.type = type;
		this.title = title;
		this.story = story;
		this.state = state;
		this.image = image;
		this.world = world;
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.headline = headline;
	}

	public Event(EventType type, String title, String story, EventState state, String image, World world, String headline, User user) {
		this.type = type;
		this.title = title;
		this.story = story;
		this.state = state;
		this.image = image;
		this.world = world;
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.headline = headline;
		this.user = user;
	}

	public Event(EventType type, String title, String story, EventState state, World world, String headline) {
		this.type = type;
		this.title = title;
		this.story = story;
		this.state = state;
		this.world = world;
		this.timestamp = new Timestamp(System.currentTimeMillis());
		this.headline = headline;
	}

	public Event(EventType type, String story, World world, User user) {
		this.type = type;
		this.story = story;
		this.world = world;
		this.user = user;
		this.image = user.getPicture();
		this.timestamp = new Timestamp(System.currentTimeMillis());
	}

	public Event(EventType type, String title, String story, EventState state, String image) {
		this.type = type;
		this.story = story;
		this.title = title;
		this.image = image;
		this.state = state;
		this.timestamp = new Timestamp(System.currentTimeMillis());
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
	public boolean equals(Object o) {
		Event e1 = this;
		Event e2 = (Event)o;
		
		if(Objects.equals(e1.getTitle(), e2.getTitle()))
			return true;
		return false;
	}
	
	
	

}

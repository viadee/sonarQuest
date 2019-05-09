package com.viadee.sonarquest.entities;

import java.sql.Timestamp;

import com.viadee.sonarquest.constants.EventState;
import com.viadee.sonarquest.constants.EventType;

public class EventDto {

	private Long id;
	private EventType type;
	private String title;
	private String story;
	private EventState state;
	private String image;
	private String headline;
	private Long worldId;
	private Long userId;
	private Timestamp timestamp;

    
	public EventDto() {}
	

	public EventDto(Long id, EventType type, String title, String story, EventState state, String image,
			String headline, Long worldId, Long userId, Timestamp timestamp) {
		super();
		this.id = id;
		this.type = type;
		this.title = title;
		this.story = story;
		this.state = state;
		this.image = image;
		this.headline = headline;
		this.worldId = worldId;
		this.userId = userId;
		this.timestamp = timestamp;
	}


	public EventDto(Event event) {
		Long uId = null;
		
		if (event.getUser() != null) {
			uId = event.getUser().getId();
		}		
		
		
		this.id = event.getId();
		this.type = event.getType();
		this.title = event.getTitle();
		this.story = event.getStory();
		this.state = event.getState();
		this.headline = event.getHeadline();
		this.image = event.getImage();
		this.worldId = event.getWorld().getId();
		this.userId = uId;
		this.timestamp = event.getTimestamp();
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public EventType getType() {
		return type;
	}


	public void setType(EventType type) {
		this.type = type;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
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


	public String getHeadline() {
		return headline;
	}


	public void setHeadline(String headline) {
		this.headline = headline;
	}


	public Long getWorldId() {
		return worldId;
	}


	public void setWorldId(Long worldId) {
		this.worldId = worldId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}	
	
	
}

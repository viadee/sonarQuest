package com.viadee.sonarquest.entities;

import java.sql.Timestamp;

import com.viadee.sonarquest.constants.EventState;
import com.viadee.sonarquest.constants.EventType;

public class EventDto {

	private Long id;
	private EventType type;
	private Long typeId;
	private String title;
	private String story;
	private EventState state;
	private String image;
	private String headline;	// FREE to Use
	private Long worldId;
	private Long userId;
	private Timestamp timestamp;

    
	public EventDto() {}
	

	public EventDto(Event event) {
		if (event.getUser() != null) {
			this.userId = event.getUser().getId();
		} else {
			this.userId = null;
		}
		
		if (event.getWorld() != null) {
			this.worldId = event.getWorld().getId();
		} else {
			this.worldId = null;
		}
		
		
		this.id = event.getId();
		this.type = event.getType();
		this.typeId = event.getTypeId();
		this.title = event.getTitle();
		this.story = event.getStory();
		this.state = event.getState();
		this.headline = event.getHeadline();
		this.image = event.getImage();
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
	
	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}


	public EventState getState() {
		return state;
	}


	public void setStory(String story) {
		this.story = story;
	}


	public void setState(EventState state) {
		this.state = state;
	}


	public void setHeadline(String headline) {
		this.headline = headline;
	}


	public String getHeadline() {
		return headline;
	}


	public void setWorldId(Long worldId) {
		this.worldId = worldId;
	}


	public Long getWorldId() {
		return worldId;
	}
	

	public void setImage(String image) {
		this.image = image;
	}


	public String getImage() {
		return image;
	}


	public Timestamp getTimestamp() {
		return timestamp;
	}


	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}


	public Long getTypeId() {
		return typeId;
	}


	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}	
	
	
}

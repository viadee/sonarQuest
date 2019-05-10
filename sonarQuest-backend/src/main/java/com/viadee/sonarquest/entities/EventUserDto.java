package com.viadee.sonarquest.entities;

import java.util.List;

public class EventUserDto {

	private List<UserDto> userDtos;
	private List<EventDto> eventDtos;
	
	public EventUserDto() {}
	
	public EventUserDto(List<UserDto> userDtos, List<EventDto> eventDtos) {
		super();
		this.userDtos = userDtos;
		this.eventDtos = eventDtos;
	}

	public List<UserDto> getUserDtos() {
		return userDtos;
	}

	public void setUserDtos(List<UserDto> userDtos) {
		this.userDtos = userDtos;
	}

	public List<EventDto> getEventDtos() {
		return eventDtos;
	}

	public void setEventDtos(List<EventDto> eventDtos) {
		this.eventDtos = eventDtos;
	}


	
	
}

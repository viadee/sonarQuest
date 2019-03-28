package com.viadee.sonarquest.entities;

public class MessageDto {
	private String message;
	private Long userId;

	
	public MessageDto() {
	}
	
	public MessageDto(String message, Long userId) {
		super();
		this.message = message;
		this.userId = userId;
	}


	public String getMessage() {
		return message;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
}

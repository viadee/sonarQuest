package com.viadee.sonarquest.dto;

import javax.persistence.Column;

public class SpecialTaskDTO extends TaskDTO {
	private String message;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}

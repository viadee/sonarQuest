package com.viadee.sonarquest.dto;

public enum SonarQuestStatusDTO {
	// @formatter:off
	OPEN("OPEN"), CLOSED("CLOSED"), SOLVED("SOLVED");
	// @formatter:on

	private String text;

	private SonarQuestStatusDTO(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	public static SonarQuestStatusDTO getStatusFromText(String text) {
		switch (text.toUpperCase()) {
		case "OPEN":
			return OPEN;
		case "CLOSED":
			return CLOSED;
		case "SOLVED":
			return SOLVED;
		default:

			return null;
		}
	}

}

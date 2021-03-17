package com.viadee.sonarquest.externalressources;

public enum SonarQubeSeverity {

	BLOCKER(5), CRITICAL(4), MAJOR(3), MINOR(2), INFO(1);

	private int rank;

	private SonarQubeSeverity(int rank) {
		this.rank = rank;
	}

	public Integer getRank() {
		return rank;
	}

	public static SonarQubeSeverity fromString(String severityName) {
		for (SonarQubeSeverity severity : SonarQubeSeverity.values()) {
			if (severity.name().equalsIgnoreCase(severityName)) {
				return severity;
			}
		}
		throw new IllegalArgumentException("No matching SonarQubeSeverity found for severityName " + severityName);
	}

}

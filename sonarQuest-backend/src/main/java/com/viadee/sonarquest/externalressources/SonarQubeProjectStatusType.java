package com.viadee.sonarquest.externalressources;

public enum SonarQubeProjectStatusType {
	OK (1), WARN (2), ERROR (3), NONE (4);
	
	private int rank;

	private SonarQubeProjectStatusType(int rank) {
		this.rank = rank;
	}

	public Integer getRank() {
		return rank;
	}

	public static SonarQubeProjectStatusType fromString(String statusType) {
		for (SonarQubeProjectStatusType status : SonarQubeProjectStatusType.values()) {
			if (status.name().equalsIgnoreCase(statusType)) {
				return status;
			}
		}
		return null;
	}
	
}

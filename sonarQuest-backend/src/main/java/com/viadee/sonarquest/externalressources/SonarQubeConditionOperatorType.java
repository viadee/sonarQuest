package com.viadee.sonarquest.externalressources;

public enum SonarQubeConditionOperatorType {
	EQ (1), NE (2), LT (3), GT (4);
	
	private int rank;

	private SonarQubeConditionOperatorType(int rank) {
		this.rank = rank;
	}

	public Integer getRank() {
		return rank;
	}

	public static SonarQubeConditionOperatorType fromString(String conditionType) {
		for (SonarQubeConditionOperatorType status : SonarQubeConditionOperatorType.values()) {
			if (status.name().equalsIgnoreCase(conditionType)) {
				return status;
			}
		}
		return null;
	}
	
	public static String generateText(String conditionType) {
		return generateText(fromString(conditionType));
	}
	
	public static String generateText(SonarQubeConditionOperatorType conditionType) {
		switch (conditionType) {
		case EQ:
			return "is equal";
		case NE:
			return "is not";
		case LT: 
			return "is lower than";
		case GT:
			return "is greater than";
		default:
			return "";
		}
	}
}

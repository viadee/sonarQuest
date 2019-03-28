package com.viadee.sonarquest.skillTree.dto.skillTreeDiagram;

public class SkillTreeObjectDTO {

	private String id;
	private String label;
	private int repeats;
	private int requiredRepetitions;

	public SkillTreeObjectDTO() {
	}

	public SkillTreeObjectDTO(String id, String label) {
		this.id = id;
		this.label = label;
	}

	public SkillTreeObjectDTO(String id, String label, int repeats, int requiredRepetitions) {
		this.id = id;
		this.label = label;
		this.repeats = repeats;
		this.requiredRepetitions = requiredRepetitions;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public int getRepeats() {
		return repeats;
	}

	public void setRepeats(int repeats) {
		this.repeats = repeats;
	}

	public int getRequiredRepetitions() {
		return requiredRepetitions;
	}

	public void setRequiredRepetitions(int requiredRepetitions) {
		this.requiredRepetitions = requiredRepetitions;
	}

}

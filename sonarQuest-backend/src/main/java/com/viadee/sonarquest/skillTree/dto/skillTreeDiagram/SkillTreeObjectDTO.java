package com.viadee.sonarquest.skillTree.dto.skillTreeDiagram;

import java.util.Objects;

import com.viadee.sonarquest.skillTree.entities.UserSkillToSkillTreeUser;

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

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof SkillTreeObjectDTO))
			return false;
		SkillTreeObjectDTO that = (SkillTreeObjectDTO) o;
		return Objects.equals(this.getId(), that.getId()) && Objects.equals(this.getLabel(), that.getLabel())
				&& Objects.equals(this.getRepeats(), that.getRepeats())
				&& Objects.equals(this.getRequiredRepetitions(), that.getRequiredRepetitions());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, label, requiredRepetitions, repeats);
	}
}

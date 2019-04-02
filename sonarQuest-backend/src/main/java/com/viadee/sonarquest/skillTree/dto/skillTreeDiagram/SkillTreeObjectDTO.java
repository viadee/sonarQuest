package com.viadee.sonarquest.skillTree.dto.skillTreeDiagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.viadee.sonarquest.skillTree.dto.SonarRuleDTO;
import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.entities.UserSkillToSkillTreeUser;

public class SkillTreeObjectDTO {

	private String id;
	private String label;
	private int repeats;
	private int requiredRepetitions;
	private List<SonarRuleDTO> sonarRuleDTOs;

	public SkillTreeObjectDTO() {
		this.sonarRuleDTOs = new ArrayList<SonarRuleDTO>();
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

	public List<SonarRuleDTO> getSonarRuleDTOs() {
		return sonarRuleDTOs;
	}

	public void setSonarRuleDTOs(List<SonarRuleDTO> sonarRuleDTOs) {
		this.sonarRuleDTOs = sonarRuleDTOs;
	}

	public void addRuleKey(String key, String name) {
		this.sonarRuleDTOs.add(new SonarRuleDTO(name,key));
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
				&& Objects.equals(this.getRequiredRepetitions(), that.getRequiredRepetitions())
				&& Objects.equals(this.getSonarRuleDTOs(), that.getSonarRuleDTOs());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, label, requiredRepetitions, repeats);
	}
}

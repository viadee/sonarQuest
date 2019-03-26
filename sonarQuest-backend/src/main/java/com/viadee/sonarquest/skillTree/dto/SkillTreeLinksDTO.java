package com.viadee.sonarquest.skillTree.dto;

public class SkillTreeLinksDTO {

	private String source;
	private String target;

	public SkillTreeLinksDTO() {
		super();
	}

	public SkillTreeLinksDTO(String source, String target) {
		super();
		this.source = source;
		this.target = target;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}

}

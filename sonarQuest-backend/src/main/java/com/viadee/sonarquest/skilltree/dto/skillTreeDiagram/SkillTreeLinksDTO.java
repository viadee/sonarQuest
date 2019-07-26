package com.viadee.sonarquest.skilltree.dto.skillTreeDiagram;

public class SkillTreeLinksDTO {

	private String source;
	private String target;

	public SkillTreeLinksDTO(String source, String target) {
		super();
		this.source = source;
		this.target = target;
	}
	
	public SkillTreeLinksDTO(Long source, Long target) {
		super();
		this.source = String.valueOf(source);
		this.target = String.valueOf(target);
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

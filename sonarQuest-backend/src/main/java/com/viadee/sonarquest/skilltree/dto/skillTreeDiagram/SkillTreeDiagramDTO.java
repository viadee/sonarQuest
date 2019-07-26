package com.viadee.sonarquest.skilltree.dto.skillTreeDiagram;

import java.util.ArrayList;
import java.util.List;

public class SkillTreeDiagramDTO {

	private List<SkillTreeObjectDTO> nodes;
	private List<SkillTreeLinksDTO> links;

	public SkillTreeDiagramDTO() {
		super();
	}


	public List<SkillTreeObjectDTO> getNodes() {
		if (nodes == null) {
			nodes = new ArrayList<>();
		}
		return nodes;
	}

	public void addNode(SkillTreeObjectDTO skillTreeObjectDTO) {

		this.getNodes().add(skillTreeObjectDTO);
	}

	public List<SkillTreeLinksDTO> getLinks() {
		if (links == null) {
			links = new ArrayList<>();
		}
		return links;
	}

	public void addLine(SkillTreeLinksDTO skillTreeLineDTO) {
		this.getLinks().add(skillTreeLineDTO);
	}

}

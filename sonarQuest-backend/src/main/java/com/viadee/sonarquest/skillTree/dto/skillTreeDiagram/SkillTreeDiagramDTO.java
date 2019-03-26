package com.viadee.sonarquest.skillTree.dto.skillTreeDiagram;

import java.util.ArrayList;
import java.util.List;

import com.viadee.sonarquest.interfaces.UserGratification;

public class SkillTreeDiagramDTO {

	private List<SkillTreeObjectDTO> nodes;
	private List<SkillTreeLinksDTO> links;

	public SkillTreeDiagramDTO() {
		super();
	}

	public SkillTreeDiagramDTO(List<SkillTreeObjectDTO> nodes, List<SkillTreeLinksDTO> links) {
		super();
		this.nodes = nodes;
		this.links = links;
	}

	public List<SkillTreeObjectDTO> getNodes() {
		if (nodes == null) {
			nodes = new ArrayList<SkillTreeObjectDTO>();
		}
		return nodes;
	}

	public void setNodes(List<SkillTreeObjectDTO> nodes) {
		this.nodes = nodes;
	}

	public void addNode(SkillTreeObjectDTO skillTreeObjectDTO) {

		this.getNodes().add(skillTreeObjectDTO);
	}

	public List<SkillTreeLinksDTO> getLinks() {
		if (links == null) {
			links = new ArrayList<SkillTreeLinksDTO>();
		}
		return links;
	}

	public void setLinks(List<SkillTreeLinksDTO> links) {
		this.links = links;
	}

	public void addLine(SkillTreeLinksDTO skillTreeLineDTO) {
		this.getLinks().add(skillTreeLineDTO);
	}

}

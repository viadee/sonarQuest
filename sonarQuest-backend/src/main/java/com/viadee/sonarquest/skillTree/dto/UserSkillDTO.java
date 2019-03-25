package com.viadee.sonarquest.skillTree.dto;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.entities.UserSkill;
import com.viadee.sonarquest.skillTree.entities.UserSkillGroup;

public class UserSkillDTO {

	private String description;
	private String name;
	private boolean isRoot;

	private List<UserSkillDTO> followingUserSkills = new ArrayList<UserSkillDTO>(0);

	//private List<SonarRuleDTO> sonarRules = new ArrayList<SonarRuleDTO>(0);

	

}

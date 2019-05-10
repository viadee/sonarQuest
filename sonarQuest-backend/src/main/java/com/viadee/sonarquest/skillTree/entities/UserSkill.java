package com.viadee.sonarquest.skillTree.entities;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

@Entity
@Table(name = "User_Skill")
public class UserSkill {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "description")
	private String description;

	@Column(name = "skill_name")
	private String name;

	@Column(name = "is_root")
	private boolean isRoot;

	@Column(name = "required_repetitions")
	private int requiredRepetitions;

	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name = "User_Skill_Previous", joinColumns = @JoinColumn(name = "user_skill_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "previous_user_skill_id", referencedColumnName = "id"))
    private List<UserSkill> previousUserSkills = new ArrayList<UserSkill>(0);

	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToMany(cascade = {CascadeType.MERGE, CascadeType.REFRESH})
	@JoinTable(name = "User_Skill_Following", joinColumns = @JoinColumn(name = "user_skill_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "following_user_skill_id", referencedColumnName = "id"))
	private List<UserSkill> followingUserSkills = new ArrayList<UserSkill>(0);

	/*
	 * @ManyToMany(cascade = CascadeType.MERGE)
	 * 
	 * @JoinTable(name = "User_Skill_TO_Sonar_Rule", joinColumns = @JoinColumn(name
	 * = "user_skill_id", referencedColumnName = "id"), inverseJoinColumns
	 * = @JoinColumn(name = "sonar_rule_id", referencedColumnName = "id")) private
	 * List<SonarRule> sonarRules = new ArrayList<SonarRule>(0);
	 */
	
	
	@OneToMany(mappedBy = "userSkill", cascade={CascadeType.MERGE, CascadeType.REFRESH}, orphanRemoval = true)
	private List<SonarRule> sonarRules = new ArrayList<SonarRule>(0);

	@ManyToOne()
	@JoinColumn(name = "user_skill_group_id")
	private UserSkillGroup userSkillGroup;

	@JsonIgnore
	@OneToMany(mappedBy = "userSkill", cascade={CascadeType.MERGE, CascadeType.REFRESH, CascadeType.REMOVE}, orphanRemoval = true)
	private List<UserSkillToSkillTreeUser> userSkillToSkillTreeUsers;

	public UserSkill() {

	}

	public UserSkill(String description, String name, boolean isRoot, UserSkillGroup userSkillGroup, int requiredRepetitions) {
		super();
		this.description = description;
		this.name = name;
		this.isRoot = isRoot;
		this.userSkillGroup = userSkillGroup;
		this.requiredRepetitions = requiredRepetitions;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
    public List<UserSkill> getPreviousUserSkills() {
        return previousUserSkills;
    }

    public void setPreviousUserSkills(List<UserSkill> previousUserSkills) {
        this.previousUserSkills = previousUserSkills;
    }

    public void addPreviousUserSkill(UserSkill userSkill) {
        this.previousUserSkills.add(userSkill);
    }

    public void removePreviousUserSkill(UserSkill userSkill) {
        this.previousUserSkills.remove(userSkill);
    }
    
   
	public List<UserSkill> getFollowingUserSkills() {
		return followingUserSkills;
	}

	public void setFollowingUserSkills(List<UserSkill> followingUserSkills) {
		this.followingUserSkills = followingUserSkills;
	}

	public void addFollowingUserSkill(UserSkill userSkill) {
		this.followingUserSkills.add(userSkill);
	}

	public void removeFollowingUserSkill(UserSkill userSkill) {
		this.followingUserSkills.remove(userSkill);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<SonarRule> getSonarRules() {
		return sonarRules;
	}

	public void setSonarRules(List<SonarRule> sonarRules) {
		this.sonarRules = sonarRules;
	}

	public void addSonarRule(SonarRule sonaRule) {
		this.sonarRules.add(sonaRule);
	}

	public void removeSonarRule(SonarRule sonarRule) {
		this.sonarRules.remove(sonarRule);
	}

	public boolean isRoot() {
		return isRoot;
	}

	public void setRoot(boolean isRoot) {
		this.isRoot = isRoot;
	}

	public UserSkillGroup getUserSkillGroup() {
		return userSkillGroup;
	}

	public void setUserSkillGroup(UserSkillGroup userSkillGroup) {
		this.userSkillGroup = userSkillGroup;
	}

	public List<UserSkillToSkillTreeUser> getUserSkillToSkillTreeUsers() {
		return userSkillToSkillTreeUsers;
	}

	public void setUserSkillToSkillTreeUsers(List<UserSkillToSkillTreeUser> userSkillToSkillTreeUsers) {
		this.userSkillToSkillTreeUsers = userSkillToSkillTreeUsers;
	}

	public void addUserSkillToSkilLTreeUsers(UserSkillToSkillTreeUser userSkillToSkillTreeUser) {
		this.userSkillToSkillTreeUsers.add(userSkillToSkillTreeUser);
	}

	public int getRequiredRepetitions() {
		return requiredRepetitions;
	}

	public void setRequiredRepetitions(int requiredRepetitions) {
		this.requiredRepetitions = requiredRepetitions;
	}

}

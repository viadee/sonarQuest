package com.viadee.sonarquest.skilltree.dto;

import java.util.List;

public class UserSkillDTO {

    private Long id;
    private String description;
    private String name;
    private boolean isRoot;
    private int requiredRepetitions;
    private UserSkillGroupDTO userSkillGroup;
    private List<SonarRuleDTO> sonarRules;
    private List<UserSkillDTO> followingUserSkills;
    private List<UserSkillDTO> previousUserSkills;
    private Double score;

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean root) {
        isRoot = root;
    }

    public int getRequiredRepetitions() {
        return requiredRepetitions;
    }

    public void setRequiredRepetitions(int requiredRepetitions) {
        this.requiredRepetitions = requiredRepetitions;
    }

    public UserSkillGroupDTO getUserSkillGroup() {
        return userSkillGroup;
    }

    public void setUserSkillGroup(UserSkillGroupDTO userSkillGroup) {
        this.userSkillGroup = userSkillGroup;
    }

    public List<SonarRuleDTO> getSonarRules() {
        return sonarRules;
    }

    public void setSonarRules(List<SonarRuleDTO> sonarRules) {
        this.sonarRules = sonarRules;
    }

    public List<UserSkillDTO> getFollowingUserSkills() {
        return followingUserSkills;
    }

    public void setFollowingUserSkills(List<UserSkillDTO> followingUserSkills) {
        this.followingUserSkills = followingUserSkills;
    }

    public List<UserSkillDTO> getPreviousUserSkills() {
        return previousUserSkills;
    }

    public void setPreviousUserSkills(List<UserSkillDTO> previousUserSkills) {
        this.previousUserSkills = previousUserSkills;
    }
}

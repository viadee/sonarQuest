package com.viadee.sonarquest.skilltree.dto.skillTreeDiagram;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.viadee.sonarquest.skilltree.dto.SonarRuleDTO;

public class SkillTreeObjectDTO {

    private String id;
    private String label;
    private String description;
    private int repeats;
    private int requiredRepetitions;
    private List<SonarRuleDTO> sonarRuleDTOs;
    private String backgroundColor;
    private String textColor;
    private String groupIcon;
    private int learnCoverage;
    private boolean isRoot;

    public SkillTreeObjectDTO() {
        this.sonarRuleDTOs = new ArrayList<>();
    }



    public SkillTreeObjectDTO(String id, String label, String groupIcon, boolean isRoot) {
        this.id = id;
        this.label = label;
        this.groupIcon = groupIcon;
        this.isRoot = isRoot;
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
        this.sonarRuleDTOs.add(new SonarRuleDTO(name, key));
    }

    public String getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(String backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public String getTextColor() {
        return textColor;
    }

    public void setTextColor(String textColor) {
        this.textColor = textColor;
    }

    public int getLearnCoverage() {
        return learnCoverage;
    }

    public void setLearnCoverage(int learnCoverage) {
        this.learnCoverage = learnCoverage;
    }

    public String getGroupIcon() {
        return groupIcon;
    }

    public void setGroupIcon(String groupIcon) {
        this.groupIcon = groupIcon;
    }

    public boolean isRoot() {
        return isRoot;
    }

    public void setRoot(boolean isRoot) {
        this.isRoot = isRoot;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

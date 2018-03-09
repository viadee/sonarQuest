package com.viadee.sonarQuest.dtos;

import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.Skill;

import java.util.List;

public class AvatarClassDto {

    private Long id;

    private String name;

    private List<Developer> developers;

    private List<Skill> skills;

    public AvatarClassDto() {

    }

    public AvatarClassDto(Long id, String name, List<Developer> developers, List<Skill> skills) {
        this.id = id;
        this.name = name;
        this.developers = developers;
        this.skills = skills;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}

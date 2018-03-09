package com.viadee.sonarQuest.dtos;

import com.viadee.sonarQuest.entities.AvatarClass;

import java.util.List;

public class SkillDto {

    private Long id;

    private String name;

    private String type;

    private Long value;

    private List<AvatarClass> avatarClasses;

    public SkillDto() {
    }

    public SkillDto(Long id, String name, String type, Long value, List<AvatarClass> avatarClasses) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.value = value;
        this.avatarClasses = avatarClasses;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public List<AvatarClass> getAvatarClasses() {
        return avatarClasses;
    }

    public void setAvatarClasses(List<AvatarClass> avatarClasses) {
        this.avatarClasses = avatarClasses;
    }
}

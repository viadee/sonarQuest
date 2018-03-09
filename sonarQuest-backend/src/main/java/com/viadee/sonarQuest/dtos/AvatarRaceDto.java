package com.viadee.sonarQuest.dtos;

import com.viadee.sonarQuest.entities.Developer;

import java.util.List;

public class AvatarRaceDto {

    private Long id;

    private String name;

    private List<Developer> developers;

    public AvatarRaceDto() {
    }

    public AvatarRaceDto(Long id, String name, List<Developer> developers) {
        this.id = id;
        this.name = name;
        this.developers = developers;
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
}

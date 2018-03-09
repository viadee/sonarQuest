package com.viadee.sonarQuest.dtos;


import com.viadee.sonarQuest.entities.Developer;

import java.util.List;

public class LevelDto {


    private Long id;

    private String name;

    private Long min;

    private Long max;

    private List<Developer> developers;

    public LevelDto() {
    }

    public LevelDto(Long id, String name, Long min, Long max, List<Developer> developers) {
        this.id = id;
        this.name = name;
        this.min = min;
        this.max = max;
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

    public Long getMin() {
        return min;
    }

    public void setMin(Long min) {
        this.min = min;
    }

    public Long getMax() {
        return max;
    }

    public void setMax(Long max) {
        this.max = max;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }
}

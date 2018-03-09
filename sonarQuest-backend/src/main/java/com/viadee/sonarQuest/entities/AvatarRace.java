package com.viadee.sonarQuest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Avatar_Race")
public class AvatarRace {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="avatarRace",cascade = CascadeType.ALL)
    private List<Developer> developers;

    public AvatarRace() {
    }

    public AvatarRace(String name) {
        this.name = name;
    }

    public AvatarRace(String name, List<Developer> developers) {
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

    @JsonIgnore
    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }
}

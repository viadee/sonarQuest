package com.viadee.sonarQuest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Avatar_Class")
public class AvatarClass {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy="avatarClass",cascade = CascadeType.ALL)
    private List<Developer> developers;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Avatar_Class_Skill", joinColumns = @JoinColumn(name = "avatar_class_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    private List<Skill> skills;


    public AvatarClass() {
    }

    public AvatarClass(String name) {
        this.name = name;
    }

    public AvatarClass(String name, List<Developer> developers, List<Skill> skills) {
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

    @JsonIgnore
    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    @JsonIgnore
    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
}

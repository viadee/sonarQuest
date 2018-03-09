package com.viadee.sonarQuest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="Skill")
public class Skill {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private String type;

    @Column(name = "value")
    private Long value;

    @ManyToMany(mappedBy = "skills", cascade = CascadeType.ALL)
    private List<AvatarClass> avatarClasses;

    public Skill() {
    }

    public Skill(String name, String type, Long value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public Skill(String name, String type, Long value, List<AvatarClass> avatarClasses) {
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

    @JsonIgnore
    public List<AvatarClass> getAvatarClasses() {
        return avatarClasses;
    }

    public void setAvatarClasses(List<AvatarClass> avatarClasses) {
        this.avatarClasses = avatarClasses;
    }
}

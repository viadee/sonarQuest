package com.viadee.sonarquest.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viadee.sonarquest.constants.SkillType;

@Entity
@Table(name = "Skill")
public class Skill {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private SkillType type;

    @Column(name = "value")
    private Long value;

    @JsonIgnore
    @ManyToMany(mappedBy = "skills", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private List<AvatarClass> avatarClasses;

    public Skill() {
    }

    public Skill(final String name, final SkillType type, final Long value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public Skill(final String name, final SkillType type, final Long value, final List<AvatarClass> avatarClasses) {
        this.name = name;
        this.type = type;
        this.value = value;
        this.avatarClasses = avatarClasses;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public SkillType getType() {
        return type;
    }

    public void setType(final SkillType type) {
        this.type = type;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(final Long value) {
        this.value = value;
    }

    public List<AvatarClass> getAvatarClasses() {
        return avatarClasses;
    }

    public void setAvatarClasses(final List<AvatarClass> avatarClasses) {
        this.avatarClasses = avatarClasses;
    }
}

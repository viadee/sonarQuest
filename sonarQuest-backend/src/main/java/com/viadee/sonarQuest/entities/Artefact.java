package com.viadee.sonarQuest.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "Artefact")
public class Artefact {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "icon")
    private String icon;

    @Column(name = "price")
    private Long price;

    @ManyToOne
    @JoinColumn(name="level_id")
    private Level minLevel;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Artefact_Skill", joinColumns = @JoinColumn(name = "artefact_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    private List<Skill> skills;

    @ManyToMany(mappedBy = "artefacts", cascade = CascadeType.ALL)
    private List<Developer> developers;

    public Artefact() {
    }

    public Artefact(String name, String icon, Long price, Level minLevel, List<Skill> skills) {
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.minLevel = minLevel;
        this.skills = skills;
    }

    public Artefact(String name, String icon, Long price, Level minLevel, List<Skill> skills, List<Developer> developers) {
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.minLevel = minLevel;
        this.skills = skills;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @JsonIgnore
    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Level getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(Level minLevel) {
        this.minLevel = minLevel;
    }
}

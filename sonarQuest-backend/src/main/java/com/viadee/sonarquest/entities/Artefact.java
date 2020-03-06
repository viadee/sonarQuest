package com.viadee.sonarquest.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "Artefact")
public class Artefact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "icon")
    private String icon;

    @Column(name = "price")
    private Long price;

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "description")
    private String description;

    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST })
    @JoinColumn(name = "level_id")
    private Level minLevel;

    @ManyToMany(cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    @JoinTable(name = "Artefact_Skill", joinColumns = @JoinColumn(name = "artefact_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    private List<Skill> skills;

    @JsonIgnore
    @ManyToMany(mappedBy = "artefacts", cascade = { CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
    private List<User> users;

    @Column(name = "on_marketplace")
    private boolean onMarketplace;

    public Artefact() {
        this.onMarketplace = true;
    }

    public Artefact(final String name, final String icon, final Long price, final Level minLevel,
            final List<Skill> skills) {
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.minLevel = minLevel;
        this.skills = skills;
        this.onMarketplace = true;
    }

    public Artefact(final String name, final Long price, final Long quantity, final String description) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.onMarketplace = true;
    }

    public Artefact(final String name, final String icon, final Long price, final Long quantity,
            final String description) {
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.onMarketplace = true;
    }

    public Artefact(final String name, final String icon, final Long price, final Level minLevel,
            final List<Skill> skills,
            final List<User> users) {
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.minLevel = minLevel;
        this.skills = skills;
        this.users = users;
        this.onMarketplace = true;
    }

    public Artefact(final Long id, final String name, final String icon, final Long price, final Long quantity,
            final String description, final Level minLevel,
            final List<Skill> skills, final List<User> users) {
        this.id = id;
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
        this.minLevel = minLevel;
        this.skills = skills;
        this.users = users;
        this.onMarketplace = true;
    }

}

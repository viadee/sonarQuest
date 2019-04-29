package com.viadee.sonarquest.entities;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @Column(name = "quantity")
    private Long quantity;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level minLevel;

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JoinTable(name = "Artefact_Skill", joinColumns = @JoinColumn(name = "artefact_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "skill_id", referencedColumnName = "id"))
    private List<Skill> skills;

    @JsonIgnore
    @ManyToMany(mappedBy = "artefacts", cascade = CascadeType.ALL)
    private List<User> users;

    public Artefact() {
    }

    public Artefact(final String name, final String icon, final Long price, final Level minLevel,
            final List<Skill> skills) {
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.minLevel = minLevel;
        this.skills = skills;
    }

    public Artefact(final String name, final Long price, final Long quantity, final String description) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
    }

    public Artefact(final String name, final String icon, final Long price, final Long quantity,
            final String description) {
        this.name = name;
        this.icon = icon;
        this.price = price;
        this.quantity = quantity;
        this.description = description;
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

    public String getIcon() {
        return icon;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(final List<Skill> skills) {
        this.skills = skills;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(final Long price) {
        this.price = price;
    }

    public Level getMinLevel() {
        return minLevel;
    }

    public void setMinLevel(final Level minLevel) {
        this.minLevel = minLevel;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(final Long quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(final String description) {
        this.description = description;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Artefact other = (Artefact) obj;
        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

}

package com.viadee.sonarQuest.entities;

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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "Developer")
public class Developer {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "gold")
    private Long gold;

    @Column(name = "xp")
    private Long xp;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @Column(name = "picture")
    private String picture;

    @Column(name = "about_me")
    private String aboutMe;

    @ManyToOne
    @JoinColumn(name = "avatar_class_id")
    private AvatarClass avatarClass;

    @ManyToOne
    @JoinColumn(name = "avatar_race_id")
    private AvatarRace avatarRace;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "Developer_Artefact", joinColumns = @JoinColumn(name = "developer_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "artefact_id", referencedColumnName = "id"))
    private List<Artefact> artefacts;

    @ManyToMany(mappedBy = "developers", cascade = CascadeType.ALL)
    private List<Adventure> adventures;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "developer")
    private List<Participation> participations;

    @Column(name = "deleted")
	private Boolean deleted = false;

    @ManyToOne
    @JoinColumn(name = "world_id")
    private World world;


	public Developer() {
    }

    public Developer(final String username) {
    	this.username = username;
    }
    
    public Developer(final String username, final Long gold, final Long xp, final Level level, final String picture,
            final String aboutMe, final AvatarClass avatarClass, final AvatarRace avatarRace) {
        this.username = username;
        this.gold = gold;
        this.xp = xp;
        this.level = level;
        this.picture = picture;
        this.aboutMe = aboutMe;
        this.avatarClass = avatarClass;
        this.avatarRace = avatarRace;
    }

    public Developer(final String username, final Long gold, final Long xp, final Level level, final String picture,
            final String aboutMe, final AvatarClass avatarClass, final AvatarRace avatarRace,
            final List<Artefact> artefacts, final List<Adventure> adventures,
            final List<Participation> participations) {
        this.username = username;
        this.gold = gold;
        this.xp = xp;
        this.level = level;
        this.picture = picture;
        this.aboutMe = aboutMe;
        this.avatarClass = avatarClass;
        this.avatarRace = avatarRace;
        this.artefacts = artefacts;
        this.adventures = adventures;
        this.participations = participations;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public Long getGold() {
        return gold;
    }

    public void setGold(final Long gold) {
        this.gold = gold;
    }

    public Long getXp() {
        return xp;
    }

    public void setXp(final Long xp) {
        this.xp = xp;
    }

    @JsonIgnore
    public Level getLevel() {
        return level;
    }

    public void setLevel(final Level level) {
        this.level = level;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(final String picture) {
        this.picture = picture;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(final String aboutMe) {
        this.aboutMe = aboutMe;
    }

    @JsonIgnore
    public AvatarClass getAvatarClass() {
        return avatarClass;
    }

    public void setAvatarClass(final AvatarClass avatarClass) {
        this.avatarClass = avatarClass;
    }

    @JsonIgnore
    public AvatarRace getAvatarRace() {
        return avatarRace;
    }

    public void setAvatarRace(final AvatarRace avatarRace) {
        this.avatarRace = avatarRace;
    }

    public List<Artefact> getArtefacts() {
        return artefacts;
    }

    public void setArtefacts(final List<Artefact> artefacts) {
        this.artefacts = artefacts;
    }

    @JsonIgnore
    public List<Adventure> getAdventures() {
        return adventures;
    }

    public void setAdventures(final List<Adventure> adventures) {
        this.adventures = adventures;
    }

    @JsonIgnore
    public List<Participation> getParticipations() {
        return participations;
    }

    public void setParticipations(final List<Participation> participations) {
        this.participations = participations;
    }

    /**
     * Adds the specified amount of gold.
     * 
     * @param gold
     *            the amount to add, must be positive or zero.
     */
    public void addGold(final long gold) {
        Validate.isTrue(gold >= 0);
        this.gold += gold;
    }

    /**
     * Adds the specified amount of XPerience Points.
     * 
     * @param xp
     *            the amount to add, must be positive or zero.
     */
    public void addXp(final long xp) {
        Validate.isTrue(xp >= 0);
        this.xp += xp;
    }

	public boolean isDeleted() {		
		return deleted;
	}
	
	public void setDeleted() {
		this.deleted = true;
	}
	
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
}

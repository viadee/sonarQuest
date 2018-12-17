package com.viadee.sonarquest.entities;

import java.util.ArrayList;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.Validate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Objects;

@Entity
@Table(name = "User")
public class User {

	@Id
	@GeneratedValue
	private Long id;

	@Column(name = "username")
	private String username;

	@Column(name = "password")
	private String password;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;

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

	@Column(name = "gold")
	private Long gold;

	@Column(name = "xp")
	private Long xp;

	@ManyToOne
	@JoinColumn(name = "level_id")
	private Level level;

	@ManyToOne
	@JoinColumn(name = "current_world_id")
	private World currentWorld;

	@JsonIgnore
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "User_To_World", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "world_id", referencedColumnName = "id"))
	private List<World> worlds = new ArrayList<>(0);

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "User_Artefact", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "artefact_id", referencedColumnName = "id"))
	private List<Artefact> artefacts = new ArrayList<>(0);

	@JsonIgnore
	@ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
	private List<Adventure> adventures = new ArrayList<>(0);

	@JsonIgnore
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
	private List<Participation> participations = new ArrayList<>(0);

	@OneToOne(mappedBy = "user", orphanRemoval = true)
	private UiDesign uiDesign;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(final String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(final Role role) {
		this.role = role;
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

	public AvatarClass getAvatarClass() {
		return avatarClass;
	}

	public void setAvatarClass(final AvatarClass avatarClass) {
		this.avatarClass = avatarClass;
	}

	public AvatarRace getAvatarRace() {
		return avatarRace;
	}

	public void setAvatarRace(final AvatarRace avatarRace) {
		this.avatarRace = avatarRace;
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

	public Level getLevel() {
		return level;
	}

	public void setLevel(final Level level) {
		this.level = level;
	}

	public List<World> getWorlds() {
		return worlds;
	}

	public void setWorlds(final List<World> worlds) {
		this.worlds = worlds;
	}

	public void addWorld(final World world) {
		getWorlds().add(world);
	}

	public void removeWorld(final World world) {
		getWorlds().remove(world);
	}

	public List<Artefact> getArtefacts() {
		return artefacts;
	}

	public void setArtefacts(final List<Artefact> artefacts) {
		this.artefacts = artefacts;
	}

	public List<Adventure> getAdventures() {
		return adventures;
	}

	public void setAdventures(final List<Adventure> adventures) {
		this.adventures = adventures;
	}

	public List<Participation> getParticipations() {
		return participations;
	}

	public void setParticipations(final List<Participation> participations) {
		this.participations = participations;
	}

	public World getCurrentWorld() {
		return currentWorld;
	}

	public void setCurrentWorld(final World currentWorld) {
		this.currentWorld = currentWorld;
	}

	public UiDesign getUiDesign() {
		return uiDesign;
	}

	public void setUiDesign(final UiDesign uiDesign) {
		this.uiDesign = uiDesign;
	}

	public boolean isGamemaster() {
		return getRole().getName() == RoleName.GAMEMASTER;
	}

	public boolean isAdmin() {
		return getRole().getName() == RoleName.ADMIN;
	}

	public boolean isDeveloper() {
		return getRole().getName() == RoleName.DEVELOPER;
	}

	@Override
	public int hashCode() {
		return this.getId() == null ? super.hashCode() : Objects.hashCode(this.getId());
	}

	@Override
	public boolean equals(final Object that) {
		return this.getId() == null ? this == that
				: that != null && this.getClass().isInstance(that)
						&& Objects.equal(this.getId(), ((User) that).getId());
	}

}

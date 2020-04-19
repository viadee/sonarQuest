package com.viadee.sonarquest.entities;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viadee.sonarquest.interfaces.Reward;

/**
 * A raid is a game mode in which one fights a monster.
 * It consists of a monster and a reward (=Gold, XP).
 */
@MappedSuperclass
public abstract class BaseRaid extends Auditable implements Reward {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "visible")
	protected Boolean visible;

	@Column(name = "title")
	protected String title;

	@Column(name = "monster_name")
	protected String monsterName;

	@Column(name = "monster_image")
	protected String monsterImage;

	@Column(name = "gold")
	protected Long gold;

	@Column(name = "xp")
	protected Long xp;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "world_id")
	protected World world;

	public BaseRaid() {
		this("", "", "", 0l, 0l, null);
	}

	public BaseRaid(String title, String monsterName, String monsterImage, Long gold, Long xp, World world) {
		super();
		this.title = title;
		this.monsterName = monsterName;
		this.monsterImage = monsterImage;
		this.gold = gold;
		this.xp = xp;
		this.world = world;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMonsterName() {
		return monsterName;
	}

	public void setMonsterName(String monsterName) {
		this.monsterName = monsterName;
	}

	public String getMonsterImage() {
		return monsterImage;
	}

	public void setMonsterImage(String monsterImage) {
		this.monsterImage = monsterImage;
	}

	public Long getGold() {
		return gold;
	}

	public void setGold(Long gold) {
		this.gold = gold;
	}

	public Long getXp() {
		return xp;
	}

	public void setXp(Long xp) {
		this.xp = xp;
	}

	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}
	
	/**
	 * Update title, xp, gold, monster (= image and name), visible
	 * @param raid
	 */
	@JsonIgnore
	public void updateBaseRaid(BaseRaid raid) {
		this.title = raid.getTitle();
		this.xp = raid.getXp();
		this.gold = raid.getGold();
		this.monsterImage = raid.getMonsterImage();
		this.monsterName = raid.getMonsterName();
		this.visible = raid.getVisible();
	}
}

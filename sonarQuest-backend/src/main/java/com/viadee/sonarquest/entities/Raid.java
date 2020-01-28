package com.viadee.sonarquest.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.viadee.sonarquest.constants.AdventureState;
import com.viadee.sonarquest.interfaces.RaidI;

@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "Raid_TYPE")
@Entity
@Table(name = "raid")
public class Raid implements RaidI {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "startdate")
	private Date startdate;

	@Column(name = "enddate")
	private Date enddate;

	@Column(name = "visible")
	private Boolean visible;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;
	
	@Column(name="monster_name")
	private String monsterName;
	
	@Column(name="monster_image")
	private String monsterImage;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private AdventureState status;

	@Column(name = "gold")
	private Long gold;

	@Column(name = "xp")
	private Long xp;

	@Column(name = "goldLoss")
	private Long goldLoss;

	@Column(name = "xpLoss")
	private Long xpLoss;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "world_id")
	private World world;

	@OneToMany(mappedBy = "raid", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Quest> quests;

	@JsonIgnore
	@ManyToMany(cascade = { CascadeType.ALL })
	@JoinTable(name = "Raid_User", joinColumns = @JoinColumn(name = "raid_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"))
	private List<User> users;

	public Raid() {
	}
	
	public Raid(String title, String monsterName, String monsterImage, Long gold, Long xp, World world) {
		super();
		this.title = title;
		this.monsterName = monsterName;
		this.monsterImage = monsterImage;
		this.gold = gold;
		this.xp = xp;
		this.world = world;
		this.status = AdventureState.OPEN;
		this.setStartdate(new Date(System.currentTimeMillis()));
	}

	public Raid(final String title, final String description, final AdventureState status, final Long gold,
			final Long xp, World world) {
		this.title = title;
		this.description = description;
		this.status = status;
		this.gold = gold;
		this.xp = xp;
		this.world = world;
		this.setStartdate(new Date(System.currentTimeMillis()));
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

	public Long getId() {
		return id;
	}

	public void setId(final Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public AdventureState getStatus() {
		return status;
	}

	public void setStatus(final AdventureState status) {
		this.status = status;
	}

	public List<Quest> getQuests() {
		if (quests == null)
			quests = new ArrayList<Quest>();
		return quests;
	}

	public void setQuests(List<Quest> quests) {
		this.quests = quests;
	}

	public void addQuest(final Quest quest) {
		getQuests().add(quest);
		quest.setRaid(this);
	}

//	@JsonIgnore
//	public List<User> getUsers() {
//		return users;
//	}
//
//	public void setUsers(final List<User> users) {
//		this.users = users;
//	}
//
//	public synchronized void addUser(final User user) {
//		if (users == null) {
//			users = new ArrayList<>();
//		}
//		users.add(user);
//	}

	public World getWorld() {
		return world;
	}

	public void setWorld(final World world) {
		this.world = world;
	}

	public Boolean getVisible() {
		return visible;
	}

	public void setVisible(Boolean visible) {
		this.visible = visible;
	}

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public Date getStartdate() {
		return startdate;
	}

	public void setStartdate(Date startdate) {
		this.startdate = startdate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getGoldLoss() {
		return goldLoss;
	}

	public void setGoldLoss(Long goldLoss) {
		this.goldLoss = goldLoss;
	}

	public Long getXpLoss() {
		return xpLoss;
	}

	public void setXpLoss(Long xpLoss) {
		this.xpLoss = xpLoss;
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

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}

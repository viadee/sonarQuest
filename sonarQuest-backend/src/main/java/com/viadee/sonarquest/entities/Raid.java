package com.viadee.sonarquest.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.viadee.sonarquest.constants.RaidState;
import com.viadee.sonarquest.interfaces.Reward;

/**
 * 
 * A raid consists of quests (with amount of tasks) that have to be solved.
 * If the raid was successful, then a reward (= gold/xp) will paid.
 * 
 * @see BaseRaid
 */
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "raid")
public class Raid extends BaseRaid implements Reward {

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private RaidState status;

	@Column(name = "enddate")
	private Date enddate;
	
	@OneToMany(mappedBy = "raid", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Quest> quests;

	@OneToMany(mappedBy = "raid", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<RaidLeaderboard> raidLeaderboadList;
	
	public Raid() {
		this("", "", "", 0l, 0l, null);
	}

	public Raid(String title, String monsterName, String monsterImage, Long gold, Long xp, World world) {
		super(title, monsterName, monsterImage, gold, xp, world);
		this.status = RaidState.OPEN;
	}

	public RaidState getStatus() {
		return status;
	}

	public void setStatus(final RaidState status) {
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

	public Date getEnddate() {
		return enddate;
	}

	public void setEnddate(Date enddate) {
		this.enddate = enddate;
	}

	public List<RaidLeaderboard> getRaidLeaderboadList() {
		if(raidLeaderboadList==null)
			raidLeaderboadList = new ArrayList<RaidLeaderboard>();
		return raidLeaderboadList;
	}

	public void setRaidLeaderboadList(List<RaidLeaderboard> raidLeaderboadList) {
		this.raidLeaderboadList = raidLeaderboadList;
	}
}

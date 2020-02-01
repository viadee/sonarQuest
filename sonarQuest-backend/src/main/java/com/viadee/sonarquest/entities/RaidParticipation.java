package com.viadee.sonarquest.entities;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 *	Expand participation with raid
 *
 */
@Entity
@DiscriminatorValue("RAID")
public class RaidParticipation extends Participation {
	
	@ManyToOne
	@JoinColumn(name = "raid_id")
	private Raid raid;

	public Raid getRaid() {
		return raid;
	}

	public void setRaid(Raid raid) {
		this.raid = raid;
	}
}

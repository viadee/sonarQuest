package com.viadee.sonarquest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.entities.RaidLeaderboard;
import com.viadee.sonarquest.entities.User;

public interface RaidLeaderboardRepository extends CrudRepository<RaidLeaderboard, Long> {
	
	RaidLeaderboard findTopByRaidAndUser(Raid raid, User user);
	List<RaidLeaderboard> findByRaidIdOrderByScoreXp(Long raid_id);
}


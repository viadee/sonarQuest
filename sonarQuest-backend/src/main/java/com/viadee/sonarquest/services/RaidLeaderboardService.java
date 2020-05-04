package com.viadee.sonarquest.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.entities.RaidLeaderboard;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.RaidLeaderboardRepository;

@Service
public class RaidLeaderboardService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RaidLeaderboardService.class);

	@Autowired
	private RaidLeaderboardRepository raidLeaderboardRepository;
	
	/**
	 * Update raid leader board with task reward
	 * @param task
	 */
	public void updateLeaderboard(final Task task) {
		if (task == null || task.getParticipation() == null)
			return;

		final Participation participation = task.getParticipation();
		final User user = participation.getUser();
		final Quest quest = task.getQuest();
		final Raid raid = quest.getRaid();

		if (raid == null) // Task is not in raid!
			return;

		updateLeaderboardScore(user, raid, task.getGold(), task.getXp());
	}

	/**
	 * Update raid leader board with quest reward
	 * @param quest
	 */
	public void updateLeaderboard(final Quest quest) {
		if (quest == null)
			return;

		final List<Participation> participations = quest.getParticipations();
		for (Participation participation : participations) {
			final User user = participation.getUser();
			final Raid raid = quest.getRaid();
			if (raid == null) // Quest is not in raid!
				return;
			
			updateLeaderboardScore(user, raid, quest.getGold(), quest.getXp());
		}
	}
	
	/**
	 * Create new or update RaidLeaderboard
	 * Adding score points (= Gold, XP)
	 * 
	 * @param user
	 * @param raid
	 * @param reward (= Gold, XP)
	 * @return
	 */
	@Transactional
	public RaidLeaderboard updateLeaderboardScore(final User user, final Raid raid, Long gold, Long xp) {
		RaidLeaderboard board = raidLeaderboardRepository.findTopByRaidAndUser(raid, user);
		if (board == null)
			board = new RaidLeaderboard(user, raid);

		board.setScoreDay(Date.valueOf(LocalDate.now()));
		board.addScoreGold(gold);
		board.addScoreXp(xp);
		
		LOGGER.info("Update LeaderboardScore - userID {} with {} gold and {} xp", user.getId(), gold, xp);
		
		return raidLeaderboardRepository.save(board);
	}
}

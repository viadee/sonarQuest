package com.viadee.sonarquest.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.entities.RaidLeaderboard;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.RaidLeaderboardRepository;

// TODO ADD TEST
@Service
public class RaidLeaderboardService {
	private static final Logger LOGGER = LoggerFactory.getLogger(RaidLeaderboardService.class);

	@Autowired
	private RaidLeaderboardRepository raidLeaderboardRepository;
	
	public List<RaidLeaderboard> findRaidLeaderboards(Raid raid) {
		return raidLeaderboardRepository.findByRaidIdOrderByScoreSolvedTasks(raid.getId());
	}
	
	public void updateLeaderboard(Quest quest) {
		quest.getTasks().forEach(task -> updateLeaderboard(task));
	}

	/**
	 * Update raid leaderboard by task:
	 * 
	 * Adding score points (= Gold, XP and SolvedTasks)
	 * 
	 * @param task
	 */
	public void updateLeaderboard(Task task) {
		final Participation participation = task.getParticipation();
		if (participation != null) {
			final User user = participation.getUser();
			final Quest quest = task.getQuest();
			final Raid raid = quest.getRaid();

			if (raid == null) // Task is not in raid!
				return;

			RaidLeaderboard board = raidLeaderboardRepository.findTopByRaidAndUser(raid, user);
			if (board == null) 
				board = new RaidLeaderboard(user, raid);
			
			board.setScoreDay(Date.valueOf(LocalDate.now()));
			
			board.addScoreSolvedTasks(1l);
			board.addScoreGold(task.getGold());
			board.addScoreXp(task.getXp());
			
			LOGGER.info("Task {} solved - Update Leaderboard: userID {} with {} gold and {} xp", task.getKey(),
					user.getId(), task.getGold(), task.getXp());
			
			raidLeaderboardRepository.save(board);
		} else {
			
			LOGGER.info("No SQUser participations found for task {}, so no highscore to update", task.getKey());
		}
	}
}

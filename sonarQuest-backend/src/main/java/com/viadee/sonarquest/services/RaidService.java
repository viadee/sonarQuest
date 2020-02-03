package com.viadee.sonarquest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarquest.dto.ProgressDTO;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.exception.BackendServiceRuntimeException;
import com.viadee.sonarquest.repositories.RaidRepository;

@Service
public class RaidService {
	
	@Autowired
    private RaidRepository raidRepository;
	
	@Autowired
	private QuestService questService;
	
	@Autowired
	private WorldService worldService;
	
	@Transactional
	public synchronized Raid saveRaid(final Raid raid) {
		return raidRepository.save(raid);
	}
		
	public Raid findRaidById(Long raidId) {
		Raid raid = raidRepository.findOne(raidId);
		return raid;
	}
	
	public List<Raid> findAllRaids() {
		return raidRepository.findAll();
	}
	
	public List<Raid> findAllRaidsFromWorld(Long worldId) {
		World world =  worldService.findById(worldId);
		return raidRepository.findByWorld(world);
	}
	
	@Transactional
	public synchronized Raid createRaid(final Raid raid) {
		World worldDAO = worldService.findById(raid.getWorld().getId());
		if(worldDAO==null) {
			new BackendServiceRuntimeException("World not found", new NullPointerException());
		}
		
		Raid raidDAO = saveRaid(new Raid(raid.getTitle(), raid.getMonsterName(), raid.getMonsterImage(), raid.getGold(), raid.getXp(), worldDAO));
		for (Quest quest : raid.getQuests()) {
			Quest questDao = addRaidToQuest(raidDAO.getId(), quest.getId());
			raidDAO.addQuest(questDao);
		}
		return raidDAO;
	}

	/**
	 * Add raid to existing quest
	 * @param raidId to find raid
	 * @param questId to find quest
	 * @return
	 */
	@Transactional
    public synchronized Quest addRaidToQuest(Long raidId, Long questId) {
    	Raid raidDAO = raidRepository.findOne(raidId);
    	Quest questDAO = questService.findById(questId);
		questDAO.setRaid(raidDAO);
		return questService.saveQuest(questDAO);
    }
	
    /**
     * TODO Maybe to refactor and to expand!
     * Calculate raid progress from open tasks in quests
     * 
     * @param raid
     * @return rounded progress solved value
     */
	public ProgressDTO calculateRaidProgress(Raid raid) {
		long openTasks = 0;
		int taskSize = 0;
	
		for (Quest quest : raid.getQuests()) {
			ProgressDTO progressQuest = questService.calculateQuestProgress(quest);
			taskSize += progressQuest.getTotalAmount();
			openTasks += progressQuest.getNumberOfVariable();
		}
		
		double raidProgress = Math.round((100*(double)openTasks/taskSize));
		return new ProgressDTO(taskSize, openTasks, raidProgress);
	}
}

package com.viadee.sonarquest.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarquest.dto.ProgressDTO;
import com.viadee.sonarquest.dto.SolvedTaskHistoryDTO;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.entities.Task;
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
	
	@Autowired
	private SolvedTaskHistoryService solvedTaskHistoryService;
	
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
	
	@Transactional
	public synchronized Raid updateRaid(final Raid raid) {
		Raid raidDAO = findRaidById(raid.getId());
		
		if(raidDAO==null) {
			new BackendServiceRuntimeException("Raid not found", new NullPointerException());
		}
		raidDAO.updateBaseRaid(raid);

		return saveRaid(raidDAO);
	}
	
	public void deleteRaid(final long raidId) {
		raidRepository.delete(raidId);
	}
	
	@Transactional
    public synchronized Quest addRaidToQuest(Long raidId, Long questId) {
    	Raid raidDAO = raidRepository.findOne(raidId);
    	if(raidDAO != null) {
    		Quest questDAO = questService.findById(questId);
    		questDAO.setRaid(raidDAO);
    		return questService.saveQuest(questDAO);
    	}
    	return null;
	}
	
    /**
     * Calculate raid progress from open tasks in quests
     * 
     * @param raid Raid
     * @return ProgressDTO (with rounded progress)
     */
	public ProgressDTO calculateRaidProgress(Raid raid) {
		long openTasks = 0;
		int taskSize = 0;
	
		for (Quest quest : raid.getQuests()) {
			ProgressDTO progressQuest = questService.calculateQuestProgress(quest);
			taskSize  += progressQuest.getTotalAmount();
			openTasks += progressQuest.getNumberOfVariable();
		}
		
		double raidProgress = Math.round((100*(double)openTasks/taskSize));
		return new ProgressDTO(taskSize, openTasks, raidProgress);
	}
	
	public List<SolvedTaskHistoryDTO> getSolvedTaskHistoryList(final Long raidId){
		Raid raid = raidRepository.findOne(raidId);
		if(raid==null)
			new BackendServiceRuntimeException("Could not calculate finishedTaskHistory!", new NullPointerException());
		
		List<Task> tasks = new ArrayList<Task>();
		raid.getQuests().stream().forEach(quest -> {
			tasks.addAll(quest.getTasks());
		});
		
		return solvedTaskHistoryService.getSolvedTaskHistory(tasks);
	}
}

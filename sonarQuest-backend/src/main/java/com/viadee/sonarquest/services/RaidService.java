package com.viadee.sonarquest.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.viadee.sonarquest.dto.ProgressDTO;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.exception.BackendServiceRuntimeException;
import com.viadee.sonarquest.repositories.RaidRepository;
import com.viadee.sonarquest.rules.SonarQuestStatus;

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
	
	/**
	 * Calculate for each day the amount of finished tasks.
	 * 
	 * @param from analyze date
	 * @param to analyze date
	 * @param raid with quests (= amount of tasks)
	 * @return map <date and amount of finished tasks>
	 */
	public Map<Date, Long> calculateFinishedTaskHistoryFromRaid(final LocalDate from, final LocalDate to, final Raid raid) {
		if( from == null || to == null || raid==null)
			new BackendServiceRuntimeException("Could not calculate finishedTaskHistory!", new NullPointerException());
		
		Map<Date, Long> history = new TreeMap<Date, Long>();
		
		from.datesUntil(to.plusDays(1)).forEach(date -> {
			history.put(Date.valueOf(date), 0l);
		});
		
		raid.getQuests().forEach(quest -> {
			quest.getTasks().stream().filter(t -> !SonarQuestStatus.OPEN.equals(t.getStatus())).forEach(t-> {
				history.merge(t.getEnddate(), 1l, (v1, v2) -> v1 + v2);
			});
		});
		return history;
	}
}

package com.viadee.sonarquest.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarquest.entities.StandardTask;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.externalressources.SonarQubeSeverity;
import com.viadee.sonarquest.repositories.StandardTaskRepository;
import com.viadee.sonarquest.repositories.WorldRepository;
import com.viadee.sonarquest.rules.SonarQuestStatus;

@Service
public class StandardTaskService {

    @Autowired
    private ExternalRessourceService externalRessourceService;

    @Autowired
    private StandardTaskRepository standardTaskRepository;

    @Autowired
    private QuestService questService;

    @Autowired
    private AdventureService adventureService;
    
    @Autowired
	private RaidService raidService;

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private GratificationService gratificationService;

    @Autowired
    private NamedParameterJdbcTemplate template;
    


    @Transactional
    public void updateStandardTasks(final World world) {
        final List<StandardTask> externalStandardTasks = externalRessourceService
                .generateStandardTasksFromSonarQubeIssuesForWorld(world);
        externalStandardTasks.forEach(this::updateStandardTask);
        questService.updateQuests();
        adventureService.updateAdventures();
        raidService.updateRaids();
    }

    @Transactional
    public StandardTask updateStandardTask(final StandardTask task) {
        final SonarQuestStatus oldStatus = getLastState(task);
        final SonarQuestStatus newStatus = task.getStatus();
        if (newStatus == SonarQuestStatus.SOLVED && oldStatus == SonarQuestStatus.OPEN) {
            gratificationService.rewardUserForSolvingTask(task);
            task.setEnddate(Date.valueOf(LocalDate.now()));
        }
        task.setStatus(newStatus);
        return standardTaskRepository.saveAndFlush(task);
    }

    protected SonarQuestStatus getLastState(final StandardTask task) {
        final SqlParameterSource params = new MapSqlParameterSource().addValue("id", task.getId());
        final String sql = "SELECT Status FROM Task WHERE id = :id";
        final RowMapper<String> rowmapper = new SingleColumnRowMapper<>();
        final List<String> statusTexte = template.query(sql, params, rowmapper);
        final String statusText = statusTexte.isEmpty() ? null : statusTexte.get(0);
        return SonarQuestStatus.fromStatusText(statusText);
    }

    public void setExternalRessourceService(final ExternalRessourceService externalRessourceService) {
        this.externalRessourceService = externalRessourceService;
    }

    @Transactional
    public void save(final StandardTask standardTask) {
        final World world = worldRepository.findByProject(standardTask.getWorld().getProject());
        final StandardTask st = new StandardTask(
                standardTask.getTitle(),
                SonarQuestStatus.OPEN,
                standardTask.getGold(),
                standardTask.getXp(),
                standardTask.getQuest(),
                world, null, null, null, null, null, null,null);
        standardTaskRepository.save(st);
    }

    public List<StandardTask> findAll() {
        return standardTaskRepository.findAll();
    }
    
    public List<StandardTask> getFreeStandardTasksForWorld(final World w, final SonarQuestStatus status) {
    	List<StandardTask> tasks = standardTaskRepository.findByWorldAndStatusAndQuestIsNull(w, status);
    	Collections.sort(tasks, standardTaskComparator());
    	return tasks;
    }

    public List<StandardTask> findByWorld(final World w) {
		List<StandardTask> tasks = standardTaskRepository.findByWorld(w);
		Collections.sort(tasks, standardTaskComparator());
		return tasks;
    }
    
    private Comparator<StandardTask> standardTaskComparator(){
    	Comparator<StandardTask> comparator = new Comparator<StandardTask>() {

			@Override
			public int compare(StandardTask task1, StandardTask task2) {
				SonarQubeSeverity severity1 = SonarQubeSeverity.fromString(task1.getSeverity());
				SonarQubeSeverity severity2 = SonarQubeSeverity.fromString(task2.getSeverity());
				return severity2.getRank().compareTo(severity1.getRank());
			}
		};
		return comparator;
    }

}

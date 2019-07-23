package com.viadee.sonarquest.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.viadee.sonarquest.dto.StandardTaskDTO;
import com.viadee.sonarquest.entities.StandardTask;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.externalressources.SonarQubeSeverity;
import com.viadee.sonarquest.repositories.StandardTaskRepository;
import com.viadee.sonarquest.repositories.WorldRepository;
import com.viadee.sonarquest.rules.SonarQuestStatus;
import com.viadee.sonarquest.skillTree.services.UserSkillService;
import com.viadee.sonarquest.utils.mapper.StandardTaskDtoEntityMapper;

@Service
public class StandardTaskService {
	private static final Logger LOGGER = LoggerFactory.getLogger(StandardTaskService.class);

	@Autowired
	private ExternalRessourceService externalRessourceService;

	@Autowired
	private StandardTaskRepository standardTaskRepository;

	@Autowired
	private QuestService questService;

	@Autowired
	private AdventureService adventureService;

	@Autowired
	private WorldRepository worldRepository;

	@Autowired
	private GratificationService gratificationService;

	@Autowired
	private UserSkillService userSkillService;

	@Autowired
	private NamedParameterJdbcTemplate template;

	@Autowired
	private StandardTaskDtoEntityMapper standardTaskMapper;

	@Transactional
	public void updateStandardTasks(final World world) {
		final List<StandardTask> externalStandardTasks = externalRessourceService
				.generateStandardTasksFromSonarQubeIssuesForWorld(world);
		externalStandardTasks.forEach(this::updateStandardTask);
		questService.updateQuests();
		adventureService.updateAdventures();
	}

	@Transactional
	public StandardTask updateStandardTask(final StandardTask task) {
		final SonarQuestStatus oldStatus = getLastState(task);
		final SonarQuestStatus newStatus = task.getStatus();
		if (newStatus == SonarQuestStatus.SOLVED && oldStatus == SonarQuestStatus.OPEN) {
			gratificationService.rewardUserForSolvingTask(task);
			userSkillService.learnUserSkillFromTask(task);
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
		final StandardTask st = new StandardTask(standardTask.getTitle(), SonarQuestStatus.OPEN, standardTask.getGold(),
				standardTask.getXp(), standardTask.getQuest(), world, null, null, null, null, null, null, null);
		standardTaskRepository.save(st);
	}

	public List<StandardTask> findAll() {
		return standardTaskRepository.findAll();
	}

	@Cacheable(value = "taskScoringCache", key = "#w.id")
	public List<StandardTaskDTO> findByWorld(final World world) {
		List<StandardTask> tasks = standardTaskRepository.findByWorld(world);
		List<String> teamMails = new ArrayList<String>();
		List<StandardTaskDTO> standardTaskDtos = new ArrayList<StandardTaskDTO>();
		if (!tasks.isEmpty() || tasks != null) {
			standardTaskDtos = tasks.stream().map(standardTaskMapper::enitityToDto).collect(Collectors.toList());

			if (world.getUsers() != null) {
				teamMails = world.getUsers().stream().filter(user -> user.getMail() != null).map(user -> user.getMail())
						.collect(Collectors.toList());
				if (tasks != null) {
					Map<String, Double> scores = calculateScoringForTasksFromTeam(tasks, teamMails);
					for (StandardTaskDTO standardTaskDto : standardTaskDtos) {
						standardTaskDto.setUserSkillScoring(scores.get(standardTaskDto.getIssueRule()));
					}
				}
			}

			Collections.sort(standardTaskDtos, new Comparator<StandardTaskDTO>() {

				@Override
				public int compare(StandardTaskDTO task1, StandardTaskDTO task2) {
					SonarQubeSeverity severity1 = SonarQubeSeverity.fromString(task1.getSeverity());
					SonarQubeSeverity severity2 = SonarQubeSeverity.fromString(task2.getSeverity());
					return severity2.getRank().compareTo(severity1.getRank());
				}
			});
		}
		return standardTaskDtos;
	}

	@CachePut(value = "taskScoringCache", key = "#w.id")
	public List<StandardTaskDTO> updateFindByWorldCache(final World w) {
		LOGGER.info("Cache 'taskScoringCache' for world '{}' has been updated", w.getName());
		return findByWorld(w);
	}

	private Map<String, Double> calculateScoringForTasksFromTeam(List<StandardTask> tasks, List<String> teamMails) {
		List<String> ruleKeys = tasks.stream().filter(distinctByKey(StandardTask::getIssueRule))
				.map(StandardTask::getIssueRule).collect(Collectors.toList());
		Map<String, Double> scores = new HashMap<String, Double>();
		for (String key : ruleKeys) {
			scores.put(key, userSkillService.getScoringForRuleFromTeam(key, teamMails));
		}
		return scores;
	}

	private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
		Set<Object> seen = ConcurrentHashMap.newKeySet();
		return t -> seen.add(keyExtractor.apply(t));
	}
}

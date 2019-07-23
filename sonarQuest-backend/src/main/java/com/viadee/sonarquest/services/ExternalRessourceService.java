package com.viadee.sonarquest.services;

import java.net.ConnectException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.viadee.sonarquest.entities.SonarConfig;
import com.viadee.sonarquest.entities.StandardTask;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.externalressources.SonarQubeApiCall;
import com.viadee.sonarquest.externalressources.SonarQubeComponentQualifier;
import com.viadee.sonarquest.externalressources.SonarQubeIssue;
import com.viadee.sonarquest.externalressources.SonarQubeIssueRessource;
import com.viadee.sonarquest.externalressources.SonarQubeIssueType;
import com.viadee.sonarquest.externalressources.SonarQubePaging;
import com.viadee.sonarquest.externalressources.SonarQubeProject;
import com.viadee.sonarquest.externalressources.SonarQubeProjectRessource;
import com.viadee.sonarquest.externalressources.SonarQubeRule;
import com.viadee.sonarquest.externalressources.SonarQubeRuleRessource;
import com.viadee.sonarquest.externalressources.SonarQubeSeverity;
import com.viadee.sonarquest.repositories.StandardTaskRepository;
import com.viadee.sonarquest.rules.SonarQubeStatusMapper;
import com.viadee.sonarquest.rules.SonarQuestStatus;
import com.viadee.sonarquest.skillTree.entities.SonarRule;
import com.viadee.sonarquest.skillTree.services.SonarRuleService;
import com.viadee.sonarquest.skillTree.services.UserSkillService;

/**
 * Service to access SonarQube server.
 */
@Service
public class ExternalRessourceService {

	@Autowired
	private StandardTaskEvaluationService standardTaskEvaluationService;

	@Autowired
	private SonarQubeStatusMapper statusMapper;

	@Autowired
	private StandardTaskRepository standardTaskRepository;

	@Autowired
	private SonarConfigService sonarConfigService;

	@Autowired
	private RestTemplateService restTemplateService;

	@Autowired
	private GratificationService gratificationService;

	@Autowired
	private SonarRuleService sonarRuleService;

	@Autowired
	private UserSkillService userSkillService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalRessourceService.class);

	private static final String ERROR_NO_CONNECTION = "No connection to backend - please adjust the url to the SonarQube server";

	@Value("${issue.processing.batchsize:500}")
	private int issueProcessingBatchSize;

	@Value("${max.number.of.issues.on.page:500}")
	private int maxNumberOfIssuesOnPage;

	@Value("${max.number.of.rules.on.page:500}")
	private int maxNumberOfRulesOnPage;

	@Value("#{'${issue.severities}'.split(',')}")
	private List<SonarQubeSeverity> issueSeverities;

	public List<World> generateWorldsFromSonarQubeProjects() {
		return getSonarQubeProjects().stream().map(this::toWorld).collect(Collectors.toList());
	}

	private World toWorld(final SonarQubeProject sonarQubeProject) {
		return new World(sonarQubeProject.getName(), sonarQubeProject.getKey(), false, true);
	}

	public List<StandardTask> generateStandardTasksFromSonarQubeIssuesForWorld(final World world) {
		final List<StandardTask> standardTasks;
		final List<SonarQubeIssue> sonarQubeIssues = getIssuesForSonarQubeProject(world.getProject());
		int numberOfIssues = sonarQubeIssues.size();
		LOGGER.info("Mapping {} issues to SonarQuest tasks - this may take a while...", numberOfIssues);
		if (numberOfIssues > issueProcessingBatchSize) {
			standardTasks = new ArrayList<>();
			List<List<SonarQubeIssue>> partitions = ListUtils.partition(sonarQubeIssues, issueProcessingBatchSize);
			LOGGER.info("Processing {} partitions with a size of {} issues each", partitions.size(),
					issueProcessingBatchSize);
			int partitionNumber = 1;
			for (List<SonarQubeIssue> partition : partitions) {
				LOGGER.info("Mapping partition number: {}", partitionNumber++);
				List<StandardTask> tasksForThisPartition = partition.stream()
						.map(sonarQubeIssue -> toTask(sonarQubeIssue, world)).collect(Collectors.toList());
				standardTasks.addAll(tasksForThisPartition);
			}
		} else {
			standardTasks = sonarQubeIssues.stream().map(sonarQubeIssue -> toTask(sonarQubeIssue, world))
					.collect(Collectors.toList());
		}
		LOGGER.info("Mapping done.");
		return standardTasks;
	}

	private StandardTask toTask(final SonarQubeIssue sonarQubeIssue, final World world) {
		final Long gold = standardTaskEvaluationService.evaluateGoldAmount(sonarQubeIssue.getDebt());
		final Long xp = standardTaskEvaluationService.evaluateXP(sonarQubeIssue.getSeverity());
		final Integer debt = Math.toIntExact(standardTaskEvaluationService.getDebt(sonarQubeIssue.getDebt()));
		final SonarQuestStatus status = statusMapper.mapExternalStatus(sonarQubeIssue);
		return loadTask(sonarQubeIssue, world, gold, xp, debt, status);
	}

	private StandardTask loadTask(final SonarQubeIssue sonarQubeIssue, final World world, final Long gold,
			final Long xp, final Integer debt, final SonarQuestStatus newStatus) {
		StandardTask savedTask = standardTaskRepository.findByKey(sonarQubeIssue.getKey());
		if (savedTask == null) {
			// new issue from SonarQube: Create new task
			savedTask = new StandardTask(sonarQubeIssue.getMessage(), newStatus, gold, xp, null, world,
					sonarQubeIssue.getKey(), sonarQubeIssue.getComponent(), sonarQubeIssue.getSeverity(),
					sonarQubeIssue.getType(), debt, sonarQubeIssue.getKey(), sonarQubeIssue.getRule());
		} else {
			final SonarQuestStatus lastStatus = savedTask.getStatus();
			if (newStatus == SonarQuestStatus.SOLVED && lastStatus == SonarQuestStatus.OPEN) {
				gratificationService.rewardUserForSolvingTask(savedTask);
				userSkillService.learnUserSkillFromTask(savedTask);
			}
			savedTask.setStatus(newStatus);
		}
		return savedTask;
	}

	public List<SonarQubeProject> getSonarQubeProjects() {
		try {
			final SonarConfig sonarConfig = sonarConfigService.getConfig();
			final List<SonarQubeProject> sonarQubeProjects = new ArrayList<>();
			final SonarQubeProjectRessource sonarQubeProjectRessource = getSonarQubeProjectRessourceForPageIndex(
					sonarConfig, 1);

			sonarQubeProjects.addAll(sonarQubeProjectRessource.getSonarQubeProjects());

			final Integer pagesOfExternalProjects = determinePagesOfExternalRessourcesToBeRequested(
					sonarQubeProjectRessource.getPaging());
			for (int i = 2; i <= pagesOfExternalProjects; i++) {
				sonarQubeProjects
						.addAll(getSonarQubeProjectRessourceForPageIndex(sonarConfig, i).getSonarQubeProjects());
			}
			return sonarQubeProjects;
		} catch (final Exception e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}

	private List<SonarQubeIssue> getIssuesForSonarQubeProject(final String projectKey) {
		try {
			LOGGER.info("Trying to get SonarQube issues with severities {} for projectKey {}", issueSeverities,
					projectKey);
			final SonarConfig sonarConfig = sonarConfigService.getConfig();
			final RestTemplate restTemplate = restTemplateService.getRestTemplate(sonarConfig);
			final List<SonarQubeIssue> sonarQubeIssueList = new ArrayList<>();
			SonarQubeIssueRessource sonarQubeIssueRessource = getSonarQubeIssuesWithDefaultSeverities(restTemplate,
					sonarConfig.getSonarServerUrl(), projectKey);
			sonarQubeIssueList.addAll(sonarQubeIssueRessource.getIssues());
			LOGGER.info("Retrieved {} SonarQube issues in total for projectKey {}", sonarQubeIssueList.size(),
					projectKey);
			return sonarQubeIssueList;
		} catch (final ResourceAccessException e) {
			if (e.getCause() instanceof ConnectException) {
				LOGGER.error(ERROR_NO_CONNECTION, e);
			}
			throw e;
		}
	}

	private int determinePagesOfExternalRessourcesToBeRequested(final SonarQubePaging sonarQubePaging) {
		return sonarQubePaging.getTotal() / sonarQubePaging.getPageSize() + 1;
	}

	private SonarQubeIssueRessource getSonarQubeIssuesWithDefaultSeverities(final RestTemplate restTemplate,
			final String sonarQubeServerUrl, final String projectKey) {
		// @formatter: off
		SonarQubeApiCall sonarQubeApiCall = SonarQubeApiCall.onServer(sonarQubeServerUrl).searchIssues()
				.withComponentKeys(projectKey).withTypes(SonarQubeIssueType.CODE_SMELL).withSeverities(issueSeverities)
				.pageSize(maxNumberOfIssuesOnPage).pageIndex(1).build();
		// @formatter: on
		final ResponseEntity<SonarQubeIssueRessource> response = restTemplate.getForEntity(sonarQubeApiCall.asString(),
				SonarQubeIssueRessource.class);
		return response.getBody();
	}

	private SonarQubeProjectRessource getSonarQubeProjectRessourceForPageIndex(final SonarConfig sonarConfig,
			final int pageIndex) {
		final RestTemplate restTemplate = restTemplateService.getRestTemplate(sonarConfig);
		// @formatter: off
		SonarQubeApiCall sonarQubeApiCall = SonarQubeApiCall.onServer(sonarConfig.getSonarServerUrl())
				.searchComponents(SonarQubeComponentQualifier.TRK).pageSize(maxNumberOfIssuesOnPage)
				.pageIndex(pageIndex).build();
		// @formatter: on
		final ResponseEntity<SonarQubeProjectRessource> response = restTemplate
				.getForEntity(sonarQubeApiCall.asString(), SonarQubeProjectRessource.class);
		return response.getBody();
	}

	private SonarQubeRuleRessource getSonarQubeRulesByLanguage(final SonarConfig sonarConfig, final String language) {
		final RestTemplate restTemplate = restTemplateService.getRestTemplate(sonarConfig);
		SonarQubeApiCall sonarQubeApiCall = SonarQubeApiCall.onServer(sonarConfig.getSonarServerUrl()).searchRules()
				.language(language).pageSize(maxNumberOfRulesOnPage).sort("createdAt").build();

		ResponseEntity<SonarQubeRuleRessource> response = restTemplate.getForEntity(sonarQubeApiCall.asString(),
				SonarQubeRuleRessource.class);

		if (response.getBody().getRules().size() == maxNumberOfRulesOnPage) {
			Timestamp timeStamp = response.getBody().getRules().get(response.getBody().getRules().size() - 1)
					.getCreatedAt();
			Date date = new Date();
			date.setTime(timeStamp.getTime());
			String formattedDate = new SimpleDateFormat("yyyy-MM-dd").format(date);

			for (SonarQubeRule rule : getSonarQubeRulesByLanguageAndCreatedSince(sonarConfig, language, formattedDate)
					.getRules()) {
				response.getBody().addRuleIfNotExists(rule);
			}
		}
		return response.getBody();
	}

	private SonarQubeRuleRessource getSonarQubeRulesByLanguageAndCreatedSince(SonarConfig sonarConfig, String language,
			String lastRuleUpdate) {
		final RestTemplate restTemplate = restTemplateService.getRestTemplate(sonarConfig);
		SonarQubeApiCall sonarQubeApiCall = SonarQubeApiCall.onServer(sonarConfig.getSonarServerUrl()).searchRules()
				.language(language).pageSize(maxNumberOfRulesOnPage).availableSince(lastRuleUpdate).sort("createdAt")
				.build();
		ResponseEntity<SonarQubeRuleRessource> response = restTemplate.getForEntity(sonarQubeApiCall.asString(),
				SonarQubeRuleRessource.class);
		return response.getBody();
	}

	private List<SonarQubeRule> getSonarQubeRules(final String language) {

		try {
			final SonarConfig sonarConfig = sonarConfigService.getConfig();
			final List<SonarQubeRule> sonarQubeRules = new ArrayList<>();
			SonarQubeRuleRessource sonarQubeRuleRessource;
			List<SonarRule> currentRules = sonarRuleService.findAll();
			LOGGER.info("Trying to get SonarQube rules with language {} ", language);
			sonarQubeRuleRessource = getSonarQubeRulesByLanguage(sonarConfig, language);
			if (currentRules.isEmpty() || currentRules == null) {
				LOGGER.info("Trying to get SonarQube rules with language {} ", language);
				sonarQubeRuleRessource = getSonarQubeRulesByLanguage(sonarConfig, language);
			} else {
				LOGGER.info("Trying to update SonarQube rules with language {} ", language);
				sonarQubeRuleRessource = getSonarQubeRulesByLanguageAndCreatedSince(sonarConfig, language,
						sonarRuleService.getLastAddedDate());
			}

			sonarQubeRules.addAll(sonarQubeRuleRessource.getRules());
			LOGGER.info("Retrieved {} SonarQube rules in total for language {}", sonarQubeRules.size(), language);
			return sonarQubeRules;

		} catch (final ResourceAccessException e) {
			if (e.getCause() instanceof ConnectException) {
				LOGGER.error(ERROR_NO_CONNECTION, e);
			}
			throw e;
		}
	}

	public List<SonarRule> generateSonarRulesByLanguage(final String language) {
		return getSonarQubeRules(language).stream().map(this::toSonarRule).collect(Collectors.toList());
	}

	private SonarRule toSonarRule(final SonarQubeRule sonarQubeRule) {
		SonarRule sonarRule = new SonarRule();
		sonarRule.setKey(sonarQubeRule.getKey());
		sonarRule.setName(sonarQubeRule.getName());
		sonarRule.setAddedAt(Timestamp.valueOf(LocalDateTime.now()));
		return sonarRule;
	}

}

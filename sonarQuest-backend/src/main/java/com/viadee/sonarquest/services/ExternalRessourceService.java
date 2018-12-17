package com.viadee.sonarquest.services;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.viadee.sonarquest.externalressources.SonarQubeSeverity;
import com.viadee.sonarquest.repositories.StandardTaskRepository;
import com.viadee.sonarquest.rules.SonarQubeStatusMapper;
import com.viadee.sonarquest.rules.SonarQuestStatus;

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

	private static final Logger LOGGER = LoggerFactory.getLogger(ExternalRessourceService.class);

	private static final String ERROR_NO_CONNECTION = "No connection to backend - please adjust the url to the SonarQube server";

	private static final int ISSUE_PROCESSING_BATCH_SIZE = 500;

	private static final int MAX_NUMBER_OF_ISSUES_ON_PAGE = 500;

	private static final List<SonarQubeSeverity> DEFAULT_ISSUE_SEVERITIES = Arrays.asList(SonarQubeSeverity.BLOCKER,
			SonarQubeSeverity.CRITICAL, SonarQubeSeverity.MAJOR);

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
		LOGGER.info("Mapping " + numberOfIssues + " issues to SonarQuest tasks - this may take a while...");
		if (numberOfIssues > ISSUE_PROCESSING_BATCH_SIZE) {
			standardTasks = new ArrayList<>();
			List<List<SonarQubeIssue>> partitions = ListUtils.partition(sonarQubeIssues, ISSUE_PROCESSING_BATCH_SIZE);
			LOGGER.info("Processing " + partitions.size() + " partitions with a size of " + ISSUE_PROCESSING_BATCH_SIZE
					+ " issues each");
			int partitionNumber = 1;
			for (List<SonarQubeIssue> partition : partitions) {
				LOGGER.info("Mapping partition number: " + partitionNumber++);
				List<StandardTask> tasksForThisPartition = partition.stream()
						.map(sonarQubeIssue -> toTask(sonarQubeIssue, world)).collect(Collectors.toList());
				standardTasks.addAll(tasksForThisPartition);
			}
		} else {
			standardTasks = sonarQubeIssues.stream().map(sonarQubeIssue -> toTask(sonarQubeIssue, world))
					.collect(Collectors.toList());
		}
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
			final Long xp, final Integer debt, final SonarQuestStatus status) {
		StandardTask sonarQubeTask = standardTaskRepository.findByKey(sonarQubeIssue.getKey());
		if (sonarQubeTask == null) {
			// new issue from SonarQube: Create new task
			sonarQubeTask = new StandardTask(sonarQubeIssue.getMessage(), status, gold, xp, null, world,
					sonarQubeIssue.getKey(), sonarQubeIssue.getComponent(), sonarQubeIssue.getSeverity(),
					sonarQubeIssue.getType(), debt, sonarQubeIssue.getKey());
		} else {
			// issue already in SonarQuest database: update the task
			sonarQubeTask.setStatus(status);
		}
		return sonarQubeTask;
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
			LOGGER.info(String.format("Trying to get SonarQube issues with severities %s for projectKey %s",
					DEFAULT_ISSUE_SEVERITIES, projectKey));
			final SonarConfig sonarConfig = sonarConfigService.getConfig();
			final RestTemplate restTemplate = restTemplateService.getRestTemplate(sonarConfig);
			final List<SonarQubeIssue> sonarQubeIssueList = new ArrayList<>();
			SonarQubeIssueRessource sonarQubeIssueRessource = getSonarQubeIssuesWithDefaultSeverities(restTemplate,
					sonarConfig.getSonarServerUrl(), projectKey);
			sonarQubeIssueList.addAll(sonarQubeIssueRessource.getIssues());

			if (sonarQubeIssueList.size() < MAX_NUMBER_OF_ISSUES_ON_PAGE) {
				LOGGER.info(String.format(
						"Only %s issues have been found - dropping them and loading issues with ALL severities instead",
						sonarQubeIssueList.size()));
				sonarQubeIssueList.clear();
				sonarQubeIssueRessource = getSonarQubeIssuesWithAllSeverities(restTemplate,
						sonarConfig.getSonarServerUrl(), projectKey, 1);
				sonarQubeIssueList.addAll(sonarQubeIssueRessource.getIssues());
				final int pagesOfExternalIssues = determinePagesOfExternalRessourcesToBeRequested(
						sonarQubeIssueRessource.getPaging());
				LOGGER.debug("Found a total of " + pagesOfExternalIssues
						+ " pages of issues on the SonarQube server. Retrieving them pagewise...");
				for (int i = 2; i <= pagesOfExternalIssues; i++) {
					sonarQubeIssueList.addAll(getSonarQubeIssuesWithAllSeverities(restTemplate,
							sonarConfig.getSonarServerUrl(), projectKey, i).getIssues());
				}
			}

			LOGGER.info(String.format("Retrieved %s SonarQube issues in total for projectKey %s",
					sonarQubeIssueList.size(), projectKey));
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

	private SonarQubeIssueRessource getSonarQubeIssuesWithAllSeverities(final RestTemplate restTemplate,
			final String sonarQubeServerUrl, final String projectKey, final int pageIndex) {
		SonarQubeApiCall sonarQubeApiCall = SonarQubeApiCall.onServer(sonarQubeServerUrl).searchIssues()
				.withComponentKeys(projectKey).pageSize(MAX_NUMBER_OF_ISSUES_ON_PAGE).pageIndex(pageIndex).build();
		final ResponseEntity<SonarQubeIssueRessource> response = restTemplate.getForEntity(sonarQubeApiCall.asString(),
				SonarQubeIssueRessource.class);
		return response.getBody();
	}

	private SonarQubeIssueRessource getSonarQubeIssuesWithDefaultSeverities(final RestTemplate restTemplate,
			final String sonarQubeServerUrl, final String projectKey) {
		SonarQubeApiCall sonarQubeApiCall = SonarQubeApiCall.onServer(sonarQubeServerUrl).searchIssues()
				.withComponentKeys(projectKey).withTypes(SonarQubeIssueType.CODE_SMELL)
				.withSeverities(DEFAULT_ISSUE_SEVERITIES).pageSize(MAX_NUMBER_OF_ISSUES_ON_PAGE).pageIndex(1).build();
		final ResponseEntity<SonarQubeIssueRessource> response = restTemplate.getForEntity(sonarQubeApiCall.asString(),
				SonarQubeIssueRessource.class);
		return response.getBody();
	}

	private SonarQubeProjectRessource getSonarQubeProjectRessourceForPageIndex(final SonarConfig sonarConfig,
			final int pageIndex) {
		final RestTemplate restTemplate = restTemplateService.getRestTemplate(sonarConfig);
		SonarQubeApiCall sonarQubeApiCall = SonarQubeApiCall.onServer(sonarConfig.getSonarServerUrl())
				.searchComponents(SonarQubeComponentQualifier.TRK).pageSize(MAX_NUMBER_OF_ISSUES_ON_PAGE)
				.pageIndex(pageIndex).build();
		final ResponseEntity<SonarQubeProjectRessource> response = restTemplate
				.getForEntity(sonarQubeApiCall.asString(), SonarQubeProjectRessource.class);
		return response.getBody();
	}

}

package com.viadee.sonarQuest.services;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.viadee.sonarQuest.entities.SonarConfig;
import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssueRessource;
import com.viadee.sonarQuest.externalRessources.SonarQubePaging;
import com.viadee.sonarQuest.externalRessources.SonarQubeProject;
import com.viadee.sonarQuest.externalRessources.SonarQubeProjectRessource;
import com.viadee.sonarQuest.repositories.StandardTaskRepository;
import com.viadee.sonarQuest.rules.SonarQubeStatusMapper;
import com.viadee.sonarQuest.rules.SonarQuestStatus;

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

	private static final String ERROR_NO_CONNECTION = "No connection to backend - please adjust the url to the sonar server or start this server with --simulateSonarServer=true";

	public List<World> generateWorldsFromSonarQubeProjects() {
		return getSonarQubeProjects().stream().map(this::toWorld).collect(Collectors.toList());
	}

	public World toWorld(final SonarQubeProject sonarQubeProject) {
		return new World(sonarQubeProject.getName(), sonarQubeProject.getKey(), false);
	}

	public List<StandardTask> generateStandardTasksFromSonarQubeIssuesForWorld(final World world) {
		final List<SonarQubeIssue> sonarQubeIssues = getIssuesForSonarQubeProject(world.getProject());
		return sonarQubeIssues.stream().map(sonarQubeIssue -> toTask(sonarQubeIssue, world))
				.collect(Collectors.toList());
	}

	public StandardTask toTask(final SonarQubeIssue sonarQubeIssue, final World world) {
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
			//new issue from SonarQube: Create new task
			sonarQubeTask = new StandardTask(sonarQubeIssue.getMessage(), status.getText(), gold, xp, null, world,
					sonarQubeIssue.getKey(), sonarQubeIssue.getComponent(), sonarQubeIssue.getSeverity(),
					sonarQubeIssue.getType(), debt, sonarQubeIssue.getKey());
		} else {
			//issue already in SonarQuest database: update the task
			sonarQubeTask.setSonarQuestStatus(status);
		}
		return sonarQubeTask;
	}

	public List<SonarQubeProject> getSonarQubeProjects() {
		try {
			final SonarConfig sonarConfig = sonarConfigService.getConfig();
			final List<SonarQubeProject> sonarQubeProjects = new ArrayList<>();
			final SonarQubeProjectRessource sonarQubeProjectRessource = getSonarQubeProjecRessourceForPageIndex(sonarConfig, 1);

			sonarQubeProjects.addAll(sonarQubeProjectRessource.getSonarQubeProjects());

			final Integer pagesOfExternalProjects = determinePagesOfExternalRessourcesToBeRequested(sonarQubeProjectRessource.getPaging());
			for (int i = 2; i <= pagesOfExternalProjects; i++) {
				sonarQubeProjects.addAll(getSonarQubeProjecRessourceForPageIndex(sonarConfig, i).getSonarQubeProjects());
			}
			return sonarQubeProjects;
		} catch (final Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}
	}

	public List<SonarQubeIssue> getIssuesForSonarQubeProject(final String projectKey) {
		try {
			final SonarConfig sonarConfig = sonarConfigService.getConfig();
			final List<SonarQubeIssue> sonarQubeIssueList = new ArrayList<>();
			final SonarQubeIssueRessource sonarQubeIssueRessource = getSonarQubeIssueResourceForProjectAndPageIndex(
					sonarConfig, projectKey, 1);

			sonarQubeIssueList.addAll(sonarQubeIssueRessource.getIssues());

			final Integer pagesOfExternalIssues = determinePagesOfExternalRessourcesToBeRequested(
					sonarQubeIssueRessource.getPaging());
			for (int i = 2; i <= pagesOfExternalIssues; i++) {
				sonarQubeIssueList.addAll(getSonarQubeIssueResourceForProjectAndPageIndex(sonarConfig, projectKey, i).getIssues());
			}
			return sonarQubeIssueList;
		} catch (final ResourceAccessException e) {
			if (e.getCause() instanceof ConnectException) {
				LOGGER.error(ERROR_NO_CONNECTION);
			}
			throw e;
		}
	}

	public int determinePagesOfExternalRessourcesToBeRequested(final SonarQubePaging sonarQubePaging) {
		return sonarQubePaging.getTotal() / sonarQubePaging.getPageSize() + 1;
	}

	private SonarQubeIssueRessource getSonarQubeIssueResourceForProjectAndPageIndex(final SonarConfig sonarConfig,
			final String projectKey, final int pageIndex) {
		final RestTemplate restTemplate = restTemplateService.getRestTemplate(sonarConfig);
		final String fooResourceUrl = sonarConfig.getSonarServerUrl() + "/api/issues/search?componentRoots="
				+ projectKey + "&pageSize=500&pageIndex=" + pageIndex;
		final ResponseEntity<SonarQubeIssueRessource> response = restTemplate.getForEntity(fooResourceUrl,
				SonarQubeIssueRessource.class);
		return response.getBody();
	}

	private SonarQubeProjectRessource getSonarQubeProjecRessourceForPageIndex(final SonarConfig sonarConfig,
			final int pageIndex) {
		final RestTemplate restTemplate = restTemplateService.getRestTemplate(sonarConfig);
		final String fooResourceUrl = sonarConfig.getSonarServerUrl()
				+ "/api/components/search?qualifiers=TRK&pageSize=500&pageIndex=" + pageIndex;
		final ResponseEntity<SonarQubeProjectRessource> response = restTemplate.getForEntity(fooResourceUrl,
				SonarQubeProjectRessource.class);
		return response.getBody();
	}

}

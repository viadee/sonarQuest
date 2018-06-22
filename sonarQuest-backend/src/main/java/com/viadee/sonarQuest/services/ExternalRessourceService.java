package com.viadee.sonarQuest.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.externalRessources.SonarQubeProject;
import com.viadee.sonarQuest.rules.SonarQubeStatusMapper;
import com.viadee.sonarQuest.rules.SonarQuestStatus;

/**
 * Actual implementation is either {@link RealExternalRessourceService} or {@link SimulatedExternalRessourceService},
 * depending on the command line property simulateSonarServer
 */
public abstract class ExternalRessourceService {

    @Autowired
    private StandardTaskEvaluationService standardTaskEvaluationService;

    @Autowired
    private SonarQubeStatusMapper statusMapper;

    public abstract List<SonarQubeProject> getSonarQubeProjects();

    public List<World> generateWorldsFromSonarQubeProjects() {
        return getSonarQubeProjects().stream().map(this::toWorld).collect(Collectors.toList());
    }

    public World toWorld(final SonarQubeProject sonarQubeProject) {
        return new World(sonarQubeProject.getName(), sonarQubeProject.getKey(), false);
    }

    public abstract List<SonarQubeIssue> getIssuesForSonarQubeProject(String projectKey);

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
        return new StandardTask(sonarQubeIssue.getMessage(), status.getText(), gold, xp, null, world,
                sonarQubeIssue.getKey(), sonarQubeIssue.getComponent(), sonarQubeIssue.getSeverity(),
                sonarQubeIssue.getType(), debt, sonarQubeIssue.getKey());
    }

}

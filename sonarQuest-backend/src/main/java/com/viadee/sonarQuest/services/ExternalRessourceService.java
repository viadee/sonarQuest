package com.viadee.sonarQuest.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.viadee.sonarQuest.constants.TaskStates;
import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.externalRessources.SonarQubeProject;

/**
 * Actual implementation is either {@link RealExternalRessourceService} or {@link SimulatedExternalRessourceService},
 * depending on the command line property simulateSonarServer
 */
public abstract class ExternalRessourceService {

    @Autowired
    private StandardTaskEvaluationService standardTaskEvaluationService;

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
        return new StandardTask(sonarQubeIssue.getMessage(), mapExternalStatus(sonarQubeIssue), gold, xp, null, world,
                sonarQubeIssue.getKey(), sonarQubeIssue.getComponent(), sonarQubeIssue.getSeverity(),
                sonarQubeIssue.getType(), debt, sonarQubeIssue.getKey());
    }

    public String mapExternalStatus(final SonarQubeIssue sonarQubeIssue) {
        String mappedStatus;
        final String externalStatus = sonarQubeIssue.getStatus();
        final String resolution = sonarQubeIssue.getResolution();
        switch (externalStatus) {
            case "OPEN":
            case "REOPENED":
            case "CONFIRMED":
                mappedStatus = TaskStates.OPEN;
                break;
            case "CLOSED":
                mappedStatus = TaskStates.SOLVED;
                break;
            case "RESOLVED":
                if (resolution.equals("FALSE-POSITIVE")) {
                    mappedStatus = TaskStates.CLOSED;
                } else {
                    mappedStatus = TaskStates.OPEN;
                }
                break;
            default:
                mappedStatus = TaskStates.OPEN;
                break;
        }
        return mappedStatus;
    }
}

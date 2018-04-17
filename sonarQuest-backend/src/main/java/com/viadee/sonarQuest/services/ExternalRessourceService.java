package com.viadee.sonarQuest.services;

import com.viadee.sonarQuest.constants.TaskStates;
import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.externalRessources.SonarQubeProject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Actual implementation is either {@link RealExternalRessourceService} or {@link SimulatedExternalRessourceService}, depending on the command line property simulateSonarServer
 */
public abstract class ExternalRessourceService {

    @Autowired
    private StandardTaskEvaluationService standardTaskEvaluationService;

    public abstract List<SonarQubeProject> getSonarQubeProjects();

    public List<World> generateWorldsFromSonarQubeProjects(){
        return getSonarQubeProjects().stream().map(this::toWorld).collect(Collectors.toList());
    }

    public World toWorld(SonarQubeProject sonarQubeProject) {
        return new World(sonarQubeProject.getName(), sonarQubeProject.getKey(), false);
    }

    public abstract List<SonarQubeIssue> getIssuesForSonarQubeProject(String projectKey);

    public List<StandardTask> generateStandardTasksFromSonarQubeIssuesForWorld(World world){
        List<SonarQubeIssue> sonarQubeIssues = this.getIssuesForSonarQubeProject(world.getProject());
        return sonarQubeIssues.stream().map(sonarQubeIssue -> toTask(sonarQubeIssue, world)).collect(Collectors.toList());
    }

    public StandardTask toTask(SonarQubeIssue sonarQubeIssue, World world){
        Long gold = standardTaskEvaluationService.evaluateGoldAmount(sonarQubeIssue.getDebt());
        Long xp = standardTaskEvaluationService.evaluateXP(sonarQubeIssue.getSeverity());
        Integer debt = Math.toIntExact(standardTaskEvaluationService.getDebt(sonarQubeIssue.getDebt()));
        return new StandardTask(sonarQubeIssue.getMessage(), mapExternalStatus(sonarQubeIssue),gold,xp,null,world,sonarQubeIssue.getKey(),sonarQubeIssue.getComponent(),sonarQubeIssue.getSeverity(),sonarQubeIssue.getType(),debt);
    }

    public String mapExternalStatus(SonarQubeIssue sonarQubeIssue){
        String mappedStatus;
        String externalStatus = sonarQubeIssue.getStatus();
        String resolution = sonarQubeIssue.getResolution();
        switch (externalStatus){
            case  "OPEN" :
            case "REOPENED" :
            case "CONFIRMED":
                mappedStatus= TaskStates.OPEN;
                break;
            case "CLOSED":
                mappedStatus= TaskStates.SOLVED;
                break;
            case "RESOLVED":
                if(resolution.equals("FALSE-POSITIVE")){
                    mappedStatus= TaskStates.CLOSED;
                }else{
                    mappedStatus= TaskStates.OPEN;
                }
                break;
            default:
                mappedStatus=TaskStates.OPEN;
                break;
        }
        return mappedStatus;
    }
}

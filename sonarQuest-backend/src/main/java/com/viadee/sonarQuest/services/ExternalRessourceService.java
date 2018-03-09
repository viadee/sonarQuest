package com.viadee.sonarQuest.services;

import com.viadee.sonarQuest.constants.RessourceEndpoints;
import com.viadee.sonarQuest.constants.TaskStates;
import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssue;
import com.viadee.sonarQuest.externalRessources.SonarQubeIssueRessource;
import com.viadee.sonarQuest.externalRessources.SonarQubeProject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExternalRessourceService {

    @Autowired
    private StandardTaskEvaluationService standardTaskEvaluationService;



    public List<SonarQubeProject> getSonarQubeProjects(){
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<List<SonarQubeProject>> projectResponse = restTemplate.exchange(RessourceEndpoints.DEV_ENDPOINT+"/projects", HttpMethod.GET, null, new ParameterizedTypeReference<List<SonarQubeProject>>() {});
        List<SonarQubeProject> sonarQubeProjects = projectResponse.getBody();
        return sonarQubeProjects;
    }

    public List<World> generateWorldsFromSonarQubeProjects(){
        List<SonarQubeProject> sonarQubeProjects = this.getSonarQubeProjects();
        List<World> worlds=sonarQubeProjects.stream().map(sonarQubeProject -> toWorld(sonarQubeProject)).collect(Collectors.toList());
        return worlds;
    }

    public World toWorld(SonarQubeProject sonarQubeProject) {
        World world = new World(sonarQubeProject.getName(), sonarQubeProject.getKey(), false);
        return world;
    }

    public List<SonarQubeIssue> getIssuesForSonarQubeProject(String projectKey){
        RestTemplate restTemplate = new RestTemplate();
        SonarQubeIssueRessource sonarQubeIssueRessource = restTemplate.getForObject(RessourceEndpoints.DEV_ENDPOINT+"/issues/search?projectKeys=" +projectKey, SonarQubeIssueRessource.class);
        return sonarQubeIssueRessource.getIssues();
    }

    public List<StandardTask> generateStandardTasksFromSonarQubeIssuesForWorld(World world){
        List<SonarQubeIssue> sonarQubeIssues = this.getIssuesForSonarQubeProject(world.getProject());
        List<StandardTask> tasks = sonarQubeIssues.stream().map(sonarQubeIssue -> toTask(sonarQubeIssue, world)).collect(Collectors.toList());
        return tasks;
    }

    public StandardTask toTask(SonarQubeIssue sonarQubeIssue, World world){
        Long gold = standardTaskEvaluationService.evaluateGoldAmount(sonarQubeIssue.getDebt());
        Long xp = standardTaskEvaluationService.evaluateXP(sonarQubeIssue.getSeverity());
        Integer debt = Math.toIntExact(standardTaskEvaluationService.getDebt(sonarQubeIssue.getDebt()));
        StandardTask task = new StandardTask(sonarQubeIssue.getMessage(), mapExternalStatus(sonarQubeIssue),gold,xp,null,world,sonarQubeIssue.getKey(),sonarQubeIssue.getComponent(),sonarQubeIssue.getSeverity(),sonarQubeIssue.getType(),debt);
        return task;
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

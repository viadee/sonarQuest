package com.viadee.sonarQuest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.StandardTask;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.StandardTaskRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.rules.SonarQuestStatus;

@Service
public class StandardTaskService {

    @Autowired
    private ExternalRessourceService externalRessourceService;

    @Autowired
    private GratificationService gratificationService;

    @Autowired
    private StandardTaskRepository standardTaskRepository;

    @Autowired
    private QuestService questService;

    @Autowired
    private AdventureService adventureService;

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private NamedParameterJdbcTemplate template;

    public void updateStandardTasks(final World world) {
        final List<StandardTask> externalStandardTasks = externalRessourceService
                .generateStandardTasksFromSonarQubeIssuesForWorld(world);
        externalStandardTasks.forEach(this::updateStandardTask);
        questService.updateQuests();
        adventureService.updateAdventures();
    }

    public StandardTask updateStandardTask(final StandardTask task) {
        final SonarQuestStatus oldStatus = getLastState(task);
        final SonarQuestStatus newStatus = SonarQuestStatus.fromStatusText(task.getStatus());
        if (newStatus == SonarQuestStatus.SOLVED && oldStatus != SonarQuestStatus.SOLVED) {
            gratificationService.rewardUserForSolvingTask(task);
        }
        task.setStatus(SonarQuestStatus.CREATED.getText());
        return standardTaskRepository.saveAndFlush(task);
    }

    protected SonarQuestStatus getLastState(final StandardTask task) {
        SqlParameterSource params = new MapSqlParameterSource().addValue("id", task.getId());
        String sql = "SELECT Status FROM Task WHERE id = :id";
        RowMapper<String> rowmapper = new SingleColumnRowMapper<>();
        List<String> statusTexte = template.query(sql, params, rowmapper);
        String statusText = statusTexte.isEmpty() ? null : statusTexte.get(0);
        return SonarQuestStatus.fromStatusText(statusText);
    }

    public void setExternalRessourceService(final ExternalRessourceService externalRessourceService) {
        this.externalRessourceService = externalRessourceService;
    }

    public void save(final StandardTask standardTask) {

        final World world = worldRepository.findByProject(standardTask.getWorld().getProject());

        final StandardTask st = new StandardTask(
                standardTask.getTitle(),
                SonarQuestStatus.CREATED.getText(),
                standardTask.getGold(),
                standardTask.getXp(),
                standardTask.getQuest(),
                world, null, null, null, null, null, null);

        standardTaskRepository.save(st);
    }

}

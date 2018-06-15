package com.viadee.sonarQuest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.SpecialTask;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.SpecialTaskRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.rules.SonarQuestStatus;

@Service
public class SpecialTaskService {

    @Autowired
    private SpecialTaskRepository specialTaskRepository;

    @Autowired
    private WorldRepository worldRepository;

    public void saveDto(final SpecialTask specialTaskDto) {

        final World world = worldRepository.findByProject(specialTaskDto.getWorld().getProject());

        final SpecialTask sp = new SpecialTask(
                specialTaskDto.getTitle(),
                SonarQuestStatus.CREATED.getText(),
                specialTaskDto.getGold(),
                specialTaskDto.getXp(),
                specialTaskDto.getQuest(),
                specialTaskDto.getMessage(),
                world);

        specialTaskRepository.save(sp);
    }

    public SpecialTask updateSpecialTask(final SpecialTask taskDto) {
        final SpecialTask task = specialTaskRepository.findOne(taskDto.getId());
        task.setTitle(taskDto.getTitle());
        task.setGold(taskDto.getGold());
        task.setXp(taskDto.getXp());
        task.setMessage(taskDto.getMessage());
        return specialTaskRepository.save(task);
    }

}

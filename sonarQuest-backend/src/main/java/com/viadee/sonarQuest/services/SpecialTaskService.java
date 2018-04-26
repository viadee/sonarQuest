package com.viadee.sonarQuest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.constants.TaskStates;
import com.viadee.sonarQuest.dtos.SpecialTaskDto;
import com.viadee.sonarQuest.entities.SpecialTask;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.SpecialTaskRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;

@Service
public class SpecialTaskService {

    @Autowired
    private SpecialTaskRepository specialTaskRepository;

    @Autowired
    private WorldRepository worldRepository;

    public void saveDto(final SpecialTaskDto specialTaskDto) {

        final World world = worldRepository.findByProject(specialTaskDto.getWorld().getProject());

        final SpecialTask sp = new SpecialTask(
                specialTaskDto.getTitle(),
                TaskStates.CREATED,
                specialTaskDto.getGold(),
                specialTaskDto.getXp(),
                specialTaskDto.getQuest(),
                specialTaskDto.getMessage(),
                world);

        specialTaskRepository.save(sp);
    }

}

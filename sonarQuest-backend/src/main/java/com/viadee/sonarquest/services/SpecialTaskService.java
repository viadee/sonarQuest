package com.viadee.sonarquest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.entities.SpecialTask;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.SpecialTaskRepository;
import com.viadee.sonarquest.repositories.WorldRepository;
import com.viadee.sonarquest.rules.SonarQuestStatus;

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
                SonarQuestStatus.OPEN,
                specialTaskDto.getGold(),
                specialTaskDto.getXp(),
                specialTaskDto.getQuest(),
                specialTaskDto.getMessage(),
                world);

        specialTaskRepository.save(sp);
    }

    public SpecialTask updateSpecialTask(final SpecialTask taskDto) {
        final SpecialTask task = specialTaskRepository.findById(taskDto.getId()).orElseThrow(ResourceNotFoundException::new);
        task.setTitle(taskDto.getTitle());
        task.setGold(taskDto.getGold());
        task.setXp(taskDto.getXp());
        task.setMessage(taskDto.getMessage());
        return specialTaskRepository.save(task);
    }

    public List<SpecialTask> findAll() {
        return specialTaskRepository.findAll();
    }

    public List<SpecialTask> findByWorld(final World w) {
        return specialTaskRepository.findByWorld(w);
    }

    public SpecialTask findById(final Long id) {
        return specialTaskRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

}

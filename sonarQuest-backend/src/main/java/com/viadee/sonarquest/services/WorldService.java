package com.viadee.sonarquest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.WorldRepository;

@Service
public class WorldService {

    @Autowired
    private ExternalRessourceService externalRessourceService;

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private StandardTaskService standardTaskService;

    public List<World> findAll() {
        return worldRepository.findAll();
    }

    public World save(final World world) {
        return worldRepository.save(world);
    }

    public void updateWorlds() {
        final List<World> externalWorlds = externalRessourceService.generateWorldsFromSonarQubeProjects();
        externalWorlds.forEach(this::saveWorldIfNotExists);
    }

    private void saveWorldIfNotExists(final World externalWorld) {
        final World internalWorld = worldRepository.findByProject(externalWorld.getProject());
        if (internalWorld == null) {
            worldRepository.save(externalWorld);
        }
    }

    public void setExternalRessourceService(final ExternalRessourceService externalRessourceService) {
        this.externalRessourceService = externalRessourceService;
    }

    public List<World> findAllActiveWorlds() {
        return worldRepository.findByActiveTrue();
    }

    public World findById(final Long id) {
        return worldRepository.findOne(id);
    }

    public World updateWorld(final World world) {
        World currentWorld = worldRepository.findOne(world.getId());
        if (world != null) {
            currentWorld.setName(world.getName());
            currentWorld.setActive(world.getActive());
            currentWorld.setUsequestcards(world.getUsequestcards());
            currentWorld = worldRepository.save(currentWorld);
            standardTaskService.updateStandardTasks(currentWorld);
        }
        return currentWorld;
    }

}

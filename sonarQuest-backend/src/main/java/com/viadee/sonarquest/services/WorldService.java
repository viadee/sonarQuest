package com.viadee.sonarquest.services;

import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.WorldRepository;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorldService {

    private final ExternalRessourceService externalRessourceService;

    private final WorldRepository worldRepository;

    private final StandardTaskService standardTaskService;

    public WorldService(ExternalRessourceService externalRessourceService, WorldRepository worldRepository, StandardTaskService standardTaskService) {
        this.externalRessourceService = externalRessourceService;
        this.worldRepository = worldRepository;
        this.standardTaskService = standardTaskService;
    }

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

    public List<World> findAllActiveWorlds() {
        return worldRepository.findByActiveTrue();
    }

    public World findById(final Long id) throws ResourceNotFoundException {
        return worldRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
    }

    public World updateWorld(final World world) {
        World currentWorld = null;
        if (world != null) {
            currentWorld = worldRepository.findById(world.getId()).orElseThrow(ResourceNotFoundException::new);
            currentWorld.setName(world.getName());
            currentWorld.setActive(world.getActive());
            currentWorld.setUsequestcards(world.getUsequestcards());
            currentWorld = worldRepository.save(currentWorld);
            standardTaskService.updateStandardTasks(currentWorld);
        }
        return currentWorld;
    }

}

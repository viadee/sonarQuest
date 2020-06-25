package com.viadee.sonarquest.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.WorldRepository;

@Service
public class WorldService {
	private static final Logger LOGGER = LoggerFactory.getLogger(WorldService.class);

    @Autowired
    private ExternalRessourceService externalRessourceService;

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private StandardTaskService standardTaskService;
    
    @Autowired
    private QualityGateRaidService qualityGateRaidService;
    
    public List<World> findAll() {
        return worldRepository.findAll();
    }

    public World save(final World world) {
        return worldRepository.save(world);
    }

    public void updateWorlds() {
    	LOGGER.info("update worlds");
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
    	LOGGER.info("update World");
        World currentWorld = null;
        if (world != null) {
            currentWorld = worldRepository.findOne(world.getId());
            currentWorld.setName(world.getName());
            currentWorld.setBranch(world.getBranch());
            currentWorld.setFilter(world.getFilter());
            currentWorld.setActive(world.getActive());
            currentWorld.setUsequestcards(world.getUsequestcards());
            currentWorld = worldRepository.save(currentWorld);
            standardTaskService.updateStandardTasks(currentWorld);
            qualityGateRaidService.updateDefaultQualityGateRaidFromWorld(currentWorld);
        }
        return currentWorld;
    }

}

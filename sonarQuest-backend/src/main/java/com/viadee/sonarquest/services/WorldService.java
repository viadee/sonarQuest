package com.viadee.sonarquest.services;

import java.util.List;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.WorldRepository;

@Service
public class WorldService {

    private final WorldRepository worldRepository;

    public WorldService(final WorldRepository worldRepository) {
        this.worldRepository = worldRepository;
    }

    public List<World> findAll() {
        return worldRepository.findAll();
    }

    public World save(final World world) {
        return worldRepository.save(world);
    }

    public void saveWorlds(final List<World> worlds) {
        worlds.forEach(this::saveWorldIfNotExists);
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

    public World findById(final Long id) {
        if(id != null){
            return worldRepository.findById(id).orElseThrow(ResourceNotFoundException::new);
        }
        return null;
    }

    public World updateWorld(final World world) {
        World currentWorld;
        currentWorld = worldRepository.findById(world.getId()).orElseThrow(ResourceNotFoundException::new);
        currentWorld.setName(world.getName());
        currentWorld.setActive(world.getActive());
        currentWorld.setUsequestcards(world.getUsequestcards());
        currentWorld = worldRepository.save(currentWorld);
        return currentWorld;
    }

}

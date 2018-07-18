package com.viadee.sonarQuest.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.entities.GitServer;
import com.viadee.sonarQuest.repositories.GitServerRepository;
import com.viadee.sonarQuest.services.WorldService;

@RestController
@RequestMapping("/gitserver")
public class GitServerController {
    private final WorldService worldService;
    private final GitServerRepository gitServerRepository;

    public GitServerController(WorldService worldService, GitServerRepository gitServerRepository) {
        this.worldService = worldService;
        this.gitServerRepository = gitServerRepository;
    }

    @PreAuthorize("hasAuthority('FULL_WORLD_ACCESS')")
    @RequestMapping(value = "/world/{id}", method = RequestMethod.GET)
    public GitServer getServerInfoByWorld(@PathVariable("id") long worldId) {
        return gitServerRepository.findOneByWorld(worldService.findById(worldId));
    }

    @PreAuthorize("hasAuthority('FULL_WORLD_ACCESS')")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteAdventure(@PathVariable("id") long id) {
        gitServerRepository.delete(id);
    }

    @PreAuthorize("hasAuthority('FULL_WORLD_ACCESS')")
    @RequestMapping(method = RequestMethod.POST)
    public GitServer updateServerInfo(@RequestBody GitServer data) {
        GitServer server = gitServerRepository.findOneByWorld(worldService.findById(data.getWorld().getId()));
        if (server == null) {
            server = new GitServer();
        }
        server.setUrl(data.getUrl());
        server.setUsername(data.getUsername());
        server.setPassword(data.getPassword());
        server.setWorld(worldService.findById(data.getWorld().getId()));
        server = gitServerRepository.save(server);
        return server;
    }
}

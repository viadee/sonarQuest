package com.viadee.sonarquest.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.services.ExternalRessourceService;
import com.viadee.sonarquest.services.StandardTaskService;
import com.viadee.sonarquest.services.UserService;
import com.viadee.sonarquest.services.WorldService;

@RestController
@RequestMapping("/world")
public class WorldController {

    private final UserService userService;

    private final WorldService worldService;

    private final StandardTaskService standardTaskService;

    private final ExternalRessourceService externalRessourceService;

    public WorldController(final UserService userService, final WorldService worldService,
            final StandardTaskService standardTaskService, final ExternalRessourceService externalRessourceService) {
        this.userService = userService;
        this.worldService = worldService;
        this.standardTaskService = standardTaskService;
        this.externalRessourceService = externalRessourceService;
    }

    @GetMapping(value = "/current")
    public World getCurrentWorld(final Principal principal) {
        final User user = userService.findByUsername(principal.getName());
        return user.getCurrentWorld();
    }

    @PostMapping(value = "/current")
    public World setCurrentWorld(final Principal principal, @RequestBody final World world) {
        final User user = userService.findByUsername(principal.getName());
        final World currentWorld = worldService.findById(world.getId());
        user.setCurrentWorld(currentWorld);
        user.setLastTavernVisit(null);
        return userService.updateUser(user).getCurrentWorld();
    }

    @GetMapping(value = "/worlds")
    public List<World> getWorlds(final Principal principal) {
        final User user = userService.findByUsername(principal.getName());
        return user.getWorlds();
    }

    @PreAuthorize("hasAuthority('FULL_WORLD_ACCESS')")
    @GetMapping(value = "/all")
    public List<World> getAllWorlds() {
        return worldService.findAll();
    }

    @PreAuthorize("hasAuthority('FULL_WORLD_ACCESS')")
    @GetMapping(value = "/active")
    public List<World> activeWorlds(final Principal principal) {
        return worldService.findAllActiveWorlds();
    }

    @GetMapping(value = "/world/{id}")
    public World getWorldById(@PathVariable(value = "id") final Long id) {
        return worldService.findById(id);
    }

    @PreAuthorize("hasAuthority('FULL_WORLD_ACCESS')")
    @PostMapping(value = "/world")
    public World updateWorld(@RequestBody final World data) {
         final World world = worldService.updateWorld(data);
         standardTaskService.updateStandardTasks(world);
         return world;
    }

    @PreAuthorize("hasAuthority('ACTIVE_WORLD_ACCESS')")
    @PutMapping(value = "/world/{id}/image")
    public World updateBackground(@PathVariable(value = "id") final Long id, @RequestBody final String image) {
        World world = worldService.findById(id);
        if (world != null) {
            world.setImage(image);
            world = worldService.save(world);
        }
        return world;
    }

    @PreAuthorize("hasAuthority('FULL_WORLD_ACCESS')")
    @GetMapping(value = "/generate")
    public List<World> generateWorlds() {
        final List<World> externalWorlds = externalRessourceService.generateWorldsFromSonarQubeProjects();
        worldService.saveWorlds(externalWorlds);
        return worldService.findAll();
    }

}

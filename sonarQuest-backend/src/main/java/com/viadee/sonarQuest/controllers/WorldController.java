package com.viadee.sonarQuest.controllers;

import static com.viadee.sonarQuest.dtos.WorldDto.toWorldDto;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.dtos.WorldDto;
import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.entities.World;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.repositories.WorldRepository;
import com.viadee.sonarQuest.services.UserService;
import com.viadee.sonarQuest.services.WorldService;

@RestController
@RequestMapping("/world")
public class WorldController {

    @Autowired
    private WorldRepository worldRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private WorldService worldService;

    @RequestMapping(method = RequestMethod.GET)
    public List<WorldDto> getAllWorlds() {
        return this.worldRepository.findAll().stream().map(WorldDto::toWorldDto).collect(Collectors.toList());
    }

    @CrossOrigin
    @RequestMapping(value = "/developer/{developer_id}", method = RequestMethod.GET)
    public WorldDto getCurrentWorld(final Principal principal) {
        final User user = userService.findByUsername(principal.getName());
        // TODO Hier dürfen nur die Welten für den User übergeben werden.
        final Developer d = this.developerRepository.findById(user.getId());
        final World w = this.worldService.getCurrentWorld(d);
        return toWorldDto(w);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public WorldDto getWorldById(@PathVariable(value = "id") final Long id) {
        final World world = this.worldRepository.findOne(id);
        return toWorldDto(world);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public WorldDto updateWorld(@PathVariable(value = "id") final Long id, @RequestBody final WorldDto worldDto) {
        World world = this.worldRepository.findOne(id);
        if (world != null) {
            world.setName(worldDto.getName());
            world.setActive(worldDto.getActive());
            world = this.worldRepository.save(world);
        }
        return toWorldDto(world);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}/image", method = RequestMethod.PUT)
    public WorldDto updateBackground(@PathVariable(value = "id") final Long id, @RequestBody final String image) {
        World world = this.worldRepository.findOne(id);
        if (world != null) {
            world.setImage(image);
            world = this.worldRepository.save(world);
        }
        return toWorldDto(world);
    }

    @RequestMapping(value = "/generate", method = RequestMethod.GET)
    public List<WorldDto> generateWorlds() {
        worldService.updateWorlds();
        final List<World> worlds = worldRepository.findAll();
        return worlds.stream().map(WorldDto::toWorldDto).collect(Collectors.toList());

    }

}

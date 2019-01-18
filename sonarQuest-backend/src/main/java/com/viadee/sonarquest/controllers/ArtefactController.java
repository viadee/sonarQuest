package com.viadee.sonarquest.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.Artefact;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.ArtefactRepository;
import com.viadee.sonarquest.services.ArtefactService;
import com.viadee.sonarquest.services.UserService;

@RestController
@RequestMapping("/artefact")
public class ArtefactController {

    @Autowired
    private ArtefactRepository artefactRepository;

    @Autowired
    private ArtefactService artefactService;

    @Autowired
    private UserService userService;

    @GetMapping
    public List<Artefact> getAllArtefacts() {
        return artefactService.getArtefacts();
    }

    @GetMapping(value = "/forMarketplace/")
    public List<Artefact> getArtefactsforMarkteplace() {
        return artefactService.getArtefactsforMarkteplace();
    }

    @GetMapping(value = "/{id}")
    public Artefact getArtefactById(@PathVariable(value = "id") final Long id) {
        return artefactService.getArtefact(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artefact createArtefact(@RequestBody final Artefact artefact) {
        return artefactService.createArtefact(artefact);
    }

    @PutMapping(value = "/{id}")
    public Artefact updateArtefact(@PathVariable(value = "id") final Long id, @RequestBody final Artefact artefactDto) {
        return artefactService.updateArtefact(id, artefactDto);
    }

    @PutMapping(value = "/{artefact_id}/buy")
    public boolean buyArtefact(final Principal principal, @PathVariable(value = "artefact_id") final Long artefact_id) {
        final User user = userService.findByUsername(principal.getName());
        final Artefact artefact = artefactRepository.findOne(artefact_id);

        return artefactService.buyArtefact(artefact, user) != null;
    }

    @DeleteMapping(value = "/{id}")
    public void deleteArtefact(@PathVariable(value = "id") final Long id) {
        if (artefactRepository.findOne(id) != null) {
            artefactRepository.delete(id);
        }
    }

}

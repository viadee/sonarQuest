package com.viadee.sonarQuest.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.entities.Artefact;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.repositories.ArtefactRepository;
import com.viadee.sonarQuest.services.ArtefactService;
import com.viadee.sonarQuest.services.UserService;

@RestController
@RequestMapping("/artefact")
public class ArtefactController {

    @Autowired
    private ArtefactRepository artefactRepository;

    @Autowired
    private ArtefactService artefactService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Artefact> getAllArtefacts() {
        return artefactService.getArtefacts();
    }

    @RequestMapping(value = "/forMarketplace/", method = RequestMethod.GET)
    public List<Artefact> getArtefactsforMarkteplace() {
        return artefactService.getArtefactsforMarkteplace();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Artefact getArtefactById(@PathVariable(value = "id") final Long id) {
        return artefactService.getArtefact(id);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Artefact createArtefact(@RequestBody final Artefact artefact) {
        return artefactService.createArtefact(artefact);
    }

    @CrossOrigin
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Artefact updateArtefact(@PathVariable(value = "id") final Long id,
            @RequestBody final Artefact artefactDto) {
        return artefactService.updateArtefact(id, artefactDto);
    }

    @CrossOrigin
    @RequestMapping(value = "/{artefact_id}/buy", method = RequestMethod.PUT)
    public boolean buyArtefact(final Principal principal, @PathVariable(value = "artefact_id") final Long artefact_id) {
        final User user = userService.findByUsername(principal.getName());
        final Artefact artefact = artefactRepository.findOne(artefact_id);

        return artefactService.buyArtefact(artefact, user) != null;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public void deleteArtefact(@PathVariable(value = "id") final Long id) {
        if (artefactRepository.findOne(id) != null) {
            artefactRepository.delete(id);
        }
    }

}

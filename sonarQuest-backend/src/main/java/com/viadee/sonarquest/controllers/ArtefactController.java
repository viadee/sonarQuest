package com.viadee.sonarquest.controllers;

import java.security.Principal;
import java.util.List;

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
import com.viadee.sonarquest.services.ArtefactService;
import com.viadee.sonarquest.services.UserService;

@RestController
@RequestMapping("/artefact")
public class ArtefactController {

    private final ArtefactService artefactService;

    private final UserService userService;

    private final WebSocketController webSocketController;

    public ArtefactController(final ArtefactService artefactService, final UserService userService, final WebSocketController webSocketController) {
        this.artefactService = artefactService;
        this.userService = userService;
        this.webSocketController = webSocketController;
    }

    @GetMapping
    public List<Artefact> getAllArtefacts() {
        return artefactService.getArtefacts();
    }

    @GetMapping(value = "/forMarketplace/")
    public List<Artefact> getArtefactsforMarketplace() {
        return artefactService.getArtefactsForMarketplace();
    }

    @GetMapping(value = "/{artefactId}")
    public Artefact getArtefactById(@PathVariable(value = "artefactId") final Long id) {
        return artefactService.getArtefact(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Artefact createArtefact(final Principal principal, @RequestBody final Artefact artefact) {
        webSocketController.onCreateArtefact(artefact, principal);
        return artefactService.createArtefact(artefact);
    }

    @PutMapping(value = "/{artefactId}")
    public Artefact updateArtefact(final Principal principal, @PathVariable(value = "artefactId") final Long artefactId, @RequestBody final Artefact artefact) {
        final Artefact savedArtefact = artefactService.updateArtefact(artefactId, artefact);
        webSocketController.onUpdateArtefact(artefact, principal);
        return savedArtefact;
    }

    @PutMapping(value = "/{artefactId}/buy")
    public Artefact buyArtefact(final Principal principal, @PathVariable(value = "artefactId") final Long artefactId) {
        final User user = userService.findByUsername(principal.getName());
        return artefactService.buyArtefact(artefactId, user);
    }

    @DeleteMapping(value = "/{artefactId}")
    public void deleteArtefact(final Principal principal, @PathVariable(value = "artefactId") final Long artefactId) {
        final Artefact artefact = artefactService.getArtefact(artefactId);
        webSocketController.onDeleteArtefact(artefact, principal);
        artefactService.deleteArtefact(artefactId);
    }

    @DeleteMapping(value = "/{artefactId}/payout")
    public void payoutArtefact(@PathVariable(value = "artefactId") final Long artefactId) {
        artefactService.payoutArtefact(artefactId);
    }

    @PutMapping(value = "/{artefactId}/removeFromMarketplace")
    public void removeArtefactFromMarketplace(@PathVariable(value = "artefactId") final Long artefactId) {
        artefactService.removeArtefactFromMarketplace(artefactId);
    }

}

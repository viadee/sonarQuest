package com.viadee.sonarquest.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.ParticipationRepository;
import com.viadee.sonarquest.services.ParticipationService;
import com.viadee.sonarquest.services.UserService;

@RestController
@RequestMapping("/participation")
public class ParticipationController {

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private ParticipationService participationService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Iterable<Participation> getAllParticipations() {
        return participationRepository.findAll();
    }

    @GetMapping(value = "/{questid}")
    public Participation getParticipation(final Principal principal,
            @PathVariable(value = "questid") final Long questid) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        return participationService.findParticipationByQuestIdAndUserId(questid, user.getId());
    }

    @GetMapping(value = "/{questid}/all")
    public List<Participation> getParticipations(final Principal principal,
            @PathVariable(value = "questid") final Long questid) {
        return participationService.findParticipationByQuestId(questid);
    }

    @GetMapping(value = "/allMyParticipations")
    public List<Participation> getAllMyParticipations(final Principal principal) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        return participationService.findParticipationByUser(user);
    }

    @PostMapping(value = "/{questid}")
    @ResponseStatus(HttpStatus.CREATED)
    public Participation createParticipation(final Principal principal,
            @PathVariable(value = "questid") final Long questid) {
        return this.participationService.createParticipation(principal, questid); 
    }

    @DeleteMapping(value = "/{questid}/{developerid}")
    public void deleteDeleteParticipation(final Principal principal,
            @PathVariable(value = "questid") final Long questid) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        final Participation foundParticipation = participationService.findParticipationByQuestIdAndUserId(questid,
                user.getId());
        if (foundParticipation != null) {
            participationRepository.delete(foundParticipation);
        }
    }

}

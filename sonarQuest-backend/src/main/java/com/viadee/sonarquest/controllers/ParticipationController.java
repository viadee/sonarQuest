package com.viadee.sonarquest.controllers;

import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.ParticipationRepository;
import com.viadee.sonarquest.services.ParticipationService;
import com.viadee.sonarquest.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/participation")
public class ParticipationController {

    private final ParticipationRepository participationRepository;

    private final ParticipationService participationService;

    private final UserService userService;

    public ParticipationController(ParticipationRepository participationRepository, ParticipationService participationService, UserService userService) {
        this.participationRepository = participationRepository;
        this.participationService = participationService;
        this.userService = userService;
    }

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

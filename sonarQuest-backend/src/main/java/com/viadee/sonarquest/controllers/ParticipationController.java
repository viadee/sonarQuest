package com.viadee.sonarquest.controllers;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.ParticipationRepository;
import com.viadee.sonarquest.repositories.QuestRepository;
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
    private QuestRepository questRepository;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Participation> getAllParticipations() {
        return participationRepository.findAll();
    }

    @RequestMapping(value = "/{questid}", method = RequestMethod.GET)
    public Participation getParticipation(final Principal principal,
            @PathVariable(value = "questid") final Long questid) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        return participationService.findParticipationByQuestIdAndUserId(questid, user.getId());
    }

    @RequestMapping(value = "/{questid}/all", method = RequestMethod.GET)
    public List<Participation> getParticipations(final Principal principal,
            @PathVariable(value = "questid") final Long questid) {
        return participationService.findParticipationByQuestId(questid);
    }

    @RequestMapping(value = "/allMyParticipations", method = RequestMethod.GET)
    public List<Participation> getAllMyParticipations(final Principal principal) {
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        return participationService.findParticipationByUser(user);
    }

    @RequestMapping(value = "/{questid}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Participation createParticipation(final Principal principal,
            @PathVariable(value = "questid") final Long questid) {
        final Quest foundQuest = questRepository.findOne(questid);
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        final Participation foundParticipation = participationRepository.findByQuestAndUser(foundQuest, user);
        Participation participation = null;
        if ((foundQuest != null) && (user != null) && (foundParticipation == null)) {
            participation = new Participation(foundQuest, user);
            participation = participationRepository.save(participation);
        }
        return participation;
    }

    @RequestMapping(value = "/{questid}/{developerid}", method = RequestMethod.DELETE)
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

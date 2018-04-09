package com.viadee.sonarQuest.controllers;

import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.repositories.ParticipationRepository;
import com.viadee.sonarQuest.repositories.QuestRepository;
import com.viadee.sonarQuest.services.ParticipationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    private DeveloperRepository developerRepository;

    @RequestMapping(method = RequestMethod.GET)
    public Iterable<Participation> getAllParticipations(){
        return this.participationRepository.findAll();
    }

    @RequestMapping(value = "/{questid}/{developerid}", method = RequestMethod.GET)
    public Participation getParticipationByQuestIdAndDeveloperId(@PathVariable(value = "questid") Long questid,@PathVariable(value = "developerid") Long developerid) {
        return participationService.findParticipationByQuestIdAndDeveloperId(questid,developerid);
    }

    @CrossOrigin
    @RequestMapping(value = "/{questid}/{developerid}",method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Participation createParticipation(@PathVariable(value = "questid") Long questid,@PathVariable(value = "developerid") Long developerid) {
        Quest foundQuest = questRepository.findOne(questid);
        Developer foundDeveloper = developerRepository.findOne(developerid);
        Participation foundParticipation = participationRepository.findByQuestAndDeveloper(foundQuest,foundDeveloper);
        Participation participation = null;
        if((foundQuest != null) && (foundDeveloper != null) && (foundParticipation == null)) {
            participation = new Participation(foundQuest, foundDeveloper);
            participation= participationRepository.save(participation);
        }
        return participation;


    }

    @RequestMapping(value ="/{questid}/{developerid}", method = RequestMethod.DELETE)
    public void deleteDeleteParticipation(@PathVariable(value = "questid") Long questid,@PathVariable(value = "developerid") Long developerid) {
        Participation foundParticipation = participationService.findParticipationByQuestIdAndDeveloperId(questid,developerid);
        if (foundParticipation != null) {
            participationRepository.delete(foundParticipation);
        }
    }


}

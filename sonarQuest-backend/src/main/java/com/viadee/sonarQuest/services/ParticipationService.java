package com.viadee.sonarQuest.services;

import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.repositories.DeveloperRepository;
import com.viadee.sonarQuest.repositories.ParticipationRepository;
import com.viadee.sonarQuest.repositories.QuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParticipationService {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    public Participation findParticipationByQuestIdAndDeveloperId(Long questId, Long developerId ){
        Quest foundQuest = questRepository.findOne(questId);
        Developer foundDeveloper = developerRepository.findOne(developerId);
        Participation foundParticipation = null;
        if((foundQuest != null) && (foundDeveloper != null)){
            foundParticipation = participationRepository.findByQuestAndDeveloper(foundQuest,foundDeveloper);
        }
        return foundParticipation;
    }
}

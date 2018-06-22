package com.viadee.sonarQuest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.repositories.ParticipationRepository;
import com.viadee.sonarQuest.repositories.QuestRepository;

@Service
public class ParticipationService {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private UserService userService;

    public Participation findParticipationByQuestIdAndUserId(final Long questId, final Long userId) {
        final Quest foundQuest = questRepository.findOne(questId);
        final User foundUser = userService.findById(userId);
        Participation foundParticipation = null;
        if ((foundQuest != null) && (foundUser != null)) {
            foundParticipation = participationRepository.findByQuestAndUser(foundQuest, foundUser);
        }
        return foundParticipation;
    }

    public List<Participation> findParticipationByQuestId(final Long questId) {
        final Quest foundQuest = questRepository.findOne(questId);
        return participationRepository.findByQuest(foundQuest);
    }
}

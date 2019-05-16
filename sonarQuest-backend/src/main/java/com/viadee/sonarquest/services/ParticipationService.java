package com.viadee.sonarquest.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.controllers.WebSocketController;
import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.ParticipationRepository;
import com.viadee.sonarquest.repositories.QuestRepository;

@Service
public class ParticipationService {

    @Autowired
    private QuestRepository questRepository;

    @Autowired
    private ParticipationRepository participationRepository;

    @Autowired
    private UserService userService;
    
    @Autowired
    private EventService eventService;
    
    @Autowired
    private WebSocketController webSocketController;

    public Participation findParticipationByQuestIdAndUserId(final Long questId, final Long userId) {
        final Quest quest = questRepository.findOne(questId);
        final User user = userService.findById(userId);
        if (quest != null && user != null) {
            return participationRepository.findByQuestAndUser(quest, user);
        }
        return null;
    }

    public List<Participation> findParticipationByQuestId(final Long questId) {
        final Quest foundQuest = questRepository.findOne(questId);
        return participationRepository.findByQuest(foundQuest);
    }

    public List<Participation> findParticipationByUser(final User user) {
        List<Participation> participations = new ArrayList<>();
        if (user != null) {
            participations = participationRepository.findByUser(user);
        }
        return participations;
    }

	public Participation createParticipation(Principal principal, Long questid) {
		final Quest foundQuest = questRepository.findOne(questid);
        final String username = principal.getName();
        final User user = userService.findByUsername(username);
        final Participation foundParticipation = participationRepository.findByQuestAndUser(foundQuest, user);
        Participation participation = null;
        if ((foundQuest != null) && (user != null) && (foundParticipation == null)) {
            participation = new Participation(foundQuest, user);
            participation = participationRepository.save(participation);
            // Create Event for User join Quest
            webSocketController.onUserJoinQuest(foundQuest, principal, user);
        }
		return participation;
	}
}

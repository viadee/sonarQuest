package com.viadee.sonarquest.services;

import com.viadee.sonarquest.controllers.WebSocketController;
import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.repositories.ParticipationRepository;
import com.viadee.sonarquest.repositories.QuestRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ParticipationService {

    private final ParticipationRepository participationRepository;

    private final UserService userService;

    private final QuestService questService;
    
    private final WebSocketController webSocketController;

    public ParticipationService(ParticipationRepository participationRepository, UserService userService, QuestService questService, WebSocketController webSocketController) {
        this.participationRepository = participationRepository;
        this.userService = userService;
        this.questService = questService;
        this.webSocketController = webSocketController;
    }

    @Transactional
    public Participation findParticipationByQuestIdAndUserId(final Long questId, final Long userId) {
        final Quest quest = questService.findById(questId);
        final User user = userService.findById(userId);
        return participationRepository.findByQuestAndUser(quest, user);
    }

    @Transactional
    public List<Participation> findParticipationByQuestId(final Long questId) {
        final Quest foundQuest = questService.findById(questId);
        return participationRepository.findByQuest(foundQuest);
    }

    @Transactional
    public List<Participation> findParticipationByUser(final User user) {
        List<Participation> participations = new ArrayList<>();
        if (user != null) {
            participations = participationRepository.findByUser(user);
        }
        return participations;
    }

    @Transactional
	public Participation createParticipation(Principal principal, Long questId) {
		final Quest foundQuest = questService.findById(questId);
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

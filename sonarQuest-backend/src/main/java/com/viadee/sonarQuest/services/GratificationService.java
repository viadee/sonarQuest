package com.viadee.sonarQuest.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.constants.SkillType;
import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Artefact;
import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.Skill;
import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.interfaces.UserGratification;

@Service
public class GratificationService implements UserGratification {

    @Autowired
    private UserService userService;

    @Autowired
    private LevelService levelService;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GratificationService.class);

    @Override
    public void rewardUserForSolvingTask(final Task task) {
        final Participation participation = task.getParticipation();
        if (participation != null) {
            final User user = participation.getUser();
            LOGGER.info(String.format("Task %s solved - Rewarding userID %s with %s gold and %s xp", task.getKey(), user.getId(), task.getGold(), task.getXp()));
            user.addXp(task.getXp());
            user.addGold(task.getGold());
            addSkillReward(user);
            user.setLevel(levelService.getLevelByUserXp(user.getXp()));
            userService.save(user);
        }
    }

    private void rewardUserForSolvingAdventure(final User user, final Adventure adventure) {
    	LOGGER.info(String.format("Adventure %s solved - Rewarding userID %s with %s gold and %s xp", adventure.getId(), user.getId(), adventure.getGold(), adventure.getXp()));
        user.addGold(adventure.getGold());
        user.addXp(adventure.getXp());
        user.setLevel(levelService.getLevelByUserXp(user.getXp()));
        userService.save(user);
    }

    @Override
    public void rewardUsersForSolvingQuest(final Quest quest) {
        final List<Participation> participations = quest.getParticipations();
        //First, find all Users only once that participated
        Set<User> usersThatTookPart = new HashSet<>();
        for (Participation participation : participations) {
        	usersThatTookPart.add(participation.getUser());
		}
        LOGGER.info((String.format("A Quest has been solved by the following user IDs: %s and now the quest reward will be paid out to everyone.", usersThatTookPart)));
        //Now, reward them 
        for (User user : usersThatTookPart) {
        	rewardQuestParticipation(quest, user);
        }
    }

    @Override
    public void rewardUsersForSolvingAdventure(final Adventure adventure) {
        final List<User> users = adventure.getUsers();
        users.forEach(user -> rewardUserForSolvingAdventure(user, adventure));
    }

    private void rewardQuestParticipation(Quest quest, User user) {
        LOGGER.info(String.format("Rewarding participation in solving quest %s - Rewarding userID %s with %s gold and %s xp", quest.getId(), user.getId(), quest.getGold(), quest.getXp()));
        user.addGold(quest.getGold());
        user.addXp(quest.getXp());
        user.setLevel(levelService.getLevelByUserXp(user.getXp()));
        userService.save(user);
    }

    private User addSkillReward(final User user) {
        final User rewardedUser = user;
        final List<Skill> avatarClassSkills = rewardedUser.getAvatarClass().getSkills();
        final List<Skill> artefactSkills = rewardedUser.getArtefacts().stream()
                .map(Artefact::getSkills)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        final List<Skill> totalSkills = new ArrayList<>();
        totalSkills.addAll(avatarClassSkills);
        totalSkills.addAll(artefactSkills);
        final Long extraGold = totalSkills.stream()
                .filter(skill -> skill.getType().equals(SkillType.GOLD))
                .mapToLong(Skill::getValue)
                .sum();
        final Long extraXP = totalSkills.stream()
                .filter(skill -> skill.getType().equals(SkillType.XP))
                .mapToLong(Skill::getValue)
                .sum();
        LOGGER.info(String.format("Adding skill rewards to user ID %s - extra gold %s and extra xp %s", user.getId(), extraGold, extraXP));
        rewardedUser.addGold(extraGold);
        rewardedUser.addXp(extraXP);
        return rewardedUser;
    }

}

package com.viadee.sonarQuest.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public void rewardUserForSolvingTask(final Task task) {
        final Participation participation = task.getParticipation();
        if (participation != null) {
            final User user = participation.getUser();
            user.addXp(task.getXp());
            user.addGold(task.getGold());
            addSkillReward(user);
            user.setLevel(levelService.getLevelByUserXp(user.getXp()));
        }
    }

    @Override
    public void rewardUsersForSolvingQuest(final Quest quest) {
        final List<Participation> participations = quest.getParticipations();
        participations.forEach(this::rewardParticipation);
    }

    @Override
    public void rewardUsersForSolvingAdventure(final Adventure adventure) {
        final List<User> users = adventure.getUsers();
        users.forEach(user -> rewardUserForSolvingAdventure(user, adventure));

    }

    private void rewardUserForSolvingAdventure(final User user, final Adventure adventure) {
        user.addGold(adventure.getGold());
        user.addXp(adventure.getXp());
        user.setLevel(levelService.getLevelByUserXp(user.getXp()));
        userService.save(user);
    }

    private void rewardParticipation(final Participation participation) {
        final User user = participation.getUser();
        final Quest quest = participation.getQuest();
        user.addGold(quest.getGold());
        user.addXp(quest.getXp());
        user.setLevel(levelService.getLevelByUserXp(user.getXp()));
        userService.save(user);
    }

    private User addSkillReward(final User user) {
        final User rewardedUser = user;
        final List<Skill> avatarClassSkills = rewardedUser.getAvatarClass().getSkills();
        final List<Skill> artefactSkills = rewardedUser.getArtefacts().stream()
                .map(Artefact::getSkills).flatMap(Collection::stream).collect(Collectors.toList());
        final List<Skill> totalSkills = new ArrayList<>();
        totalSkills.addAll(avatarClassSkills);
        totalSkills.addAll(artefactSkills);
        final Long extraGold = totalSkills.stream().filter(skill -> skill.getType().equals(SkillType.GOLD))
                .mapToLong(Skill::getValue).sum();
        final Long extraXP = totalSkills.stream().filter(skill -> skill.getType().equals(SkillType.XP))
                .mapToLong(Skill::getValue).sum();
        rewardedUser.addGold(extraGold);
        rewardedUser.addXp(extraXP);
        return rewardedUser;
    }

}

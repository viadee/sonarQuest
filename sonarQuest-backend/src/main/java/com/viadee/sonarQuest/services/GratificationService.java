package com.viadee.sonarQuest.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.viadee.sonarQuest.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.constants.SkillType;
import com.viadee.sonarQuest.interfaces.DeveloperGratification;
import com.viadee.sonarQuest.repositories.DeveloperRepository;

@Service
public class GratificationService implements DeveloperGratification {

    @Autowired
    private DeveloperRepository developerRepository;

    @Autowired
    private LevelService levelService;

    @Override
    public void rewardDeveloperForSolvingTask(final Task task) {
        final Participation participation = task.getParticipation();
        if (participation != null) {
            Developer developer = participation.getDeveloper();
            developer.addXp(task.getXp());
            developer.addGold(task.getGold());
            developer = addSkillReward(developer, task);
            developer.setLevel(levelService.getLevelByDeveloperXp(developer.getXp()));
            developerRepository.save(developer);
        }
    }

    @Override
    public void rewardDevelopersForSolvingQuest(final Quest quest) {
        final List<Participation> participations = quest.getParticipations();
        participations.forEach(this::rewardParticipation);
    }

    @Override
    public void rewardDevelopersForSolvingAdventure(final Adventure adventure) {
        final List<Developer> developers = adventure.getDevelopers();
        developers.forEach(developer -> rewardDeveloperForSolvingAdventure(developer, adventure));

    }

    private void rewardDeveloperForSolvingAdventure(final Developer developer, final Adventure adventure) {
        developer.addGold(adventure.getGold());
        developer.addXp(adventure.getXp());
        developer.setLevel(levelService.getLevelByDeveloperXp(developer.getXp()));
        developerRepository.save(developer);
    }

    private void rewardParticipation(final Participation participation) {
        final Developer developer = participation.getDeveloper();
        final Quest quest = participation.getQuest();
        developer.addGold(quest.getGold());
        developer.addXp(quest.getXp());
        developer.setLevel(levelService.getLevelByDeveloperXp(developer.getXp()));
        developerRepository.save(developer);
    }

    private Developer addSkillReward(final Developer developer, final Task task) {
        final Developer rewardedDeveloper = developer;
        final List<Skill> avatarClassSkills = rewardedDeveloper.getAvatarClass().getSkills();
        final List<Skill> artefactSkills = rewardedDeveloper.getArtefacts().stream()
                .map(Artefact::getSkills).flatMap(Collection::stream).collect(Collectors.toList());
        final List<Skill> totalSkills = new ArrayList<>();
        totalSkills.addAll(avatarClassSkills);
        totalSkills.addAll(artefactSkills);
        final Long extraGold = totalSkills.stream().filter(skill -> skill.getType().equals(SkillType.GOLD))
                .mapToLong(Skill::getValue).sum();
        final Long extraXP = totalSkills.stream().filter(skill -> skill.getType().equals(SkillType.XP))
                .mapToLong(Skill::getValue).sum();
        rewardedDeveloper.addGold(extraGold);
        rewardedDeveloper.addXp(extraXP);
        return rewardedDeveloper;
    }

}

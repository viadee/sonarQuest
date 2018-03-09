package com.viadee.sonarQuest.interfaces;

import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.Task;

public interface DeveloperGratification {

    void rewardDeveloperForSolvingTask(Task task);

    void rewardDevelopersForSolvingQuest(Quest quest);

    void rewardDevelopersForSolvingAdventure(Adventure adventure);

}

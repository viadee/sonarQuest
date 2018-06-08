package com.viadee.sonarQuest.interfaces;

import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.Task;

public interface UserGratification {

    void rewardUserForSolvingTask(Task task);

    void rewardUsersForSolvingQuest(Quest quest);

    void rewardUsersForSolvingAdventure(Adventure adventure);

}

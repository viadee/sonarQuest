package com.viadee.sonarquest.interfaces;

import com.viadee.sonarquest.entities.Adventure;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.entities.World;

public interface UserGratification {

    void rewardUserForSolvingTask(Task task);

    void rewardUsersForSolvingQuest(Quest quest);

    void rewardUsersForSolvingAdventure(Adventure adventure);
    
    void rewardUsersForSolvingRaid(final Raid raid);
    
    void rewardUsersForPassedQualityGate(final World world, final Reward reward);

}

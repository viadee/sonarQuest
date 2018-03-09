package com.viadee.sonarQuest.interfaces;

import com.viadee.sonarQuest.entities.Task;
import com.viadee.sonarQuest.entities.World;

import java.util.List;

public interface QuestSuggestion {

    List<Task> suggestTasksWithApproxGoldAmount(World world, Long goldApprox);

    List<Task> suggestTasksWithApproxXpAmount(World world, Long xpApprox);

}

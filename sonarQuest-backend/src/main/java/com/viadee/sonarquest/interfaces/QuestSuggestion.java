package com.viadee.sonarquest.interfaces;

import java.util.List;

import com.viadee.sonarquest.entities.Task;
import com.viadee.sonarquest.entities.World;

public interface QuestSuggestion {

    List<Task> suggestTasksWithApproxGoldAmount(World world, Long goldApprox);

    List<Task> suggestTasksWithApproxXpAmount(World world, Long xpApprox);

}

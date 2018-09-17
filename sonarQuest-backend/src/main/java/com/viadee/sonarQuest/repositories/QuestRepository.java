package com.viadee.sonarQuest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.viadee.sonarQuest.constants.QuestState;
import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.World;

public interface QuestRepository extends CrudRepository<Quest, Long> {

    @Override
    List<Quest> findAll();

    List<Quest> findByAdventureAndStatus(Adventure adventure, QuestState status);

    List<Quest> findByWorldAndAdventure(World world, Adventure adventure);

    List<Quest> findByWorld(World world);
}

package com.viadee.sonarquest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.viadee.sonarquest.constants.QuestState;
import com.viadee.sonarquest.entities.Adventure;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.entities.World;

public interface QuestRepository extends CrudRepository<Quest, Long> {

    @Override
    List<Quest> findAll();

    List<Quest> findByAdventureAndStatus(Adventure adventure, QuestState status);
    
    List<Quest> findByRaidAndStatus(Raid raid, QuestState status);

    List<Quest> findByWorldAndAdventure(World world, Adventure adventure);

    List<Quest> findByWorld(World world);
}

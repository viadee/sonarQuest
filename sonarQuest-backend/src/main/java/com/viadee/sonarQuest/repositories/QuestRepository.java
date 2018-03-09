package com.viadee.sonarQuest.repositories;

import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.World;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface QuestRepository extends CrudRepository<Quest,Long> {

    List<Quest> findAll();

    List<Quest> findByAdventureAndStatus(Adventure adventure,String status);

    List<Quest> findByWorldAndAdventure(World world, Adventure adventure);

    List<Quest> findByWorld(World world);
}

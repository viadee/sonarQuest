package com.viadee.sonarQuest.repositories;

import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ParticipationRepository extends CrudRepository<Participation,Long> {

    Participation findByQuestAndDeveloper(Quest quest, Developer developer);

    List<Participation> findByDeveloper(Developer developer);
}

package com.viadee.sonarQuest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.viadee.sonarQuest.entities.Participation;
import com.viadee.sonarQuest.entities.Quest;
import com.viadee.sonarQuest.entities.User;

public interface ParticipationRepository extends CrudRepository<Participation, Long> {

    Participation findByQuestAndUser(Quest quest, User user);

    List<Participation> findByUser(User user);

    List<Participation> findByQuest(Quest foundQuest);
}

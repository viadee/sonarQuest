package com.viadee.sonarquest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.User;

public interface ParticipationRepository extends CrudRepository<Participation, Long> {

    Participation findByQuestAndUser(Quest quest, User user);

    List<Participation> findByUser(User user);

    List<Participation> findByQuest(Quest foundQuest);
}

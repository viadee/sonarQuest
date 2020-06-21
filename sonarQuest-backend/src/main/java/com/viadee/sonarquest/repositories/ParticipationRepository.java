package com.viadee.sonarquest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.viadee.sonarquest.entities.Participation;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.User;

public interface ParticipationRepository extends CrudRepository<Participation, Long> {

    Participation findByQuestAndUser(Quest quest, User user);

    List<Participation> findByUser(User user);

    List<Participation> findByQuest(Quest foundQuest);
    
    @Modifying 
    @Query(value = "DELETE FROM Participation WHERE id = :id",nativeQuery = true) 
    int deleteById(@Param("id") long id); 
}

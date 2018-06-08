package com.viadee.sonarQuest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarQuest.entities.Adventure;
import com.viadee.sonarQuest.entities.User;
import com.viadee.sonarQuest.entities.World;

public interface AdventureRepository extends JpaRepository<Adventure, Long> {

    List<Adventure> findByWorld(World world);

    List<Adventure> findByUsers(List<User> users);

    List<Adventure> findByUsersAndWorld(List<User> users, World world);
}

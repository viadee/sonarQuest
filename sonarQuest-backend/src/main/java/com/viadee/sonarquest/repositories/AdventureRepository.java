package com.viadee.sonarquest.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarquest.entities.Adventure;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;

public interface AdventureRepository extends JpaRepository<Adventure, Long> {

    List<Adventure> findByWorld(World world);

    List<Adventure> findByUsersAndWorld(List<User> users, World world);
}

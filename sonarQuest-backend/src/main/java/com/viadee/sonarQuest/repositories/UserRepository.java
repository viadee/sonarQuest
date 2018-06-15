package com.viadee.sonarQuest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarQuest.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}

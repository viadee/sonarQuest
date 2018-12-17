package com.viadee.sonarquest.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.viadee.sonarquest.entities.User;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);

}

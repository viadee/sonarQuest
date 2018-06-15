package com.viadee.sonarQuest.repositories;

import org.springframework.data.repository.CrudRepository;

import com.viadee.sonarQuest.entities.UiDesign;
import com.viadee.sonarQuest.entities.User;

public interface UiDesignRepository extends CrudRepository<UiDesign, Long> {

    UiDesign findByUser(User user);

}

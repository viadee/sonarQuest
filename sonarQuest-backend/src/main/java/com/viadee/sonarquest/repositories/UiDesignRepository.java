package com.viadee.sonarquest.repositories;

import org.springframework.data.repository.CrudRepository;

import com.viadee.sonarquest.entities.UiDesign;
import com.viadee.sonarquest.entities.User;

public interface UiDesignRepository extends CrudRepository<UiDesign, Long> {

    UiDesign findByUser(User user);

}

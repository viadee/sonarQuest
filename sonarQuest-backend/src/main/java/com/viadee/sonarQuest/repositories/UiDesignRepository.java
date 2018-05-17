package com.viadee.sonarQuest.repositories;


import org.springframework.data.repository.CrudRepository;

import com.viadee.sonarQuest.entities.Developer;
import com.viadee.sonarQuest.entities.UiDesign;

public interface UiDesignRepository extends CrudRepository<UiDesign,Long>  {
	
	UiDesign findByDeveloper(Developer developer);

}

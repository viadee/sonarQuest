package com.viadee.sonarQuest.repositories;

import com.viadee.sonarQuest.entities.AvatarClass;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AvatarClassRepository extends CrudRepository<AvatarClass,Long> {

       List<AvatarClass> findAll();
}

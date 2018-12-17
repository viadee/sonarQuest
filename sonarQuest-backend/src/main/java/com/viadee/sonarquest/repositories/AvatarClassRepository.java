package com.viadee.sonarquest.repositories;

import org.springframework.data.repository.CrudRepository;

import com.viadee.sonarquest.entities.AvatarClass;

import java.util.List;

public interface AvatarClassRepository extends CrudRepository<AvatarClass,Long> {

       List<AvatarClass> findAll();
       
       AvatarClass findByName(String name);
}

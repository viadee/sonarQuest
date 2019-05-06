package com.viadee.sonarquest.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.viadee.sonarquest.entities.Event;
import com.viadee.sonarquest.entities.World;

public interface EventRepository extends CrudRepository<Event,Long>  {

	@Override
	List<Event> findAll();
	
	List<Event> findByWorld(World world);
	
	List<Event> findByWorldOrWorldIsNull(World world);

	List<Event> findFirst1ByOrderByTimestampDesc();
	
	List<Event> findFirst2ByOrderByTimestampDesc();
	
	List<Event> findFirst3ByOrderByTimestampDesc();
	
	List<Event> findFirst10ByOrderByTimestampDesc();
}


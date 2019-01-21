package com.viadee.sonarquest.controllers;


import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.Event;
import com.viadee.sonarquest.services.EventService;

@RestController
@RequestMapping("/event")
public class EventController {
	protected static final Log LOGGER = LogFactory.getLog(EventController.class);
	
	@Autowired
	private EventService eventService;
	
	@RequestMapping(method = RequestMethod.GET)
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

	
	@CrossOrigin
    @RequestMapping(value = "/world/{worldId}", method = RequestMethod.GET)
    public List<Event> getEventsForWorld(@PathVariable(value = "worldId") final Long worldId) {
		return eventService.getEventsForWorld(worldId);
    }
	
	

    @CrossOrigin
    @RequestMapping(value = "/world/{worldId}/sendChat/{developerId}", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Event sendChat(@PathVariable(value = "worldId") final Long worldId, @PathVariable(value = "developerId") final Long developerId, @RequestBody String message) {
    	return eventService.newMessage(worldId, message, developerId);
    }
    
    
}

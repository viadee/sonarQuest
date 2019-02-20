package com.viadee.sonarquest.controllers;


import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
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

import com.viadee.sonarquest.constants.EventType;
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
        List<Event> events = new ArrayList<>();
        
        Iterator<Event> i = eventService.getAllEvents().iterator();
        while (i.hasNext()) {
        	Event e = i.next();
        	e.getType();
			if (e.getType() == EventType.MESSAGE) {
        		e.setImage(e.getUser().getPicture());
        	}
			events.add(e);
        }
        
        return events;
    }

	
	@CrossOrigin
    @RequestMapping(value = "/world/{worldId}", method = RequestMethod.GET)
    public List<Event> getEventsForWorld(@PathVariable(value = "worldId") final Long worldId) {
        return eventService.getEventsForWorld(worldId);
    }
	
	

    @CrossOrigin
    @RequestMapping(value = "/world/{worldId}/sendChat", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Event sendChat(final Principal principal, @PathVariable(value = "worldId") final Long worldId, @RequestBody String message) {
    	return eventService.newMessage(worldId, message, principal);
    }
    
    
}

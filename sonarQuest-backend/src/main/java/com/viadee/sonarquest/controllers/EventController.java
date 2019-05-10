package com.viadee.sonarquest.controllers;

import java.security.Principal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.Event;
import com.viadee.sonarquest.entities.EventUserDto;
import com.viadee.sonarquest.services.EventService;

@RestController
@RequestMapping("/event")
public class EventController {
	
    @Autowired
    private EventService eventService;
    
    protected static final Log LOGGER = LogFactory.getLog(EventController.class);

    @GetMapping
    public List<Event> getAllEvents() {
        return eventService.getAllEvents();
    }

    @CrossOrigin
    @GetMapping(value = "/currentWorld")
    public List<Event> getEventsForCurrentWorld(final Principal principal) {
        return eventService.getEventsForWorld(principal);
    }

    
    @CrossOrigin
    @GetMapping(value = "/getEventsForCurrentWorldEfficient")
    public EventUserDto getEventsForCurrentWorldEfficient(final Principal principal) {
    	return this.eventService.principalToEvents(principal);	
    }
    

    @CrossOrigin
    @PostMapping(value = "/sendChat")
    @ResponseStatus(HttpStatus.CREATED)
    public Event sendChat(final Principal principal, @RequestBody String message) {
        return eventService.createEventForNewMessage(message, principal);
    }

    @CrossOrigin
    @PostMapping(value = "/something")
    @ResponseStatus(HttpStatus.CREATED)
    public String something(final Principal principal, @RequestBody String message) {
        LOGGER.info("Something()");
        return "Server";
    }

}

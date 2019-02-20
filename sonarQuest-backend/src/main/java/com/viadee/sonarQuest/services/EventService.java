package com.viadee.sonarquest.services;

import java.security.Principal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.constants.EventType;
import com.viadee.sonarquest.entities.Adventure;
import com.viadee.sonarquest.entities.Event;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.EventRepository;
import com.viadee.sonarquest.repositories.WorldRepository;

@Service
public class EventService {
	
	protected static final Log LOGGER = LogFactory.getLog(EventService.class);
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private WorldRepository worldRepository;
	
	@Autowired
	private UserService userService;
	
	
	
    public void createEventForSolveQuest(Quest quest, Principal principal) {
    	final String username = principal.getName();
		User user = userService.findByUsername(username);
    	EventType type  = EventType.QUEST;
		String title = quest.getTitle();
		String story = quest.getStory();
		String status = quest.getStatus().toString();
		String image = quest.getImage();
		World world = quest.getWorld();
		String head = "";

		LOGGER.info("New Event because of an Quest");
		
		
		
		// create event
		eventRepository.save(new Event(type, title, story, status, image, world, head, user));
		
	}
    
    public void createEvent(Adventure adventure, User user) {
    	EventType type  = EventType.ADVENTURE;
		String title = adventure.getTitle();
		String story = adventure.getStory();
		String status = adventure.getStatus().toString();
		String image = "";
		World world = adventure.getWorld();
		String head = "";

		LOGGER.info("New Event because of an Adventure");
		
		// create event
		eventRepository.save(new Event(type, title, story, status, image, world, head, user));
		
	}
	
	public List<Event> getAllEvents(){
		return eventRepository.findAll();
	}

	public Event newMessage(String message, Principal principal) {
		final String username = principal.getName();
		User user = userService.findByUsername(username);
		EventType type  = EventType.MESSAGE;
		String story = message;
		World world  = user.getCurrentWorld();
		
		// create event
		return eventRepository.save(new Event(type, story, world, user));
		
	}
	
	public void createEventForCreateQuest(Quest questDto) {
		EventType type  = EventType.QUEST;
		String title = questDto.getTitle();
		String story = questDto.getStory();
		String status = questDto.getStatus().toString();
		String image = questDto.getImage();
		World world = questDto.getWorld();
		String head = "Es wurde eine neue Quest hinzugefügt";

		LOGGER.info("New Event because of an created Quest");
		
		// create event
		eventRepository.save(new Event(type, title, story, status, image, world, head));
		
		
	}

	public List<Event> getEventsForWorld(Principal principal) {
		final String username = principal.getName();
		User user = userService.findByUsername(username);
		World world = user.getCurrentWorld();
        List<Event> events =  eventRepository.findByWorld(world);        
        return events;
	}

	public Event storeEvent(Event event) {
		return eventRepository.save(event);
	}
	


	public List<Event> getLatestEvent() {
		return eventRepository.findFirst1ByOrderByTimestampDesc();
	}
	
	public List<Event> get2LatestEvents() {
		return eventRepository.findFirst2ByOrderByTimestampDesc();
	}
	public List<Event> get3LatestEvents() {
		return eventRepository.findFirst3ByOrderByTimestampDesc();
	}
	
	public List<Event> get10LatestEvents() {
		return eventRepository.findFirst10ByOrderByTimestampDesc();
	}

	

	
	

	
}

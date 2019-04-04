package com.viadee.sonarquest.services;

import java.security.Principal;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.constants.EventState;
import com.viadee.sonarquest.constants.EventType;
import com.viadee.sonarquest.entities.Adventure;
import com.viadee.sonarquest.entities.Artefact;
import com.viadee.sonarquest.entities.Event;
import com.viadee.sonarquest.entities.MessageDto;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.EventRepository;

@Service
public class EventService {
	
	protected static final Log LOGGER = LogFactory.getLog(EventService.class);
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private UserService userService;
	
	
	public List<Event> getAllEvents(){
		return eventRepository.findAll();
	}

	public List<Event> getEventsForWorld(Principal principal) {
		User user = userService.getUser(principal);
		World currentWorld = user.getCurrentWorld();
        List<Event> events =  eventRepository.findByWorld(currentWorld);
                
        return events;
	}

    public void createEventForSolveQuest(Quest quest, Principal principal) {
    	EventType type  = EventType.QUEST;
		String title = quest.getTitle();
		String story = quest.getStory();
		EventState state = EventState.SOLVED;
		String image = quest.getImage();
		World world = quest.getWorld();
		String head = "";

		LOGGER.info("New Event because of a solved Quest");
		eventRepository.save(new Event(type, title, story, state, image, world, head, userService.getUser(principal)));
	}
    
    public void createEventForSolveAdventure(Adventure adventure, Principal principal) {
    	EventType type  = EventType.QUEST;
		String title = adventure.getTitle();
		String story = adventure.getStory();
		EventState state = EventState.SOLVED;
		String image = "";
		World world = adventure.getWorld();
		String head = "";

		LOGGER.info("New Event because of a solved Adventure");
		eventRepository.save(new Event(type, title, story, state, image, world, head, userService.getUser(principal)));
	}
    
    public void createEventForCreateAdventure(Adventure adventure, User user) {
    	EventType type  = EventType.ADVENTURE;
		String title = adventure.getTitle();
		String story = adventure.getStory();
		EventState state = EventState.CREATED;
		String image = "";
		World world = adventure.getWorld();
		String head = "";

		LOGGER.info("New Event because of an created Adventure");
		eventRepository.save(new Event(type, title, story, state, image, world, head, user));
	}
    
    public void createEventForCreateQuest(Quest questDto) {
		EventType type  = EventType.QUEST;
		String title = questDto.getTitle();
		String story = questDto.getStory();
		EventState state = EventState.CREATED;
		String image = questDto.getImage();
		World world = questDto.getWorld();
		String head = "";

		LOGGER.info("New Event because of a created Quest");
		
		// create event
		eventRepository.save(new Event(type, title, story, state, image, world, head));
		
		
	}

	public Event createEventForNewMessage(String message, Principal principal) {
		User user = userService.getUser(principal);
		EventType type  = EventType.MESSAGE;
		String story 	= message;
		World world  	= user.getCurrentWorld();
		
		return eventRepository.save(new Event(type, story, world, user));
	}

	public Event createEventForNewMessage(MessageDto messageDto) {
		User user = userService.findById(messageDto.getUserId());
		EventType type  = EventType.MESSAGE;
		String story 	= messageDto.getMessage();
		World world  	= user.getCurrentWorld();
		
		return eventRepository.save(new Event(type, story, world, user));
	}
	
	
	public void createEventForUserJoinQuest(Quest quest, User user) {
		EventType type  = EventType.QUEST;
		String title = quest.getTitle();
		String story = quest.getStory();
		EventState state = EventState.NEW_MEMBER;
		String image = quest.getImage();
		World world = quest.getWorld();
		String head = "";

		LOGGER.info("New Event because UserId " + user.getId() + " joined Quest " + quest.getId());
		
		// create event
		eventRepository.save(new Event(type, title, story, state, image, world, head, user));
	}
	
	
	public void createEventForCreateArtefact(Artefact artefact) {
		EventType type  = EventType.ARTEFACT;
		String title = artefact.getName();
		String story = artefact.getDescription();
		EventState state = EventState.CREATED;
		String image = artefact.getIcon();

		LOGGER.info("New Event because of a created artefact");
		
		// create event
		eventRepository.save(new Event(type, title, story, state, image));
		
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

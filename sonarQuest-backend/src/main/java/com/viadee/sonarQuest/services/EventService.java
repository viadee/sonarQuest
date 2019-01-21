package com.viadee.sonarquest.services;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.constants.AdventureState;
import com.viadee.sonarquest.constants.QuestState;
import com.viadee.sonarquest.entities.Adventure;
import com.viadee.sonarquest.entities.Event;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.EventRepository;
import com.viadee.sonarquest.repositories.UserRepository;
import com.viadee.sonarquest.repositories.WorldRepository;

@Service
public class EventService {
	
	protected static final Log LOGGER = LogFactory.getLog(EventService.class);
	
	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private WorldRepository worldRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
    public void createEvent(Quest quest) {
		String type  = "Quest";
		String title = quest.getTitle();
		String story = quest.getStory();
		QuestState status = quest.getStatus();
		String image = quest.getImage();
		World world = quest.getWorld();
		String head = "";

		LOGGER.info("New Event");
		
		
		
		// create event
		eventRepository.save(new Event(type, title, story, status, image, world, head));
		
	}
    
    public void createEvent(Adventure adventure) {
		String type  = "Adventure";
		String title = adventure.getTitle();
		String story = adventure.getStory();
		AdventureState status = adventure.getStatus();
		World world = adventure.getWorld();
		String head = "";

		LOGGER.info("New Adventure");
		
		
		
		// create event
		eventRepository.save(new Event(type, title, story, status, world, head));
		
	}
	
	public List<Event> getAllEvents(){
		return eventRepository.findAll();
	}

	public Event newMessage(Long worldId, String message, Long userId) {
		String type  = "Message";
		String story = message;
		//World world  = worldRepository.findOne(worldId);
		World world  = worldRepository.findFirst1By();
		User user = userRepository.findOne(userId);
		
		// create event
		return eventRepository.save(new Event(type, story, world, user));
		
	}

	public List<Event> getEventsForWorld(Long worldId) {
		//World world = worldRepository.findOne(worldId);
        //List<Event> events =  eventRepository.findByWorld(world);
        List<Event> events =  eventRepository.findAll();
        /*for(int i = 0; i < events.size(); i++) {
        	if (events.get(i).getUser()==null) {
        		User user = new User();
        		user.setUsername("SonarQuest System");
        		events.get(i).setUser(user);
        	}
        }*/
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

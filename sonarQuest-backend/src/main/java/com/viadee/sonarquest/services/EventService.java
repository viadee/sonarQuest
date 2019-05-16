package com.viadee.sonarquest.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarquest.constants.EventState;
import com.viadee.sonarquest.constants.EventType;
import com.viadee.sonarquest.entities.Adventure;
import com.viadee.sonarquest.entities.Artefact;
import com.viadee.sonarquest.entities.Event;
import com.viadee.sonarquest.entities.EventDto;
import com.viadee.sonarquest.entities.EventUserDto;
import com.viadee.sonarquest.entities.MessageDto;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.entities.User;
import com.viadee.sonarquest.entities.UserDto;
import com.viadee.sonarquest.entities.World;
import com.viadee.sonarquest.repositories.EventRepository;

@Service
public class EventService {

	private static final Log LOGGER = LogFactory.getLog(EventService.class);

	private static final int EVENT_STORY_MAX_LENGTH = 255;

	@Autowired
	private EventRepository eventRepository;

	@Autowired
	private UserService userService;

	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}

    public List<Event> getEventsForWorld(Principal principal) {
        User user = userService.getUser(principal);
        World currentWorld = user.getCurrentWorld();
        return eventRepository.findByWorldOrWorldIsNull(currentWorld);
    }

    public Event createEventForSolvedAdventure(Adventure adventure, Principal principal) {
    	Event event = adventureToEvent(adventure, principal, EventState.SOLVED);
        LOGGER.info(String.format("New event because of a solved adventure '%s'", adventure.getTitle()));
        return checkStoryAndSave(event);
    }

    public Event createEventForDeletedAdventure(Adventure adventure, Principal principal) {
    	Event event = adventureToEvent(adventure, principal, EventState.DELETED);
        LOGGER.info(String.format("New event because of deleted adventure '%s'", adventure.getTitle()));
        return checkStoryAndSave(event);
    }

    public Event createEventForCreatedAdventure(Adventure adventure, Principal principal) {
    	Event event = adventureToEvent(adventure, principal, EventState.CREATED);
        LOGGER.info(String.format("New event because of a newly created adventure '%s'", adventure.getTitle()));
        return checkStoryAndSave(event);
    }
    private Event adventureToEvent(Adventure adventure, Principal principal, EventState state) {
    	 EventType type = EventType.ADVENTURE;
         String title = adventure.getTitle();
         String story = adventure.getStory();
         String image = StringUtils.EMPTY;
         World world = adventure.getWorld();
         String head = StringUtils.EMPTY;
         return new Event(type, title, story, state, image, world, head, userService.getUser(principal));
    }

    public Event createEventForSolvedQuest(Quest quest, Principal principal) {
    	Event event = questToEvent(quest, principal, EventState.SOLVED);
        LOGGER.info(String.format("New event because of a solved quest '%s'", quest.getTitle()));
        return checkStoryAndSave(event);
    }

    public Event createEventForCreatedQuest(Quest quest, Principal principal) {
    	Event event = questToEvent(quest, principal, EventState.CREATED);
        LOGGER.info(String.format("New event because of a newly created quest '%s'", quest.getTitle()));
        return checkStoryAndSave(event);
    }
	public Event createEventForDeletedQuest(Quest quest, Principal principal) {
    	Event event = questToEvent(quest, principal, EventState.DELETED);
        LOGGER.info(String.format("New event because of a deleted quest '%s'", quest.getTitle()));
        return checkStoryAndSave(event);
	}
	public Event createEventForUpdatedQuest(Quest quest, Principal principal) {
    	Event event = questToEvent(quest, principal, EventState.UPDATED);
        LOGGER.info(String.format("New event because of a updated quest '%s'", quest.getTitle()));
        return checkStoryAndSave(event);
	}
    public Event createEventForUserJoinQuest(Quest quest, Principal principal, User user) { 
    	Event event = questToEvent(quest, principal, EventState.NEW_MEMBER);
        LOGGER.info("New Event because UserId " + user.getId() + " joined Quest " + quest.getId());
        return checkStoryAndSave(event);
    }
	private Event questToEvent(Quest quest, Principal principal, EventState state) {
		EventType type = EventType.QUEST;
        String title = quest.getTitle();
        String story = quest.getStory();
        String image = quest.getImage();
        World world = quest.getWorld();
        String head = StringUtils.EMPTY;
        return new Event(type, title, story, state, image, world, head, userService.getUser(principal));
	}
    public Event createEventForCreatedArtefact(Artefact artefact, Principal principal) {
    	Event event = artefactToEvent(artefact, principal, EventState.CREATED);
        LOGGER.info(String.format("New Event because of a created artefact '%s'", artefact.getName()));
        return checkStoryAndSave(event);
    }

    public Event createEventForDeletedArtefact(Artefact artefact, Principal principal) {
    	Event event = artefactToEvent(artefact, principal, EventState.DELETED);
        LOGGER.info(String.format("New Event because of a deleted artefact '%s'", artefact.getName()));
        return checkStoryAndSave(event);
    }

	public Event createEventForUpdatedArtefact(Artefact artefact, Principal principal) {
    	Event event = artefactToEvent(artefact, principal, EventState.UPDATED);
        LOGGER.info(String.format("New Event because of an updated artefact '%s'", artefact.getName()));
        return checkStoryAndSave(event);
	}
    
    private Event artefactToEvent(Artefact artefact, Principal principal, EventState state) {
    	EventType type = EventType.ARTEFACT;
        String title = artefact.getName();
        String story = artefact.getDescription();
        String image = artefact.getIcon();
        return new Event(type, title, story, state, image, userService.getUser(principal));
    }

	public Event createEventForNewMessage(String message, Principal principal) {
		User user = userService.getUser(principal);
		EventType type = EventType.MESSAGE;
		String story = message;
		World world = user.getCurrentWorld();
		return checkStoryAndSave(new Event(type, story, world, user));
	}

	public Event createEventForNewMessage(MessageDto messageDto) {
		User user = userService.findById(messageDto.getUserId());
		EventType type = EventType.MESSAGE;
		String story = messageDto.getMessage();
		World world = user.getCurrentWorld();

        return checkStoryAndSave(new Event(type, story, world, user));
	}

	public EventUserDto eventToEventUserDto(Event event) {
		List<EventDto> eventDtos = new ArrayList<>();
		List<UserDto> userDtos = new ArrayList<>();

		eventDtos.add(new EventDto(event));

		if (event.getUser() != null) {
			UserDto userDto = new UserDto(event.getUser());
			if (!hasUserDto(userDtos, userDto)) {
				userDtos.add(userDto);
			}
		}

		return new EventUserDto(userDtos, eventDtos);
	}

	public EventUserDto eventsToEventUserDto(List<Event> events) {
		List<EventDto> eventDtos = new ArrayList<>();
		List<UserDto> userDtos = new ArrayList<>();

		events.forEach(event -> {

			EventUserDto eventUserDto = eventToEventUserDto(event);
			if (!eventUserDto.getEventDtos().isEmpty() && eventDtos.stream()
					.noneMatch(dto -> dto.getId().equals(eventUserDto.getEventDtos().get(0).getId()))) {
				eventDtos.add(eventUserDto.getEventDtos().get(0));
			}

			if (!eventUserDto.getUserDtos().isEmpty() && userDtos.stream()
					.noneMatch(dto -> dto.getId().equals(eventUserDto.getUserDtos().get(0).getId()))) {
				userDtos.add(eventUserDto.getUserDtos().get(0));
			}

		});
		return new EventUserDto(userDtos, eventDtos);
	}

	public EventUserDto principalToEvents(Principal principal) {
		List<Event> events = getEventsForWorld(principal);
		return eventsToEventUserDto(events);
	}

	public Boolean hasUserDto(List<UserDto> userDtos, UserDto userDto) {
		Iterator<UserDto> i = userDtos.iterator();

		while (i.hasNext()) {
			UserDto dto = i.next();
			if (dto.getId().equals(userDto.getId())) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Since the "story" field of events may be shorter then the story of external
	 * events, it is cut to "story..." in case it exceeds the max length.
	 */
	private Event checkStoryAndSave(Event event) {
		String story = event.getStory();
		String cutStory = StringUtils.abbreviate(story, EVENT_STORY_MAX_LENGTH);
		event.setStory(cutStory);
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

	public boolean checkForUnseenEvents(String username) {
		User user = userService.findByUsername(username);
		if (user.getLastTavernVisit() != null) {
			List<Event> unseenEvents = eventRepository.findAllWithTimestampAfter(user.getLastTavernVisit());
			if(!unseenEvents.isEmpty()) {
				return true;
			}
		}
		return false;
	}

}

package com.viadee.sonarquest.services;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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

	private final EventRepository eventRepository;

	private final UserService userService;

	public EventService(final EventRepository eventRepository, final UserService userService) {
		this.eventRepository = eventRepository;
		this.userService = userService;
	}

	public List<Event> getAllEvents() {
		return eventRepository.findAll();
	}

    public List<Event> getEventsForWorld(final Principal principal) {
        final User user = userService.findByUsername(principal.getName());
        final World currentWorld = user.getCurrentWorld();
        return eventRepository.findByWorldOrWorldIsNull(currentWorld);
    }

    public Event createEventForSolvedAdventure(final Adventure adventure, final Principal principal) {
    	final Event event = adventureToEvent(adventure, principal, EventState.SOLVED);
        LOGGER.info(String.format("New event because of a solved adventure '%s'", adventure.getTitle()));
        return checkStoryAndSave(event);
    }

    public Event createEventForDeletedAdventure(final Adventure adventure, final Principal principal) {
    	final Event event = adventureToEvent(adventure, principal, EventState.DELETED);
        LOGGER.info(String.format("New event because of deleted adventure '%s'", adventure.getTitle()));
        return checkStoryAndSave(event);
    }

    public Event createEventForCreatedAdventure(final Adventure adventure, final Principal principal) {
    	final Event event = adventureToEvent(adventure, principal, EventState.CREATED);
        LOGGER.info(String.format("New event because of a newly created adventure '%s'", adventure.getTitle()));
        return checkStoryAndSave(event);
    }
    private Event adventureToEvent(final Adventure adventure, final Principal principal, final EventState state) {
    	 final EventType type = EventType.ADVENTURE;
         final String title = adventure.getTitle();
         final String story = adventure.getStory();
         final String image = StringUtils.EMPTY;
         final World world = adventure.getWorld();
         final String head = StringUtils.EMPTY;
         final User user = userService.findByUsername(principal.getName());
         return new Event(type, title, story, state, image, world, head, user);
    }

    public Event createEventForSolvedQuest(final Quest quest, final Principal principal) {
    	final Event event = questToEvent(quest, principal, EventState.SOLVED);
        LOGGER.info(String.format("New event because of a solved quest '%s'", quest.getTitle()));
        return checkStoryAndSave(event);
    }

    public Event createEventForCreatedQuest(final Quest quest, final Principal principal) {
    	final Event event = questToEvent(quest, principal, EventState.CREATED);
        LOGGER.info(String.format("New event because of a newly created quest '%s'", quest.getTitle()));
        return checkStoryAndSave(event);
    }
	public Event createEventForDeletedQuest(final Quest quest, final Principal principal) {
    	final Event event = questToEvent(quest, principal, EventState.DELETED);
        LOGGER.info(String.format("New event because of a deleted quest '%s'", quest.getTitle()));
        return checkStoryAndSave(event);
	}
	public Event createEventForUpdatedQuest(final Quest quest, final Principal principal) {
    	final Event event = questToEvent(quest, principal, EventState.UPDATED);
        LOGGER.info(String.format("New event because of a updated quest '%s'", quest.getTitle()));
        return checkStoryAndSave(event);
	}
    public Event createEventForUserJoinQuest(final Quest quest, final Principal principal, final User user) {
    	final Event event = questToEvent(quest, principal, EventState.NEW_MEMBER);
        LOGGER.info("New Event because UserId " + user.getId() + " joined Quest " + quest.getId());
        return checkStoryAndSave(event);
    }
	private Event questToEvent(final Quest quest, final Principal principal, final EventState state) {
		final EventType type = EventType.QUEST;
        final String title = quest.getTitle();
        final String story = quest.getStory();
        final String image = quest.getImage();
        final World world = quest.getWorld();
        final String head = StringUtils.EMPTY;
        final User user = userService.findByUsername(principal.getName());
        return new Event(type, title, story, state, image, world, head, user);
	}
    public Event createEventForCreatedArtefact(final Artefact artefact, final Principal principal) {
    	final Event event = artefactToEvent(artefact, principal, EventState.CREATED);
        LOGGER.info(String.format("New Event because of a created artefact '%s'", artefact.getName()));
        return checkStoryAndSave(event);
    }

    public Event createEventForDeletedArtefact(final Artefact artefact, final Principal principal) {
    	final Event event = artefactToEvent(artefact, principal, EventState.DELETED);
        LOGGER.info(String.format("New Event because of a deleted artefact '%s'", artefact.getName()));
        return checkStoryAndSave(event);
    }

	public Event createEventForUpdatedArtefact(final Artefact artefact, final Principal principal) {
    	final Event event = artefactToEvent(artefact, principal, EventState.UPDATED);
        LOGGER.info(String.format("New Event because of an updated artefact '%s'", artefact.getName()));
        return checkStoryAndSave(event);
	}

    private Event artefactToEvent(final Artefact artefact, final Principal principal, final EventState state) {
    	final EventType type = EventType.ARTEFACT;
        final String title = artefact.getName();
        final String story = artefact.getDescription();
        final String image = artefact.getIcon();
		final User user = userService.findByUsername(principal.getName());
        return new Event(type, title, story, state, image, user);
    }

	public Event createEventForNewMessage(final String message, final Principal principal) {
		final User user = userService.findByUsername(principal.getName());
		final EventType type = EventType.MESSAGE;
		final World world = user.getCurrentWorld();
		return checkStoryAndSave(new Event(type, message, world, user));
	}

	public Event createEventForNewMessage(final MessageDto messageDto) {
		final User user = userService.findById(messageDto.getUserId());
		final EventType type = EventType.MESSAGE;
		final String story = messageDto.getMessage();
		final World world = user.getCurrentWorld();

        return checkStoryAndSave(new Event(type, story, world, user));
	}

	public EventUserDto eventToEventUserDto(final Event event) {
		final List<EventDto> eventDtos = new ArrayList<>();
		final List<UserDto> userDtos = new ArrayList<>();

		eventDtos.add(new EventDto(event));

		if (event.getUser() != null) {
			final UserDto userDto = new UserDto(event.getUser());
			if (!hasUserDto(userDtos, userDto)) {
				userDtos.add(userDto);
			}
		}

		return new EventUserDto(userDtos, eventDtos);
	}

	public EventUserDto eventsToEventUserDto(final List<Event> events) {
		final List<EventDto> eventDtos = new ArrayList<>();
		final List<UserDto> userDtos = new ArrayList<>();

		events.forEach(event -> {

			final EventUserDto eventUserDto = eventToEventUserDto(event);
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

	public EventUserDto principalToEvents(final Principal principal) {
		final List<Event> events = getEventsForWorld(principal);
		return eventsToEventUserDto(events);
	}

	public Boolean hasUserDto(final List<UserDto> userDtos, final UserDto userDto) {

		for (final UserDto dto : userDtos) {
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
	private Event checkStoryAndSave(final Event event) {
		final String story = event.getStory();
		final String cutStory = StringUtils.abbreviate(story, EVENT_STORY_MAX_LENGTH);
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

	public boolean areUnseenEventsAvailable(final String username) {
		final User user = userService.findByUsername(username);
		if (user.getLastTavernVisit() != null) {
			final List<Event> unseenEvents = eventRepository.findAllWithTimestampAfter(user.getLastTavernVisit());
			return !unseenEvents.isEmpty();
		}
		return false;
	}

}

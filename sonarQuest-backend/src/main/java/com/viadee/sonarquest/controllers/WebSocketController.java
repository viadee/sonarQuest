package com.viadee.sonarquest.controllers;

import com.viadee.sonarquest.entities.*;
import com.viadee.sonarquest.services.EventService;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
public class WebSocketController {

    private static final String CHAT = "/chat";

    private final SimpMessagingTemplate template;
    
    private final EventService eventService;

    public WebSocketController(SimpMessagingTemplate template, EventService eventService) {
        this.template = template;
        this.eventService = eventService;
    }

    @MessageMapping("/send/message")
    public void onReceivedMessage(final MessageDto messageDto) {
        Event event = eventService.createEventForNewMessage(messageDto);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
 
    
    
    
    public void onCreateQuest(final Quest quest, Principal principal) {
        Event event = eventService.createEventForCreatedQuest(quest, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
    
    public void onDeleteQuest(final Quest quest, Principal principal) {
        Event event = eventService.createEventForDeletedQuest(quest, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
    
    public void onUpdateQuest(final Quest quest, Principal principal) {
        Event event = eventService.createEventForUpdatedQuest(quest, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
    
    public void onSolveQuest(final Quest quest, Principal principal) {
        Event event = eventService.createEventForSolvedQuest(quest, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
    
    public void onUserJoinQuest(final Quest quest, Principal principal, User user) {
        Event event = eventService.createEventForUserJoinQuest(quest, principal, user);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
    
    
    
    
    public void onCreateAdventure(final Adventure adventure, Principal principal) {
        Event event = eventService.createEventForCreatedAdventure(adventure, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
    
    public void onDeleteAdventure(final Adventure adventure, Principal principal) {
        Event event = eventService.createEventForDeletedAdventure(adventure, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
    
    
    
    
    public void onCreateArtefact(final Artefact artefact, Principal principal) {
        Event event = eventService.createEventForCreatedArtefact(artefact, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }
    
    public void onDeleteArtefact(final Artefact artefact, Principal principal) {
        Event event = eventService.createEventForDeletedArtefact(artefact, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
    }

	public void onUpdateArtefact(Artefact artefact, Principal principal) {
        Event event = eventService.createEventForUpdatedArtefact(artefact, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend(CHAT, eventUserDto);
	}

}

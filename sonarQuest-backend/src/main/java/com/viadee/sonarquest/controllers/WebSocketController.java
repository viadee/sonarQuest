package com.viadee.sonarquest.controllers;

import java.security.Principal;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.viadee.sonarquest.entities.Event;
import com.viadee.sonarquest.entities.EventUserDto;
import com.viadee.sonarquest.entities.MessageDto;
import com.viadee.sonarquest.entities.Quest;
import com.viadee.sonarquest.services.EventService;

@Controller
public class WebSocketController {

    protected static final Log LOGGER = LogFactory.getLog(WebSocketController.class);

    private final SimpMessagingTemplate template;
    
    @Autowired
    private EventService eventService;

    @Autowired
    public WebSocketController(final SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/send/message")
    public void onReceivedMessage(final MessageDto messageDto) {
        Event event = eventService.createEventForNewMessage(messageDto);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend("/chat", eventUserDto);
    }
    
    public void onCreateQuest(final Quest quest, Principal principal) {
        Event event = eventService.createEventForCreatedQuest(quest, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend("/chat", eventUserDto);
    }
    
    public void onDeleteQuest(final Quest quest, Principal principal) {
        Event event = eventService.createEventForDeleteQuest(quest, principal);
        EventUserDto eventUserDto = eventService.eventToEventUserDto(event);
        template.convertAndSend("/chat", eventUserDto);
    }

}

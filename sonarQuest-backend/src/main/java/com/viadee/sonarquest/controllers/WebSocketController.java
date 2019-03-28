package com.viadee.sonarquest.controllers;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.viadee.sonarquest.entities.Event;
import com.viadee.sonarquest.entities.MessageDto;
import com.viadee.sonarquest.services.EventService;

@Controller
public class WebSocketController {

    protected static final Log LOGGER = LogFactory.getLog(WebSocketController.class);

    private final SimpMessagingTemplate template;
    
    @Autowired
    private EventService eventService;

    @Autowired
    WebSocketController(final SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/send/message")
    public void onReceivedMessage(final MessageDto messageDto) {
        Event event = eventService.createEventForNewMessage(messageDto);
        this.template.convertAndSend("/chat", event);
    }

}

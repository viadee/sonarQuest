package com.viadee.sonarquest.controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;

@Controller
public class WebSocketController {
	protected static final Log LOGGER = LogFactory.getLog(WebSocketController.class);
	
	private final SimpMessagingTemplate template;
	
	@Autowired
	WebSocketController(SimpMessagingTemplate template){
		this.template = template;
	}
	
	
	

    @CrossOrigin
    @MessageMapping("/send/message")
	public void onReceivedMessage(String message) {
		LOGGER.info("----------------------------------------------------------onReceivedMessage(String message)");
		this.template.convertAndSend("/chat", new SimpleDateFormat("HH:mm:ss").format(new Date())+"- "+message);
	}
	
}

/*
@CrossOrigin
@RestController(value = "chatCtrl")
@RequestMapping("api/v1/chats")
public class ChatCtrl {

    @Autowired
    ChatRepo repo;

    @Autowired
    MessageRepo messageRepo;

    @Autowired
    private SimpMessagingTemplate template;

    @RequestMapping(value="/{id}/messages", method=RequestMethod.POST)
    public void processMessage(@PathVariable Long id, @RequestBody Message message){
        //Process Message
        System.out.println("Method invoked!");
        message.setChat(repo.findOne(id));
        messageRepo.save(message);
        //Notify clients
        this.template.convertAndSend("/chat", message);
    }
}
*/
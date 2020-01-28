package com.viadee.sonarquest.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.QualityRaid;
import com.viadee.sonarquest.entities.Raid;
import com.viadee.sonarquest.repositories.WorldRepository;
import com.viadee.sonarquest.services.EventService;
import com.viadee.sonarquest.services.GratificationService;
import com.viadee.sonarquest.services.QualityRaidService;
import com.viadee.sonarquest.services.UserService;

@RestController
@RequestMapping("/qualityraid")
public class QualityRaidController {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(QualityRaidController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private WorldRepository worldRepository;

   @Autowired
   private QualityRaidService qualityRaidService;

    @Autowired
    private GratificationService gratificationService;

    @Autowired
    private EventService eventService;
    
    
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public QualityRaid createQualityRaid(@RequestBody final Raid raid) {
//    	return qualityRaidService.createAndSaveQualityRaid(raid.getWorld().getId(), raid.getTitle(), raid.getStory(), raid.getGold(), raid.getXp());
//    }

}

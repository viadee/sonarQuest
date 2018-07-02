package com.viadee.sonarQuest.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarQuest.entities.SonarConfig;
import com.viadee.sonarQuest.services.SonarConfigService;

@RestController
@RequestMapping("/sonarconfig")
public class SonarConfigController {

    @Autowired
    private SonarConfigService sonarConfigService;

    @RequestMapping(method = RequestMethod.GET)
    public SonarConfig getSonarConfigs() {
        return sonarConfigService.getConfig();
    }

    @RequestMapping(method = RequestMethod.POST)
    public void saveSonarConfig(@Valid @RequestBody final SonarConfig sonarConfig) {
        sonarConfigService.saveConfig(sonarConfig);
    }

    @RequestMapping(value = "/checkSonarQubeUrl",method = RequestMethod.POST)
    public Boolean checkSonarQubeUrl(@Valid @RequestBody final SonarConfig sonarConfig) {
        return sonarConfigService.checkSonarQubeURL(sonarConfig);
    }


}

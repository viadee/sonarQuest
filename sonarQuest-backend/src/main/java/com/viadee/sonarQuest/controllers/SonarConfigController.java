package com.viadee.sonarQuest.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
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
    public List<SonarConfig> getSonarConfigs() {
        return sonarConfigService.getAll();
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public SonarConfig getSonarConfig(@PathVariable(value = "name") final String name) {
        return sonarConfigService.getConfig(name);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public void createSonarConfig(@Valid @RequestBody final SonarConfig sonarConfig) {
        sonarConfigService.saveConfig(sonarConfig);
    }

}

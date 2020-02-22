package com.viadee.sonarquest.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.viadee.sonarquest.entities.SonarConfig;
import com.viadee.sonarquest.services.SonarConfigService;

@RestController
@RequestMapping("/sonarconfig")
public class SonarConfigController {

    private final SonarConfigService sonarConfigService;

    public SonarConfigController(SonarConfigService sonarConfigService) {
        this.sonarConfigService = sonarConfigService;
    }

    @GetMapping
    public SonarConfig getSonarConfigs() {
        return sonarConfigService.getConfig();
    }

    @PostMapping
    public void saveSonarConfig(@Valid @RequestBody final SonarConfig sonarConfig) {
        sonarConfigService.saveConfig(sonarConfig);
    }

    @PostMapping(value = "/checkSonarQubeUrl")
    public Boolean checkSonarQubeUrl(@Valid @RequestBody final SonarConfig sonarConfig) {
        return sonarConfigService.checkSonarQubeURL(sonarConfig);
    }


}

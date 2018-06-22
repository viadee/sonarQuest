package com.viadee.sonarQuest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.SonarConfig;
import com.viadee.sonarQuest.repositories.SonarConfigRepository;

@Service
public class SonarConfigService {

    @Autowired
    private SonarConfigRepository sonarConfigRepository;

    public SonarConfig getConfig() {
        return sonarConfigRepository.findFirstBy();
    }

    public SonarConfig saveConfig(final SonarConfig config) {
        final SonarConfig currentConfig = getConfig();
        return currentConfig == null ? sonarConfigRepository.save(config) : updateCurrentConfig(config, currentConfig);
    }

    private SonarConfig updateCurrentConfig(final SonarConfig config, final SonarConfig currentConfig) {
        currentConfig.setName(config.getName());
        currentConfig.setSonarServerUrl(config.getSonarServerUrl());
        return sonarConfigRepository.save(currentConfig);
    }

}

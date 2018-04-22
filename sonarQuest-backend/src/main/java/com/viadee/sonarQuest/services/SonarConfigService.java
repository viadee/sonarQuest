package com.viadee.sonarQuest.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.SonarConfig;
import com.viadee.sonarQuest.repositories.SonarConfigRepository;

@Service
public class SonarConfigService {

    @Autowired
    private SonarConfigRepository sonarConfigRepository;

    public SonarConfig getConfig(final String name) {
        return sonarConfigRepository.getByName(name);
    }

    public SonarConfig saveConfig(final SonarConfig config) {
        final SonarConfig currentConfig = getConfig(config.getName());
        return currentConfig == null ? sonarConfigRepository.save(config) : updateCurrentConfig(config, currentConfig);
    }

    private SonarConfig updateCurrentConfig(final SonarConfig config, final SonarConfig currentConfig) {
        currentConfig.setSonarProject(config.getSonarProject());
        currentConfig.setSonarServerUrl(config.getSonarServerUrl());
        return sonarConfigRepository.save(currentConfig);
    }

    public List<SonarConfig> getAll() {
        return sonarConfigRepository.findAll();
    }
}

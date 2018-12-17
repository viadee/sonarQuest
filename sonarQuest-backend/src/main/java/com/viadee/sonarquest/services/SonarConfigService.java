package com.viadee.sonarquest.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.viadee.sonarquest.entities.SonarConfig;
import com.viadee.sonarquest.externalressources.SonarQubeApiResponse;
import com.viadee.sonarquest.repositories.SonarConfigRepository;

@Service
public class SonarConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SonarConfigService.class);

    @Autowired
    private SonarConfigRepository sonarConfigRepository;

    @Autowired
    private RestTemplateService restTemplateService;

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

    public boolean checkSonarQubeURL(final SonarConfig sonarConfig) {
        boolean result = false;

        final String apiAddress = sonarConfig.getSonarServerUrl() + "/api";
        LOGGER.info("Testing server at " + apiAddress);
        final RestTemplate restTemplate = restTemplateService.getRestTemplate(sonarConfig);

        try {
            final ResponseEntity<SonarQubeApiResponse> response = restTemplate.getForEntity(apiAddress,
                    SonarQubeApiResponse.class);

            if (response.hasBody()) {
                LOGGER.info("HTML Body returned from server - server is reachable at " + apiAddress);
                result = true;
            }
        } catch (final Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        return result;
    }

}

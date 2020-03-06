package com.viadee.sonarquest.services;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.viadee.sonarquest.entities.SonarConfig;
import com.viadee.sonarquest.externalressources.SonarQubeApiResponse;
import com.viadee.sonarquest.repositories.SonarConfigRepository;

@Service
public class SonarConfigService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SonarConfigService.class);

    private final SonarConfigRepository sonarConfigRepository;

    private final RestTemplateService restTemplateService;

    public SonarConfigService(final SonarConfigRepository sonarConfigRepository, final RestTemplateService restTemplateService) {
        this.sonarConfigRepository = sonarConfigRepository;
        this.restTemplateService = restTemplateService;
    }

    public SonarConfig getConfig() {
        return sonarConfigRepository.findFirstBy();
    }

    public SonarConfig saveConfig(final SonarConfig config) {
        final SonarConfig currentConfig = getConfig();
        return currentConfig == null ? saveNewConfig(config) : updateCurrentConfig(config, currentConfig);
    }

    private SonarConfig saveNewConfig(final SonarConfig config) {
        config.setSonarServerUrl(configUrlWithoutSlash(config.getSonarServerUrl()));
        return sonarConfigRepository.save(config);
    }

    private String configUrlWithoutSlash(final String url) {
        return StringUtils.removeEnd(url, "/");
    }

    private SonarConfig updateCurrentConfig(final SonarConfig config, final SonarConfig currentConfig) {
        currentConfig.setName(config.getName());
        currentConfig.setSonarServerUrl(configUrlWithoutSlash(config.getSonarServerUrl()));
        currentConfig.setHttpBasicAuthUsername(config.getHttpBasicAuthUsername());
        currentConfig.setHttpBasicAuthPassword(config.getHttpBasicAuthPassword());
        return saveNewConfig(currentConfig);
    }

    public boolean checkSonarQubeURL(final SonarConfig sonarConfig) {
        boolean result = false;

        final String apiAddress = sonarConfig.getSonarServerUrl() + "/api";
        LOGGER.info("Testing server at {}", apiAddress);
        final RestTemplate restTemplate = restTemplateService.getRestTemplate(sonarConfig);

        try {
            final ResponseEntity<SonarQubeApiResponse> response = restTemplate.getForEntity(apiAddress,
                    SonarQubeApiResponse.class);

            if (response.hasBody()) {
                LOGGER.info("HTML Body returned from server - server is reachable at {}", apiAddress);
                result = true;
            }
        } catch (final Exception e) {
            LOGGER.debug(e.getMessage(), e);
        }
        return result;
    }

}

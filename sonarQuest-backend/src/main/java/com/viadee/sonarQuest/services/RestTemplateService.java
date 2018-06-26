package com.viadee.sonarQuest.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.viadee.sonarQuest.entities.SonarConfig;

@Service
public class RestTemplateService {

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    public RestTemplate getRestTemplate(final SonarConfig sonarConfig) {
        return sonarConfig.hasHttpBasicAuth() ? restTemplateBuilder
                .basicAuthorization(sonarConfig.getHttpBasicAuthUsername(), sonarConfig.getHttpBasicAuthPassword())
                .build() : restTemplateBuilder.build();
    }
}

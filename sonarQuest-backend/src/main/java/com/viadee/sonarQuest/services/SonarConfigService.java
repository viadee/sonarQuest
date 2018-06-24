package com.viadee.sonarQuest.services;

import com.viadee.sonarQuest.externalRessources.SonarQubeApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.viadee.sonarQuest.entities.SonarConfig;
import com.viadee.sonarQuest.repositories.SonarConfigRepository;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Service
public class SonarConfigService {

    private static final Logger log = LoggerFactory.getLogger(SonarConfigService.class);

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

    public boolean checkSonarQubeURL(SonarConfig sonarConfig) {

        boolean result = false;

        String apiAddress = sonarConfig.getSonarServerUrl()+"/api";

            final Client client = ClientBuilder.newClient();

            final WebTarget webTarget = client.target(apiAddress);

            final Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);

            try {
                final SonarQubeApiResponse sonarQubeApiResponse = invocationBuilder.get(SonarQubeApiResponse.class);
                if (sonarQubeApiResponse.getWebServices().size()>0){
                    result = true;
                }
            }
            catch (Exception e){
                log.error(e.toString());
            }


        return result;

    }

    private static boolean isIpReachable(String target) {

        boolean result = false;
        try {
            InetAddress address = InetAddress.getByName(target);
            result = address.isReachable(5000);  //timeout 5sec
        } catch (UnknownHostException ex) {
            log.error(ex.toString());
        } catch (IOException ex) {
            log.error(ex.toString());
        }
        return result;
    }
}

package com.viadee.sonarquest.services;

import com.viadee.sonarquest.entities.SonarConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.function.Supplier;

@Service
public class RestTemplateService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateService.class);

    private final RestTemplateBuilder restTemplateBuilder;

    public RestTemplateService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
    }

    public RestTemplate getRestTemplate(final SonarConfig sonarConfig) {
        if (sonarConfig.hasHttpBasicAuth()) {
            LOGGER.debug("Connecting using HTTP Basic Auth");
            return restTemplateBuilder.basicAuthentication(sonarConfig.getHttpBasicAuthUsername(),
                    sonarConfig.getHttpBasicAuthPassword())
                    .requestFactory(requestFactory()).build();
        } else {
            LOGGER.debug("Connecting using the SSL Request Factory");
            return restTemplateBuilder.requestFactory(requestFactory()).build();
        }
    }

    private Supplier<ClientHttpRequestFactory> requestFactory() {
        final TrustStrategy acceptingTrustStrategy = (final X509Certificate[] chain, final String authType) -> true;

        final SSLContext sslContext = createSslContext(acceptingTrustStrategy);

        final SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
        final CloseableHttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(csf)
                .build();

        final HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return () -> requestFactory;
    }

    private SSLContext createSslContext(final TrustStrategy acceptingTrustStrategy) {
        SSLContext sslContext = null;
        try {
            sslContext = org.apache.http.ssl.SSLContexts.custom()
                    .loadTrustMaterial(null, acceptingTrustStrategy)
                    .build();
        } catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
            LOGGER.error(e.getMessage(), e);
        }
        return sslContext;
    }
}

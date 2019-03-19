package com.viadee.sonarquest.configurations;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebConfig.class);

	@Value("${cors.header.active:false}")
	private boolean corsHeaderActive;

	/*
	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		if (corsHeaderActive) {
            LOGGER.info("|||||||||||||||||||||||||corsHeaderActive|||||||||||||||||||||");
			registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
					.allowedOrigins("").allowCredentials(true).maxAge(36000)
					.allowedHeaders("*")
					.exposedHeaders();
		}
	}
	*/
	
	@Override
    public void addCorsMappings(final CorsRegistry registry) {
        if (corsHeaderActive) {
            registry.addMapping("http://localhost:4200").allowedOrigins("http://localhost:4200")	
                    .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH");
        }
    }
	 
}
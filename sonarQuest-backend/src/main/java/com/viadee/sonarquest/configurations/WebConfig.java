package com.viadee.sonarquest.configurations;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Value("${cors.header.active:false}")
	private boolean corsHeaderActive;

	@Override
	public void addCorsMappings(final CorsRegistry registry) {
		if (corsHeaderActive) {
			registry.addMapping("/**").allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH")
					.allowedOrigins("http://localhost:4200").allowCredentials(true).maxAge(3600)
					.allowedHeaders("Accept", "Content-Type", "Origin", "Authorization", "X-Auth-Token")
					.exposedHeaders("X-Auth-Token", "Authorization")
		}
	}

}
package com.viadee.sonarquest.configurations;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    protected static final Log LOGGER = LogFactory.getLog(WebSocketConfig.class);

    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        LOGGER.info("------------------------------------configureMessageBroker");
        registry.enableSimpleBroker("/chat");
        registry.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        LOGGER.info("------------------------------------registerStompEndpoints");
        registry.addEndpoint("/socket")
		        .setAllowedOrigins("*")
		        .withSockJS();
	}
	
	
	
    
}



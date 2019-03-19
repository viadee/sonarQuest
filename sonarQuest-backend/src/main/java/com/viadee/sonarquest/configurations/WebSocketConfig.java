package com.viadee.sonarquest.configurations;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer{
	protected static final Log LOGGER = LogFactory.getLog(WebSocketConfig.class);

	@Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
		LOGGER.info("------------------------------------registerStompEndpoints");
        registry.addEndpoint("/socket")
                //.setAllowedOrigins("http://localhost:4200")
                .withSockJS()
                ;
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
		LOGGER.info("------------------------------------configureMessageBroker");
        registry.setApplicationDestinationPrefixes("/app")
                .enableSimpleBroker("/chat");
       
    }
    
    
}
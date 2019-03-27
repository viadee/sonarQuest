package com.viadee.sonarquest.configurations;

import java.security.Principal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.security.config.annotation.web.messaging.MessageSecurityMetadataSourceRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig extends AbstractWebSocketMessageBrokerConfigurer {

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
        		.setHandshakeHandler(new MyHandshakeHandler())
                .setAllowedOrigins("*")
                .withSockJS();
    }
    
    
    protected void configureInbound(MessageSecurityMetadataSourceRegistry messages) { 
        messages
        .simpDestMatchers("/*").permitAll();
        
        /*
         * 3 Our messages require the proper authorization. Specifically, any inbound message that starts with "/user/" will require ROLE_USER. 
         *   Additional details on authorization can be found in Section 20.3, “WebSocket Authorization”
         */
        //.simpDestMatchers("/user/*").authenticated();
    }
    
    
    
    
    public class MyHandshakeHandler extends DefaultHandshakeHandler {

        @Override        
        protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler,
                Map<String, Object> attributes) {
        	LOGGER.info("------------------------------------determineUser------------------------------------------------------------------------------------------------------------");
        	
        	
        	/* 
        	 * Problem???!!!
        	 * request.getPrincipal() = null   !!
        	 */
        	LOGGER.info(request.getPrincipal()); 
             // TODO Auto-generated method stub
            return super.determineUser(request, wsHandler, attributes);
        }

    }
    
}



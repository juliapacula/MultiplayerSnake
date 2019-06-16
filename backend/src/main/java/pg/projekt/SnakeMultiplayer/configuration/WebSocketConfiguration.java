package pg.projekt.SnakeMultiplayer.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import java.util.Arrays;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        //  Adds endpoints for messages where broker will be listening on server side
        //  and converting messages so they can be sent as JSON
        String[] endpoints = Arrays.stream(OutputEndpoint.values()).map((OutputEndpoint::getUrl)).toArray(String[]::new);
        config.enableSimpleBroker(endpoints);
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // Adds endpoints that are accessible from the web while
        // doing connection (eg. var socket = new SockJS(here goes endpoint)
        for (WebSocketEndpoint endpoint : WebSocketEndpoint.values()) {
            registry.addEndpoint(endpoint.getUrl())
                    .setAllowedOrigins("http://localhost:8080", "ws://localhost:8080");
        }
    }

    @Override
    public void configureClientOutboundChannel(ChannelRegistration registration) {
        registration.interceptors(new ChannelOutboundInterceptor());
    }
}
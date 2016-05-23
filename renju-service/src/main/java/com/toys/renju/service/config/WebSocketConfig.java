package com.toys.renju.service.config;

import com.toys.renju.service.RenjuHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * Created by lingyao on 16/5/20.
 */

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(renjuHandler(), "/renju")
                .withSockJS();
    }

    @Bean
    public WebSocketHandler renjuHandler() {
        return new RenjuHandler();
    }
}
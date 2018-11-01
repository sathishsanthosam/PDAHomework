package com.ksu.pda.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.server.standard.ServletServerContainerFactoryBean;

import com.ksu.pda.handler.PDAHandler;


@Configuration
@EnableWebSocket
public class WebSocketConfig  implements WebSocketConfigurer{
	
	@Autowired
	private PDAHandler pdaHandler;


	@Override
	public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {		
		registry.addHandler(pdaHandler, "/DFA").setAllowedOrigins("*");
	}
	
	@Bean
    public ServletServerContainerFactoryBean createWebSocketContainer() {
        ServletServerContainerFactoryBean container = new ServletServerContainerFactoryBean();
        container.setMaxTextMessageBufferSize(8192);
        container.setMaxBinaryMessageBufferSize(8192);
        container.setMaxSessionIdleTimeout((long) (4 * 60000));
        return container;
    }

}

package com.dh.config;

import com.dh.handler.EchoHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.reactive.HandlerMapping;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.handler.SimpleUrlHandlerMapping;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableWebFlux
public class WebSocketConfiguration {

	@Bean
	public HandlerMapping webSocketMapping() {
		final Map<String, WebSocketHandler> map = new HashMap<>(2);
		map.put("/echo", new EchoHandler());
		map.put("/foo", new EchoHandler());

		final SimpleUrlHandlerMapping mapping = new SimpleUrlHandlerMapping();
		mapping.setOrder(Ordered.HIGHEST_PRECEDENCE);
		mapping.setUrlMap(map);
		return mapping;
	}

	@Bean
	public WebSocketHandlerAdapter handlerAdapter() {
		return new WebSocketHandlerAdapter();
	}
}

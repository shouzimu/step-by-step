package com.dh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.socket.server.WebSocketService;
import org.springframework.web.reactive.socket.server.support.HandshakeWebSocketService;
import org.springframework.web.reactive.socket.server.support.WebSocketHandlerAdapter;
import org.springframework.web.reactive.socket.server.upgrade.TomcatRequestUpgradeStrategy;

@Configuration
public class WebConfig {

	@Bean
	public WebSocketHandlerAdapter handlerAdapter() {
		return new WebSocketHandlerAdapter(webSocketService());
	}

	@Bean
	public WebSocketService webSocketService() {
		TomcatRequestUpgradeStrategy strategy = new TomcatRequestUpgradeStrategy();
		strategy.setMaxSessionIdleTimeout(0L);
		return new HandshakeWebSocketService(strategy);
	}
}
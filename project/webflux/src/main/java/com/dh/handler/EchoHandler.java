package com.dh.handler;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.socket.WebSocketHandler;
import org.springframework.web.reactive.socket.WebSocketSession;

import reactor.core.publisher.Mono;

@Component
public class EchoHandler implements WebSocketHandler {
	@Override
	public Mono<Void> handle(WebSocketSession webSocketSession) {
		return webSocketSession.send(webSocketSession.receive().
				map(msg -> webSocketSession.textMessage("ECHO ->" + msg.getPayloadAsText())));
	}
}

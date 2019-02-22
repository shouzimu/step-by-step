package com.dh;

import org.springframework.web.reactive.socket.WebSocketMessage;
import org.springframework.web.reactive.socket.client.ReactorNettyWebSocketClient;
import org.springframework.web.reactive.socket.client.WebSocketClient;

import java.net.URI;
import java.time.Duration;

import reactor.core.publisher.Mono;

public class WebsocketTest {
	public static void main(String[] args) {
		WebSocketClient
				client = new ReactorNettyWebSocketClient();
		client.execute(URI.create("ws://localhost:8080/echo")
				, webSocketSession -> webSocketSession.send(
						Mono.just(webSocketSession.textMessage("hello world"))).thenMany(webSocketSession.receive()
						.map(WebSocketMessage::getPayloadAsText)).log().then()).block(Duration.ofSeconds(10));
	}
}

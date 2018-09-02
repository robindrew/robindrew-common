package com.robindrew.common.service.component.jetty.websocket;

import java.util.concurrent.atomic.AtomicLong;

import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingWebSocket extends WebSocketAdapter {

	private static final Logger log = LoggerFactory.getLogger(LoggingWebSocket.class);

	private static final AtomicLong nextNumber = new AtomicLong(0);

	private final long number;

	public LoggingWebSocket() {
		this.number = nextNumber.incrementAndGet();
	}

	public long getNumber() {
		return number;
	}

	@Override
	public void onWebSocketConnect(Session session) {
		super.onWebSocketConnect(session);

		log.info("[WebSocket Connected] #{} - Client: {}", number, session.getRemoteAddress());
	}

	@Override
	public void onWebSocketText(String message) {
		log.info("[WebSocket Text] #{} - Message: '{}'", number, message);
	}

	@Override
	public void onWebSocketBinary(byte[] bytes, int offset, int length) {
		log.info("[WebSocket Binary] #{} - Message: {} bytes", number, length);
	}

	@Override
	public void onWebSocketClose(int statusCode, String reason) {
		super.onWebSocketClose(statusCode, reason);

		if (reason == null) {
			log.info("[WebSocket Disconnected] #{} - Status: {}", number, statusCode);
		} else {
			log.info("[WebSocket Disconnected] #{} - Status: {}, Reason: '{}'", number, statusCode, reason);
		}
	}

	@Override
	public void onWebSocketError(Throwable cause) {
		super.onWebSocketError(cause);

		log.warn("[WebSocket Error] #{}", number, cause);
	}
}
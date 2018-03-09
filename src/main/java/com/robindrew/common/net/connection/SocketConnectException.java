package com.robindrew.common.net.connection;

public class SocketConnectException extends RuntimeException {

	private static final long serialVersionUID = -177147760979201979L;

	public SocketConnectException(String message, Exception e) {
		super(message, e);
	}
}

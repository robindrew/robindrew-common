package com.robindrew.common.http;

public class HttpException extends RuntimeException {

	private static final long serialVersionUID = -238206862374544284L;

	public HttpException(String message) {
		super(message);
	}

	public HttpException(Throwable cause) {
		super(cause);
	}

	public HttpException(String message, Throwable cause) {
		super(message, cause);
	}

}

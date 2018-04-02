package com.robindrew.common.http;

public class HttpResponseException extends RuntimeException {

	private static final long serialVersionUID = -238206862374544284L;

	private final int statusCode;

	public HttpResponseException(int statusCode, String message) {
		super(message);
		this.statusCode = statusCode;
	}

	public HttpResponseException(int statusCode, Throwable cause) {
		super(cause);
		this.statusCode = statusCode;
	}

	public HttpResponseException(int statusCode, String message, Throwable cause) {
		super(message, cause);
		this.statusCode = statusCode;
	}

	public int getStatusCode() {
		return statusCode;
	}

}

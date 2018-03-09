package com.robindrew.common.text.parser;

public class ParserException extends RuntimeException {

	private static final long serialVersionUID = 1276344060325019252L;

	public ParserException(String message) {
		super(message);
	}

	public ParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public ParserException(Throwable cause) {
		super(cause);
	}

}

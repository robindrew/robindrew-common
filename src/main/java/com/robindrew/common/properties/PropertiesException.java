package com.robindrew.common.properties;

public class PropertiesException extends RuntimeException {

	private static final long serialVersionUID = 4928900608132690125L;

	public PropertiesException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertiesException(String message) {
		super(message);
	}

}

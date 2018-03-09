package com.robindrew.common.properties.map;

/**
 * A Property Exception.
 */
public class PropertyException extends RuntimeException {

	private static final long serialVersionUID = 5894652053084925606L;

	public PropertyException(Throwable cause) {
		super(cause);
	}

	public PropertyException(String message, Throwable cause) {
		super(message, cause);
	}

	public PropertyException(String message) {
		super(message);
	}

}

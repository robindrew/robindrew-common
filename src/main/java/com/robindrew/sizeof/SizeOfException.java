package com.robindrew.sizeof;

public class SizeOfException extends RuntimeException {

	private static final long serialVersionUID = 7452239041695365865L;

	public static SizeOfException rethrow(Throwable throwable) {
		while (throwable instanceof SizeOfException) {
			Throwable cause = throwable.getCause();
			if (cause == null) {
				break;
			}
			throwable = cause;
		}
		if (throwable instanceof Error) {
			throw (Error) throwable;
		}
		if (throwable instanceof RuntimeException) {
			throw (RuntimeException) throwable;
		}
		throw new SizeOfException(throwable);
	}

	public SizeOfException(Throwable cause) {
		super(cause);
	}

}

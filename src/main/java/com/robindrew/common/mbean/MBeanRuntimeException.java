package com.robindrew.common.mbean;

/**
 * An MBean Runtime Exception.
 */
public class MBeanRuntimeException extends RuntimeException {

	/** Serialization id. */
	private static final long serialVersionUID = -1778670723265572774L;

	/**
	 * Creates a new exception.
	 * @param cause the cause.
	 */
	public MBeanRuntimeException(Throwable cause) {
		super(cause);
	}

}

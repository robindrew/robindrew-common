package com.robindrew.common.template;

public class TemplateException extends RuntimeException {

	private static final long serialVersionUID = -8605958699291480665L;

	public TemplateException(String message, Throwable t) {
		super(message, t);
	}

	public TemplateException(String message) {
		super(message);
	}
}

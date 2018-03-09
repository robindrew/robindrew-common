package com.robindrew.common.http.servlet.response.exception;

import com.robindrew.common.http.servlet.response.IHttpResponse;

/**
 * An exception that represents an HTTP response. In most cases you should never use an exception for control flow, use
 * sparingly!
 */
public abstract class HttpResponseException extends RuntimeException {

	private static final long serialVersionUID = 202426466542587246L;

	public abstract void populate(IHttpResponse response);

}

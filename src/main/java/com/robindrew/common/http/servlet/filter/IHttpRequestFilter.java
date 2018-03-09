package com.robindrew.common.http.servlet.filter;

import com.robindrew.common.http.servlet.request.IHttpRequest;

public interface IHttpRequestFilter {

	/**
	 * Returns true if basic authentication is enabled.
	 * @return true if basic authentication is enabled.
	 */
	boolean isEnabled();

	/**
	 * Enable or disable authentication.
	 * @param enable true to enable.
	 */
	void setEnabled(boolean enable);

	/**
	 * Returns true if the given HTTP request is valid.
	 * @param request the request.
	 * @return true if the given HTTP request is valid.
	 */
	boolean isValid(IHttpRequest request);

}

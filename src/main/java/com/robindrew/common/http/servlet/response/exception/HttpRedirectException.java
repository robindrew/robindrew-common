package com.robindrew.common.http.servlet.response.exception;

import com.robindrew.common.http.servlet.Servlets;
import com.robindrew.common.http.servlet.response.IHttpResponse;

public class HttpRedirectException extends HttpResponseException {

	private static final long serialVersionUID = -3949288785358837954L;

	private final String path;

	public HttpRedirectException(String path) {
		this.path = path;
	}

	@Override
	public void populate(IHttpResponse response) {
		Servlets.found(response, path);
	}

}

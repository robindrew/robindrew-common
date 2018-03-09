package com.robindrew.common.http.servlet.response.exception;

import com.robindrew.common.http.servlet.Servlets;
import com.robindrew.common.http.servlet.response.IHttpResponse;

public class HttpNotFoundException extends HttpResponseException {

	private static final long serialVersionUID = -3949288785358837954L;

	@Override
	public void populate(IHttpResponse response) {
		Servlets.notFound(response);
	}

}

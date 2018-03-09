package com.robindrew.common.http.servlet.response.exception;

import com.robindrew.common.http.servlet.Servlets;
import com.robindrew.common.http.servlet.response.IHttpResponse;

public class HttpUnauthorizedException extends HttpResponseException {

	private static final long serialVersionUID = 4226919650196954857L;

	@Override
	public void populate(IHttpResponse response) {
		Servlets.unauthorized(response);
	}

}

package com.robindrew.common.http.servlet.response.exception;

import com.robindrew.common.http.servlet.Servlets;
import com.robindrew.common.http.servlet.response.IHttpResponse;

public class HttpForbiddenException extends HttpResponseException {

	private static final long serialVersionUID = 808812139629359463L;

	@Override
	public void populate(IHttpResponse response) {
		Servlets.forbidden(response);
	}

}

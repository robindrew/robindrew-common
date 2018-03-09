package com.robindrew.common.http.servlet.response;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import com.google.common.io.ByteSource;
import com.robindrew.common.http.ContentType;
import com.robindrew.common.http.servlet.Servlets;

public class HttpResponse extends HttpServletResponseWrapper implements IHttpResponse {

	public HttpResponse(HttpServletResponse response) {
		super(response);
	}

	@Override
	public HttpServletResponse getResponse() {
		return (HttpServletResponse) super.getResponse();
	}

	@Override
	public void notFound() {
		Servlets.notFound(getResponse());
	}

	@Override
	public void found(String redirect) {
		Servlets.found(getResponse(), redirect);
	}

	@Override
	public void internalServerError(String text) {
		Servlets.internalServerError(getResponse(), text);
	}

	@Override
	public void ok(ContentType contentType) {
		Servlets.ok(getResponse(), contentType);
	}

	@Override
	public void ok(ContentType contentType, String text) {
		Servlets.ok(getResponse(), text, contentType);
	}

	@Override
	public void ok(ContentType contentType, ByteSource source) {
		Servlets.ok(getResponse(), source, contentType);
	}

	@Override
	public void setCookie(String key, String value) {
		Servlets.setCookie(getResponse(), key, value);
	}
}

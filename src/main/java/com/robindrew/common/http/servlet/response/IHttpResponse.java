package com.robindrew.common.http.servlet.response;

import javax.servlet.http.HttpServletResponse;

import com.google.common.io.ByteSource;
import com.robindrew.common.http.ContentType;

public interface IHttpResponse extends HttpServletResponse {

	void found(String redirect);

	void notFound();

	void internalServerError(String text);

	void ok(ContentType contentType, String text);

	void ok(ContentType contentType, ByteSource source);

	void ok(ContentType contentType);

	void setCookie(String key, String value);

}

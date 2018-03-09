package com.robindrew.common.http.servlet.matcher;

import static com.robindrew.common.util.Check.notEmpty;

import com.robindrew.common.http.servlet.request.IHttpRequest;

public class UriMatcher implements IHttpRequestMatcher {

	private final String uri;

	public UriMatcher(String uri) {
		this.uri = notEmpty("uri", uri);
	}

	public String getUri() {
		return uri;
	}

	@Override
	public boolean matches(IHttpRequest request) {
		return getUri().equals(request.getRequestURI());
	}

	@Override
	public String toString() {
		return "UriMatcher[" + uri + "]";
	}

}

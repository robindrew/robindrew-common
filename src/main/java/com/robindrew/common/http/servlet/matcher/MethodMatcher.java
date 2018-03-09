package com.robindrew.common.http.servlet.matcher;

import static com.robindrew.common.util.Check.notNull;

import com.robindrew.common.http.HttpMethod;
import com.robindrew.common.http.servlet.request.IHttpRequest;

public class MethodMatcher implements IHttpRequestMatcher {

	private final HttpMethod method;

	public MethodMatcher(String method) {
		this.method = HttpMethod.valueOf(method.toUpperCase());
	}

	public MethodMatcher(HttpMethod method) {
		this.method = notNull("method", method);
	}

	public HttpMethod getMethod() {
		return method;
	}

	@Override
	public boolean matches(IHttpRequest request) {
		return getMethod().name().equals(request.getMethod());
	}

	@Override
	public String toString() {
		return "MethodMatcher[" + method + "]";
	}

}

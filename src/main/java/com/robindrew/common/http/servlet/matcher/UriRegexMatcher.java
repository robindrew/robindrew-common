package com.robindrew.common.http.servlet.matcher;

import java.util.regex.Pattern;

import com.robindrew.common.http.servlet.request.IHttpRequest;

public class UriRegexMatcher implements IHttpRequestMatcher {

	private final Pattern uriRegex;

	public UriRegexMatcher(String uriRegex) {
		this.uriRegex = Pattern.compile(uriRegex);
	}

	public Pattern getUriRegex() {
		return uriRegex;
	}

	@Override
	public boolean matches(IHttpRequest request) {
		return getUriRegex().matcher(request.getRequestURI()).matches();
	}

	@Override
	public String toString() {
		return "UriRegexMatcher[" + uriRegex.pattern() + "]";
	}

}

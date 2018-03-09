package com.robindrew.common.http.servlet.matcher;

import java.util.regex.Pattern;

import com.robindrew.common.http.servlet.request.IHttpRequest;

public class DomainRegexMatcher implements IHttpRequestMatcher {

	private final Pattern domainRegex;

	public DomainRegexMatcher(String domainRegex) {
		this.domainRegex = Pattern.compile(domainRegex);
	}

	public Pattern getDomainRegex() {
		return domainRegex;
	}

	@Override
	public boolean matches(IHttpRequest request) {
		return getDomainRegex().matcher(request.getServerName()).matches();
	}

	@Override
	public String toString() {
		return "DomainRegexMatcher[" + domainRegex.pattern() + "]";
	}

}

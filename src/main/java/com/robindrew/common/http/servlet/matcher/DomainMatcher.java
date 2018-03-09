package com.robindrew.common.http.servlet.matcher;

import static com.robindrew.common.util.Check.notEmpty;

import com.robindrew.common.http.servlet.request.IHttpRequest;

public class DomainMatcher implements IHttpRequestMatcher {

	private final String domain;

	public DomainMatcher(String domain) {
		this.domain = notEmpty("domain", domain);
	}

	public String getDomain() {
		return domain;
	}

	@Override
	public boolean matches(IHttpRequest request) {
		return getDomain().equals(request.getServerName());
	}

	@Override
	public String toString() {
		return "DomainMatcher[" + domain + "]";
	}

}

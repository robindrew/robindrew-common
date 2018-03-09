package com.robindrew.common.http.servlet.matcher;

import static com.robindrew.common.util.Check.notEmpty;

import java.util.LinkedHashSet;
import java.util.Set;

import com.robindrew.common.http.servlet.request.IHttpRequest;

public class CompositeMatcher implements IHttpRequestMatcher {

	public static IHttpRequestMatcher of(Set<? extends IHttpRequestMatcher> matchers) {
		if (matchers.isEmpty()) {
			throw new IllegalStateException("No matchers provided");
		}

		if (matchers.size() == 1) {
			return matchers.iterator().next();
		}

		return new CompositeMatcher(matchers);
	}

	private final Set<IHttpRequestMatcher> matchers;

	public CompositeMatcher(Set<? extends IHttpRequestMatcher> matchers) {
		this.matchers = new LinkedHashSet<>(notEmpty("matchers", matchers));
	}

	@Override
	public boolean matches(IHttpRequest request) {
		for (IHttpRequestMatcher matcher : matchers) {
			if (!matcher.matches(request)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return matchers.toString();
	}

}

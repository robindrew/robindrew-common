package com.robindrew.common.http.servlet.filter;

import com.robindrew.common.http.servlet.request.IHttpRequest;

public class DisabledHttpRequestFilter implements IHttpRequestFilter {

	@Override
	public boolean isEnabled() {
		return false;
	}

	@Override
	public void setEnabled(boolean enable) {
		// Nothing to do
	}

	@Override
	public boolean isValid(IHttpRequest request) {
		return true;
	}

}

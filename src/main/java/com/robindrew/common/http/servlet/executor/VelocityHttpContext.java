package com.robindrew.common.http.servlet.executor;

import com.robindrew.common.http.servlet.authenticate.DisabledBasicAuthenticator;
import com.robindrew.common.http.servlet.authenticate.IBasicAuthenticator;
import com.robindrew.common.http.servlet.filter.DisabledHttpRequestFilter;
import com.robindrew.common.http.servlet.filter.IHttpRequestFilter;
import com.robindrew.common.template.ITemplateLocator;
import com.robindrew.common.util.Check;

public class VelocityHttpContext implements IVelocityHttpContext {

	private final ITemplateLocator locator;
	private volatile IBasicAuthenticator authenticator = new DisabledBasicAuthenticator();
	private volatile IHttpRequestFilter filter = new DisabledHttpRequestFilter();

	public VelocityHttpContext(ITemplateLocator locator) {
		this.locator = Check.notNull("locator", locator);
	}

	@Override
	public ITemplateLocator getLocator() {
		return locator;
	}

	@Override
	public IBasicAuthenticator getAthenticator() {
		return authenticator;
	}

	@Override
	public IHttpRequestFilter getRequestFilter() {
		return filter;
	}

	public void setAuthenticator(IBasicAuthenticator authenticator) {
		this.authenticator = Check.notNull("authenticator", authenticator);
	}

	public void setFilter(IHttpRequestFilter filter) {
		this.filter = Check.notNull("filter", filter);
	}
}

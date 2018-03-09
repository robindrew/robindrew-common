package com.robindrew.common.http.servlet.executor;

import com.robindrew.common.http.servlet.authenticate.IBasicAuthenticator;
import com.robindrew.common.http.servlet.filter.IHttpRequestFilter;
import com.robindrew.common.template.ITemplateLocator;

public interface IVelocityHttpContext {

	ITemplateLocator getLocator();

	IBasicAuthenticator getAthenticator();

	IHttpRequestFilter getRequestFilter();

}

package com.robindrew.common.http.servlet.executor;

import java.util.LinkedHashMap;
import java.util.Map;

import com.robindrew.common.http.ContentType;
import com.robindrew.common.http.servlet.authenticate.BasicAuthenticationExecutor;
import com.robindrew.common.http.servlet.authenticate.IBasicAuthenticator;
import com.robindrew.common.http.servlet.filter.IHttpRequestFilter;
import com.robindrew.common.http.servlet.request.IHttpRequest;
import com.robindrew.common.http.servlet.response.IHttpResponse;
import com.robindrew.common.http.servlet.response.exception.HttpForbiddenException;
import com.robindrew.common.template.ITemplate;
import com.robindrew.common.template.ITemplateLocator;
import com.robindrew.common.template.TemplateData;
import com.robindrew.common.util.Check;

public abstract class VelocityHttpExecutor implements IHttpExecutor {

	private final IVelocityHttpContext context;
	private final String templateName;

	public VelocityHttpExecutor(IVelocityHttpContext context, String templateName) {
		this.context = Check.notNull("locator", context);
		this.templateName = Check.notEmpty("templateName", templateName);
	}

	@Override
	public void execute(IHttpRequest request, IHttpResponse response) {

		// Filter ...
		IHttpRequestFilter filter = context.getRequestFilter();
		if (filter.isEnabled() && !filter.isValid(request)) {
			throw new HttpForbiddenException();
		}

		// Authenticate ...
		IBasicAuthenticator authenticator = context.getAthenticator();
		if (authenticator.isEnabled()) {
			IHttpExecutor executor = new BasicAuthenticationExecutor(authenticator);
			executor.execute(request, response);
		}

		// Find the template
		ITemplateLocator locator = context.getLocator();
		ITemplate template = locator.getTemplate(templateName);

		// Handle the request
		Map<String, Object> dataMap = new LinkedHashMap<>();
		execute(request, response, dataMap);

		// Execute the template
		String text = template.execute(new TemplateData(dataMap));
		response.ok(getContentType(), text);
	}

	/**
	 * Returns the content type. The default is HTML, override this method to change this.
	 */
	public ContentType getContentType() {
		return ContentType.TEXT_HTML;
	}

	protected abstract void execute(IHttpRequest request, IHttpResponse response, Map<String, Object> dataMap);

	@Override
	public String toString() {
		return getClass().getSimpleName() + "[" + templateName + "]";
	}

}

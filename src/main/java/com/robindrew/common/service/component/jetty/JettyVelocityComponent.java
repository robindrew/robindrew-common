package com.robindrew.common.service.component.jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.session.SessionHandler;

import com.google.common.base.Supplier;
import com.robindrew.common.http.servlet.executor.IVelocityHttpContext;
import com.robindrew.common.http.servlet.executor.VelocityHttpContext;
import com.robindrew.common.http.servlet.filter.HostHttpRequestFilter;
import com.robindrew.common.lang.IReference;
import com.robindrew.common.lang.Reference;
import com.robindrew.common.service.component.jetty.handler.MatcherHttpHandler;
import com.robindrew.common.template.ITemplateLocator;

public abstract class JettyVelocityComponent extends AbstractJettyComponent {

	private volatile IReference<IVelocityHttpContext> context = new Reference<>();

	@Override
	protected void startupComponent() throws Exception {

		// Initialise the locator
		context.set(createContext());

		// Startup Jetty
		super.startupComponent();
	}

	public IVelocityHttpContext getContext() {
		return context.get();
	}

	private IVelocityHttpContext createContext() {
		ITemplateLocator locator = getTemplateLocator().get();
		VelocityHttpContext context = new VelocityHttpContext(locator);
		context.setFilter(new HostHttpRequestFilter());
		return context;
	}

	@Override
	protected Handler createHandler() {
		MatcherHttpHandler handler = new MatcherHttpHandler();
		populate(handler);

		// Enable sessions
		SessionHandler session = new SessionHandler();
		session.setHandler(handler);
		return session;
	}

	protected abstract Supplier<ITemplateLocator> getTemplateLocator();

	protected abstract void populate(MatcherHttpHandler handler);

}

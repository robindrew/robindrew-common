package com.robindrew.common.service.component.jetty;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.servlet.ServletContextHandler;

public abstract class JettyServletComponent extends AbstractJettyComponent {

	private String contextPath;

	public JettyServletComponent(String contextPath) {
		this.contextPath = contextPath;
	}

	public JettyServletComponent() {
		this("/");
	}

	@Override
	protected Handler createHandler() {
		ServletContextHandler handler = new ServletContextHandler(ServletContextHandler.SESSIONS);
		handler.setContextPath(contextPath);
		addServlets(handler);
		return handler;
	}

	protected abstract void addServlets(ServletContextHandler handler);

}

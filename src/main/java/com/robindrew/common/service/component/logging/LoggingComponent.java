package com.robindrew.common.service.component.logging;

import com.robindrew.common.mbean.IMBeanRegistry;
import com.robindrew.common.mbean.annotated.AnnotatedMBeanRegistry;
import com.robindrew.common.service.component.AbstractIdleComponent;
import com.robindrew.common.service.component.logging.log4j.Log4jManager;
import com.robindrew.common.service.component.logging.logback.LogbackManager;
import com.robindrew.common.service.component.logging.logging.LoggingManager;

public class LoggingComponent extends AbstractIdleComponent {

	private final LogbackManager logback = new LogbackManager();
	private final Log4jManager log4j = new Log4jManager();
	private final LoggingManager logging = new LoggingManager();

	@Override
	protected void startupComponent() throws Exception {
		IMBeanRegistry registry = new AnnotatedMBeanRegistry();

		// Logback
		registry.register(logback);
		// server.registerMBean(logback, new ObjectName("com.robindrew.common.logging:type=LogManager,name=Logback"));

		// Log4j
		registry.register(log4j);
		// server.registerMBean(log4j, new ObjectName("com.robindrew.common.logging:type=LogManager,name=Log4j"));

		// Logging
		registry.register(logging);
		// server.registerMBean(logging, new ObjectName("com.robindrew.common.logging:type=LogManager,name=Logging"));
	}

	@Override
	protected void shutdownComponent() throws Exception {
	}

}

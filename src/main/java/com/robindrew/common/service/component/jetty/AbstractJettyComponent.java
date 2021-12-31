package com.robindrew.common.service.component.jetty;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.ExecutorSizedThreadPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.lang.IReference;
import com.robindrew.common.lang.Reference;
import com.robindrew.common.properties.map.type.IntegerProperty;
import com.robindrew.common.properties.map.type.StringProperty;
import com.robindrew.common.service.Services;
import com.robindrew.common.service.component.AbstractIdleComponent;
import com.robindrew.common.util.Threads;

public abstract class AbstractJettyComponent extends AbstractIdleComponent {

	private static final Logger log = LoggerFactory.getLogger(AbstractJettyComponent.class);

	private final IReference<Server> server = new Reference<>();

	public String getPropertyPrefix() {
		return "jetty";
	}

	public int getThreads() {
		String key = getPropertyPrefix() + ".threads";
		return new IntegerProperty(key).defaultValue(20).get();
	}

	public int getPort(int defaultPort) {
		String key = getPropertyPrefix() + ".port";
		return new IntegerProperty(key).get(defaultPort);
	}

	public String getHost(String defaultHost) {
		String key = getPropertyPrefix() + ".host";
		return new StringProperty(key).get(defaultHost);
	}

	public Server getServer() {
		return server.get();
	}

	@Override
	protected void startupComponent() throws Exception {

		// Create the thread pool
		int threads = getThreads();
		log.info("[Jetty] Threads: {}", threads);

		// Create the jetty server
		ThreadFactory factory = Threads.newThreadFactory(getName() + "Worker-%d", false);
		ThreadPoolExecutor executor = new ThreadPoolExecutor(threads, threads, 60, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), factory);
		Server server = new Server(new ExecutorSizedThreadPool(executor));

		// Create the connector for the server
		ServerConnector connector = createConnector(server);

		// Create the handler for the server
		Handler handler = createHandler();

		// Start the server
		server.addConnector(connector);
		server.setHandler(handler);
		server.start();

		this.server.set(server);
	}

	protected abstract Handler createHandler();

	protected ServerConnector createConnector(Server server) {

		// Create the connector
		ServerConnector connector = new ServerConnector(server);

		// The port defaults to the primary service port
		int port = getPort(Services.getServicePort());
		log.info("[Jetty] Port: {}", port);
		connector.setPort(port);

		// The host is not set by default
		String host = getHost(null);
		if (host != null) {
			log.info("[Jetty] Host: {}", host);
			connector.setHost(host);
		}

		return connector;
	}

	@Override
	protected void shutdownComponent() throws Exception {
		getServer().stop();
	}

}

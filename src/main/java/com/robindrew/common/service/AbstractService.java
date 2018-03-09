package com.robindrew.common.service;

import java.util.Arrays;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.AbstractIdleService;
import com.robindrew.common.service.component.IComponent;
import com.robindrew.common.util.Java;
import com.robindrew.common.util.SystemProperties;
import com.robindrew.common.util.Threads;

public abstract class AbstractService extends AbstractIdleService {

	/** The logger. */
	private static final Logger log = LoggerFactory.getLogger(AbstractService.class);

	/** The time the service was created. */
	private static final long timeCreated = System.currentTimeMillis();

	private final String[] args;
	private final String name;
	private final int port;
	private final int instance;
	private final Set<IComponent> asyncStartupSet = new CopyOnWriteArraySet<>();
	private final Set<IComponent> asyncShutdownSet = new CopyOnWriteArraySet<>();

	protected AbstractService(String[] args) {
		this.args = args;

		// Check the timezone
		checkTimezone();

		// Check the file encoding
		checkFileEncoding();

		// The name
		String serviceName = getClass().getSimpleName();
		if (!serviceName.endsWith("Service")) {
			throw new IllegalStateException("Class name must end with 'Service': " + serviceName);
		}
		serviceName = serviceName.substring(0, serviceName.lastIndexOf("Service"));
		this.name = Services.setServiceName(serviceName);

		// The port
		this.port = Services.getServicePort();

		// The instance
		this.instance = Services.getServiceInstance();
	}

	/**
	 * Returns the command line arguments.
	 */
	public String[] getArgs() {
		return Arrays.copyOf(args, args.length);
	}

	/**
	 * Override this to enforce your preferred file encoding or disable the check
	 */
	protected void checkFileEncoding() {
		String encoding = SystemProperties.getFileEncoding();
		if (!encoding.equals("UTF-8")) {
			throw new Error("System property 'file.encoding' must be set to UTF-8 with system property: '-Dfile.encoding=UTF-8'");
		}
	}

	/**
	 * Override this to enforce your preferred timezone or disable the check
	 */
	protected void checkTimezone() {
		String timezone = SystemProperties.getTimeZone();
		if (!timezone.equals("UTC")) {
			throw new Error("System property 'user.timezone' must be set to UTC with system property: '-Duser.timezone=UTC'");
		}
	}

	/**
	 * Returns the time the service was created.
	 */
	public final long getTimeCreated() {
		return timeCreated;
	}

	/**
	 * Returns the service name.
	 */
	public final String getName() {
		return name;
	}

	/**
	 * Returns the service port.
	 */
	public final int getPort() {
		return port;
	}

	/**
	 * Returns the service instance.
	 */
	public final int getInstance() {
		return instance;
	}

	/**
	 * Run the service asynchronously, waiting until it is terminated.
	 */
	public void run() {
		startAsync();
		awaitTerminated();
	}

	/**
	 * Startup this service.
	 */
	@Override
	protected void startUp() throws Exception {
		try {
			log.info("[{}] Starting Up", getName());
			Stopwatch timer = Stopwatch.createStarted();

			// Startup the service ...
			startupService();

			// Await asynchronous components (if any)
			for (IComponent component : asyncStartupSet) {
				component.awaitRunning();
			}

			timer.stop();
			log.info("[{}] Port: {}", getName(), getPort());
			log.info("[{}] Instance: #{}", getName(), getInstance());
			log.info("[{}] Started in {}", getName(), timer);

		} catch (Throwable t) {
			t.printStackTrace();
			log.error("[" + getName() + "] Startup failed", t);
			Java.exitAsync(1);
			throw t;
		}
	}

	/**
	 * Shutdown this service.
	 */
	@Override
	protected void shutDown() throws Exception {
		try {
			log.info("[{}] Shutting Down", getName());
			Stopwatch timer = Stopwatch.createStarted();

			// Shutdown the service ...
			shutdownService();

			// Await asynchronous components (if any)
			for (IComponent component : asyncShutdownSet) {
				component.awaitTerminated();
			}

			timer.stop();
			log.info("[{}] Shutdown in {}", getName(), timer);

		} catch (Throwable t) {
			log.error("[" + getName() + "] Shutdown failed", t);
			Java.exitAsync(1);
			throw t;
		}
	}

	public void registerShutdownHook(Thread hook) {
		log.info("Registering Shutdown Hook");
		Threads.addShutdownHook(hook);
	}

	/**
	 * Start the given component.
	 */
	protected void start(IComponent component) {
		start(component, false);
	}

	/**
	 * Start the given component.
	 * @param async true to start the component asynchronously
	 */
	protected void start(IComponent component, boolean async) {
		if (async) {
			asyncStartupSet.add(component);
			component.startAsync();
		} else {
			component.startSync();
		}
	}

	/**
	 * Stop the given component.
	 */
	protected void stop(IComponent component) {
		start(component, false);
	}

	/**
	 * Stop the given component.
	 * @param async true to stop the component asynchronously
	 */
	protected void stop(IComponent component, boolean async) {
		if (async) {
			asyncShutdownSet.add(component);
			component.stopAsync();
		} else {
			component.stopSync();
		}
	}

	protected abstract void startupService() throws Exception;

	protected abstract void shutdownService() throws Exception;

}

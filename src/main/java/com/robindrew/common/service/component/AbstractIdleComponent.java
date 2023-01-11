package com.robindrew.common.service.component;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.common.util.concurrent.AbstractIdleService;
import com.google.common.util.concurrent.Service;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Java;

public abstract class AbstractIdleComponent extends AbstractIdleService implements IComponent {

	private final Logger log;
	private final String name;

	private final AtomicBoolean startup = new AtomicBoolean(false);
	private final AtomicBoolean shutdown = new AtomicBoolean(false);

	protected AbstractIdleComponent(String name) {
		this.name = Check.notEmpty("name", name);
		this.log = LoggerFactory.getLogger(getClass());
	}

	protected AbstractIdleComponent() {
		this.name = getClass().getSimpleName();
		this.log = LoggerFactory.getLogger(getClass());
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Logger getLog() {
		return log;
	}

	/**
	 * Start this component.
	 */
	@Override
	public Service startSync() {
		startAsync();

		// Terminate the process if startup fails
		try {
			awaitRunning();
		} catch (Throwable t) {
			log.error("Error starting component", t);
			Java.exitAsync(1);
		}
		return this;
	}

	/**
	 * Stop this component.
	 */
	@Override
	public Service stopSync() {
		stopAsync();
		awaitTerminated();
		return this;
	}

	/**
	 * Startup this service.
	 */
	@Override
	protected void startUp() throws Exception {
		if (!startup.compareAndSet(false, true)) {
			log.warn("startUp() already called for component {}", getName());
			return;
		}
		try {
			log.info("[{}] Starting Up", getName());
			Stopwatch timer = Stopwatch.createStarted();

			// Register a listener to handle failures
			ComponentListener listener = new ComponentListener();
			addListener(listener, listener);

			// Startup the component ...
			startupComponent();

			timer.stop();
			log.info("[{}] Started in {}", getName(), timer);

		} catch (Throwable t) {
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
		if (!shutdown.compareAndSet(false, true)) {
			log.warn("shutDown() already called for component {}", getName());
			return;
		}
		try {
			log.info("[{}] Shutting Down", getName());
			Stopwatch timer = Stopwatch.createStarted();

			// Shutdown the component ...
			shutdownComponent();

			timer.stop();
			log.info("[{}] Shutdown in {}", getName(), timer);

		} catch (Throwable t) {
			log.error("[" + getName() + "] Shutdown failed", t);
		}
	}

	public class ComponentListener extends Listener implements Executor {

		@Override
		public void failed(State from, Throwable failure) {
			log.error("[" + getName() + "] Crashed", failure);
			Java.exitAsync(1);
		}

		@Override
		public void execute(Runnable command) {
			command.run();
		}

	}

	protected abstract void startupComponent() throws Exception;

	protected abstract void shutdownComponent() throws Exception;

}

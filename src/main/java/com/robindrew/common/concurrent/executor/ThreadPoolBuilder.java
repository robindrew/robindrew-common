package com.robindrew.common.concurrent.executor;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Supplier;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.common.util.concurrent.UncaughtExceptionHandlers;

public abstract class ThreadPoolBuilder implements Supplier<ExecutorService> {

	private static final Logger log = LoggerFactory.getLogger(ThreadPoolBuilder.class);

	private final String name;
	private boolean daemon = false;
	private UncaughtExceptionHandler handler = null;

	protected ThreadPoolBuilder(String name) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		this.name = name;
	}

	public ThreadPoolBuilder setUncaughtExceptionHandler(UncaughtExceptionHandler handler) {
		this.handler = handler;
		return this;
	}

	public ThreadPoolBuilder setDaemon(boolean daemon) {
		this.daemon = daemon;
		return this;
	}

	@Override
	public ExecutorService get() {
		ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
		builder.setNameFormat(name + "-%d");
		builder.setDaemon(daemon);
		if (handler != null) {
			builder.setUncaughtExceptionHandler(handler);
		} else {
			UncaughtExceptionHandler defaultHandler = Thread.getDefaultUncaughtExceptionHandler();
			if (defaultHandler != null) {
				builder.setUncaughtExceptionHandler(defaultHandler);
			} else {
				log.warn("Default UncaughtExceptionHandler not set (using System.exit)");
				builder.setUncaughtExceptionHandler(UncaughtExceptionHandlers.systemExit());
			}
		}
		return build(builder.build());
	}

	protected abstract ExecutorService build(ThreadFactory factory);

}

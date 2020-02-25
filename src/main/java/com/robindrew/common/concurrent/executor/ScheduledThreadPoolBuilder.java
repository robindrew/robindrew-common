package com.robindrew.common.concurrent.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

public class ScheduledThreadPoolBuilder extends ThreadPoolBuilder {

	private final int threads;

	public ScheduledThreadPoolBuilder(String name, int threads) {
		super(name);
		if (threads < 1) {
			throw new IllegalArgumentException("threads=" + threads);
		}
		this.threads = threads;
	}

	@Override
	protected ExecutorService build(ThreadFactory factory) {
		return Executors.newScheduledThreadPool(threads, factory);
	}

	public ScheduledThreadPoolExecutor getScheduled() {
		return (ScheduledThreadPoolExecutor) get();
	}
}

package com.robindrew.common.concurrent.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * The classic fixed thread pool using the standard pool building tools underneath.
 * <p>
 * Forces the pool to have a name for threads.
 */
public class FixedThreadPoolBuilder extends ThreadPoolBuilder {

	private final int threads;

	public FixedThreadPoolBuilder(String name, int threads) {
		super(name);
		if (threads < 1) {
			throw new IllegalArgumentException("threads=" + threads);
		}
		this.threads = threads;
	}

	@Override
	protected ExecutorService build(ThreadFactory factory) {
		return Executors.newFixedThreadPool(threads, factory);
	}

}

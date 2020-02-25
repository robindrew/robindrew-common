package com.robindrew.common.concurrent.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * The classic cached thread pool using the standard pool building tools underneath.
 * <p>
 * Forces the pool to have a name for threads.
 */
public class CachedThreadPoolBuilder extends ThreadPoolBuilder {

	public CachedThreadPoolBuilder(String name) {
		super(name);
	}

	@Override
	protected ExecutorService build(ThreadFactory factory) {
		return Executors.newCachedThreadPool(factory);
	}

}

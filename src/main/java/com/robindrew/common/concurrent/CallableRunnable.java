package com.robindrew.common.concurrent;

import java.util.concurrent.Callable;

public class CallableRunnable<T> implements Callable<T> {

	private final Runnable runnable;
	private final T returnValue;

	public CallableRunnable(Runnable runnable) {
		this(runnable, null);
	}

	public CallableRunnable(Runnable runnable, T returnValue) {
		if (runnable == null) {
			throw new NullPointerException("runnable");
		}
		this.runnable = runnable;
		this.returnValue = returnValue;
	}

	@Override
	public T call() throws Exception {
		runnable.run();
		return returnValue;
	}

}

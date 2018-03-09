package com.robindrew.common.concurrent;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class InstantFuture<V> implements Future<V> {

	private final V value;

	public InstantFuture(V value) {
		this.value = value;
	}

	@Override
	public boolean cancel(boolean mayInterruptIfRunning) {
		return false;
	}

	@Override
	public boolean isCancelled() {
		return false;
	}

	@Override
	public boolean isDone() {
		return true;
	}

	@Override
	public V get() {
		return value;
	}

	@Override
	public V get(long timeout, TimeUnit unit) {
		return value;
	}

}

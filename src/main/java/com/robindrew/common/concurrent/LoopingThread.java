package com.robindrew.common.concurrent;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.atomic.AtomicBoolean;

import com.robindrew.common.date.UnitTime;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Threads;

public abstract class LoopingThread extends Thread implements AutoCloseable {

	private final AtomicBoolean closed = new AtomicBoolean(false);
	private volatile UnitTime pause = new UnitTime(50, MILLISECONDS);

	public LoopingThread(String name) {
		super(name);
	}

	@Override
	public void run() {
		while (!isClosed()) {
			runOneIteration();
			Threads.sleep(pause);
		}
	}

	public UnitTime getPause() {
		return pause;
	}

	public void setPause(UnitTime pause) {
		this.pause = Check.notNull("pause", pause);
	}

	public boolean isClosed() {
		return closed.get();
	}

	@Override
	public void close() {
		closed.set(true);
	}

	protected abstract void runOneIteration();

}

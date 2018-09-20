package com.robindrew.common.concurrent;

import static com.robindrew.common.util.Check.notEmpty;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.date.UnitTime;

public abstract class LoopingThread implements AutoCloseable, Runnable {

	private static final Logger log = LoggerFactory.getLogger(LoopingThread.class);

	private final String name;
	private final AtomicReference<Thread> thread = new AtomicReference<>();
	private final AtomicBoolean shutdown = new AtomicBoolean(false);
	private final AtomicBoolean paused = new AtomicBoolean(false);
	private final AtomicLong interval = new AtomicLong(0);

	public LoopingThread(String name) {
		this.name = notEmpty("name", name);
	}

	public void start() {

		// Sanity checks ...
		if (!thread.compareAndSet(null, new Thread(this, name))) {
			throw new IllegalStateException("Thread already started");
		}
		if (isShutdown()) {
			throw new IllegalStateException("Thread is shutdown");
		}

		// Start!
		thread.get().start();
	}

	@Override
	public void run() {
		try {
			while (!isShutdown()) {

				// Pause?
				while (isPaused()) {
					Thread.sleep(50);
				}

				// Run a single iteration
				runOneIteration();

				// If there is an interval, sleep
				Thread.sleep(interval.get());
			}
		} catch (InterruptedException ie) {
			log.warn("Thread Interrupted");
		} catch (Throwable t) {
			log.error("Thread Crashed", t);
		} finally {
			shutdown();
		}
	}

	public long getInterval() {
		return interval.get();
	}

	public void setInterval(UnitTime interval) {
		setInterval(interval.getTime(MILLISECONDS));
	}

	public void setInterval(long millis) {
		if (millis < 0) {
			throw new IllegalArgumentException("millis=" + millis);
		}
		this.interval.set(millis);
	}

	public boolean isStartedUp() {
		return thread.get() != null;
	}

	public boolean isShutdown() {
		return shutdown.get();
	}

	public boolean isPaused() {
		return paused.get();
	}

	public void pause() {
		setPaused(true);
	}

	public void resume() {
		setPaused(false);
	}

	public void setPaused(boolean paused) {
		this.paused.set(paused);
	}

	public void shutdown() {
		shutdown.set(true);
	}

	@Override
	public void close() {
		shutdown();
	}

	protected abstract void runOneIteration();

}

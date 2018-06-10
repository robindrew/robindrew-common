package com.robindrew.common.concurrent;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CountDownLatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Java;

public class ThreadPool {

	private static final Logger log = LoggerFactory.getLogger(ThreadPool.class);

	private final Map<String, Runnable> runnableMap = new LinkedHashMap<>();

	public synchronized void register(String name, Runnable runnable) {
		Check.notEmpty("name", name);
		Check.notNull("runnable", runnable);

		if (runnableMap.containsKey(name)) {
			throw new IllegalArgumentException("Duplicate runnable: '" + name + "'");
		}
		runnableMap.put(name, runnable);
	}

	public synchronized void run() {
		if (runnableMap.isEmpty()) {
			throw new IllegalStateException("No runnables registered");
		}

		CountDownLatch latch = new CountDownLatch(runnableMap.size());
		List<ThreadPoolWorker> workers = new ArrayList<>();
		for (Entry<String, Runnable> entry : runnableMap.entrySet()) {
			String name = entry.getKey();
			Runnable runnable = entry.getValue();

			// Create and run thread
			ThreadPoolWorker worker = new ThreadPoolWorker(name, runnable, latch);
			workers.add(worker);
			worker.start();
		}

		// Wait for all threads to complete
		try {
			latch.await();
		} catch (InterruptedException e) {
			throw Java.propagate(e);
		}

		// Check for any exceptions and throw the first found (if any)
		for (ThreadPoolWorker worker : workers) {
			Optional<Throwable> throwable = worker.getThrowable();
			if (throwable.isPresent()) {
				throw Java.propagate(throwable.get());
			}
		}
	}

	protected void handleThrowable(ThreadPoolWorker worker, Throwable t) {
		log.error("Thread crashed: " + worker.getName(), t);
	}

	private class ThreadPoolWorker extends Thread {

		private final CountDownLatch latch;
		private volatile Throwable throwable;

		public ThreadPoolWorker(String name, Runnable runnable, CountDownLatch latch) {
			super(runnable, name);
			this.latch = latch;
		}

		public Optional<Throwable> getThrowable() {
			return Optional.fromNullable(throwable);
		}

		@Override
		public void run() {
			try {
				super.run();
			} catch (Throwable t) {
				this.throwable = t;
				handleThrowable(this, t);
			} finally {
				latch.countDown();
			}
		}
	}

}

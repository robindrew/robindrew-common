package com.robindrew.common.util;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.robindrew.common.date.UnitTime;

/**
 * A utility that brings together common threading functionality in to a single class.
 */
public class Threads {

	/**
	 * Utility class - private constructor.
	 */
	private Threads() {
	}

	/**
	 * Sleep for the given number of milliseconds.
	 * @param millis the milliseconds.
	 */
	public static void sleep(long millis) {
		try {
			Thread.sleep(millis);
		} catch (InterruptedException e) {
			throw Java.propagate(e);
		}
	}

	/**
	 * Sleep for the given time.
	 * @param time the time.
	 */
	public static void sleep(UnitTime time) {
		sleep(time.toMillis());
	}

	/**
	 * Sleep for the given amount of time.
	 * @param amount the amount of time.
	 * @param unit the unit of time.
	 */
	public static void sleep(long amount, TimeUnit unit) {
		sleep(unit.toMillis(amount));
	}

	/**
	 * Sleep until a given time. Attempts to allow for slippage by waking up close to the time.
	 * @param timeInMillis the time to sleep until.
	 */
	public static void sleepUntil(long timeInMillis) {
		// N.B. timeInMillis should be in the future :)
		while (true) {
			long millis = timeInMillis - System.currentTimeMillis();
			if (millis <= 0) {
				break;
			}

			// To avoid clock slippage, we only sleep for 3/4 of the duration
			// until we reach 1 second ...
			if (millis <= 1000) {
				sleep(millis);
			} else {
				sleep((millis * 3) / 4);
			}
		}
	}

	/**
	 * Looping sleep that wakes up at a given interval to check whether to wake up.
	 * @param wakeUp the wake up indicator.
	 * @param interval the interval in milliseconds.
	 */
	public static void sleepUntil(AtomicBoolean wakeUp, long interval) {
		while (!wakeUp.get()) {
			sleep(interval);
		}
	}

	/**
	 * Looping sleep that wakes up at a given interval to check whether to wake up.
	 * @param wakeUp the wake up indicator.
	 * @param interval the interval in the given time unit.
	 * @param unit the time unit.
	 */
	public static void sleepUntil(AtomicBoolean wakeUp, long interval, TimeUnit unit) {
		sleepUntil(wakeUp, new UnitTime(interval, unit));
	}

	/**
	 * Looping sleep that wakes up at a given interval to check whether to wake up.
	 * @param wakeUp the wake up indicator.
	 * @param interval the interval.
	 */
	public static void sleepUntil(AtomicBoolean wakeUp, UnitTime interval) {
		sleepUntil(wakeUp, interval.toMillis());
	}

	/**
	 * Sleep forever (why would you ever use this?!)
	 */
	public static void sleepForever() {
		sleep(Long.MAX_VALUE);
	}

	/**
	 * Add the given shutdown hook thread.
	 * @see java.lang.Runtime#addShutdownHook(Thread)
	 */
	public static void addShutdownHook(Thread hook) {
		Check.notNull("hook", hook);
		Runtime.getRuntime().addShutdownHook(hook);
	}

	/**
	 * Creates a new fixed thread pool.
	 * @param threadNameFormat the name format for all pooled threads.
	 * @param threadCount the number of threads.
	 */
	public static ExecutorService newFixedThreadPool(String threadNameFormat, int threadCount) {
		ThreadFactory factory = newThreadFactory(threadNameFormat, true);
		return Executors.newFixedThreadPool(threadCount, factory);
	}

	/**
	 * Creates a new fixed thread pool.
	 * @param threadNameFormat the name format for all pooled threads.
	 * @param threadCount the number of threads.
	 */
	public static ExecutorService newFixedThreadPool(String threadNameFormat, int threadCount, boolean daemon) {
		ThreadFactory factory = newThreadFactory(threadNameFormat, daemon);
		return Executors.newFixedThreadPool(threadCount, factory);
	}

	/**
	 * Creates a new thread factory.
	 * @param threadNameFormat the name format for created threads.
	 * @param daemon true to indicate the threads should all be daemon threads.
	 */
	public static ThreadFactory newThreadFactory(String threadNameFormat, boolean daemon) {
		ThreadFactoryBuilder builder = new ThreadFactoryBuilder();
		builder.setNameFormat(threadNameFormat);
		builder.setDaemon(daemon);
		return builder.build();
	}

	public static Thread start(String name, Runnable runnable) {
		Thread thread = new Thread(runnable, name);
		thread.start();
		return thread;
	}

	public static Thread start(Runnable runnable) {
		String name = runnable.getClass().getSimpleName();
		return start(name, runnable);
	}

	public static void shutdownService(ExecutorService service, long amount, TimeUnit unit) {
		try {
			service.shutdown();
			service.awaitTermination(amount, unit);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	public static <F> void drainAndShutdown(ExecutorService service, List<Future<F>> futures, Collection<F> desination) {
		try {
			for (Future<F> future : futures) {
				desination.add(future.get());
			}
			service.shutdown();
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}
}

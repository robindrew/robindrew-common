package com.robindrew.common.concurrent.executor;

import static com.robindrew.common.util.Java.propagate;
import static java.util.Collections.emptyList;
import static java.util.Collections.emptyMap;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.concurrent.CallableRunnable;
import com.robindrew.common.util.Quietly;

public class ExecutorList<T> implements IExecutorList<T> {

	private static final Logger log = LoggerFactory.getLogger(ExecutorList.class);

	private final List<Callable<T>> set = new ArrayList<Callable<T>>();
	private final String name;

	public ExecutorList(String name) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		this.name = name;
	}

	@Override
	public int size() {
		return set.size();
	}

	@Override
	public boolean isEmpty() {
		return size() == 0;
	}

	@Override
	public Callable<T> add(Runnable runnable) {
		Callable<T> callable = new CallableRunnable<T>(runnable);
		add(callable);
		return callable;
	}

	@Override
	public void add(Callable<T> callable) {
		set.add(callable);
	}

	@Override
	public synchronized Map<Callable<T>, Future<T>> execute(ExecutorService executor) {
		if (set.isEmpty()) {
			return emptyMap();
		}

		Map<Callable<T>, Future<T>> futureMap = new LinkedHashMap<Callable<T>, Future<T>>();
		for (Callable<T> callable : set) {
			Future<T> future = executor.submit(callable);
			futureMap.put(callable, future);
		}

		// Wait for futures to complete
		for (Future<T> future : futureMap.values()) {
			try {
				future.get();
			} catch (Exception e) {
				handle(e);
			}
		}
		return futureMap;
	}

	protected void handle(Exception e) {
		log.warn("Execution failed", e);
	}

	@Override
	public synchronized List<T> getList(ExecutorService executor) {
		if (set.isEmpty()) {
			return emptyList();
		}

		List<Future<T>> futureList = new ArrayList<Future<T>>(set.size());
		for (Callable<T> callable : set) {
			Future<T> future = executor.submit(callable);
			futureList.add(future);
		}

		// Wait for futures to complete
		List<T> list = new ArrayList<T>(set.size());
		try {
			for (Future<T> future : futureList) {
				list.add(future.get());
			}
		} catch (Exception e) {
			throw propagate(e);
		}
		return list;
	}

	@Override
	public Map<Callable<T>, Future<T>> execute(int threads) {
		if (set.isEmpty()) {
			return emptyMap();
		}
		if (threads < 0) {
			throw new IllegalArgumentException("threads=" + threads);
		}

		// Execute with fixed thread pool
		ExecutorService executor = getExecutor(threads);
		try {
			return execute(executor);
		} catch (Exception e) {
			throw propagate(e);
		} finally {
			Quietly.shutdown(executor);
		}
	}

	private ExecutorService getExecutor(int threads) {
		if (threads < 1) {
			return new CachedThreadPoolBuilder(name).get();
		} else {
			return new FixedThreadPoolBuilder(name, threads).get();
		}
	}

	@Override
	public Map<Callable<T>, Future<T>> execute() {
		return execute(set.size());
	}

	@Override
	public List<T> getList() {
		return getList(set.size());
	}

	@Override
	public List<T> getList(int threads) {
		if (set.isEmpty()) {
			return emptyList();
		}
		if (threads < 0) {
			throw new IllegalArgumentException("threads=" + threads);
		}

		// Execute with fixed thread pool
		ExecutorService executor = getExecutor(threads);
		try {
			return getList(executor);
		} catch (Exception e) {
			throw propagate(e);
		} finally {
			Quietly.shutdown(executor);
		}
	}

}

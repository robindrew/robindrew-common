package com.robindrew.common.concurrent.executor;

import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public interface IExecutorList<T> {

	int size();

	boolean isEmpty();

	Callable<T> add(Runnable runnable);

	void add(Callable<T> callable);

	Map<Callable<T>, Future<T>> execute();

	Map<Callable<T>, Future<T>> execute(int threads);

	Map<Callable<T>, Future<T>> execute(ExecutorService executor);

	List<T> getList();

	List<T> getList(int threads);

	List<T> getList(ExecutorService executor);

}

package com.robindrew.common.concurrent;

public interface ICountDown {

	void increment();

	void decrement();

	void set(long count);

	long get();

	void waitFor();

	boolean waiting();
}
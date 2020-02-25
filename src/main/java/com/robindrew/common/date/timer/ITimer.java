package com.robindrew.common.date.timer;

import java.util.concurrent.TimeUnit;

import com.robindrew.common.date.duration.IDuration;

public interface ITimer {

	long getStartTime();

	long getStopTime();

	void reset();

	ITimer start();

	boolean isStarted();

	ITimer stop();

	boolean isStopped();

	long elapsed();

	long elapsed(TimeUnit unit);

	boolean hasExceeded(long duration);

	boolean hasExceeded(long duration, TimeUnit unit);

	TimeUnit getTimeUnit();

	IDuration getDuration();
}

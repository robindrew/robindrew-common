package com.robindrew.common.date.timer;

public interface ITimeCounter {

	void increment(long time);

	int getNumber();

	long getTotalTime();

	long getAverageTime();

	void reset();

}

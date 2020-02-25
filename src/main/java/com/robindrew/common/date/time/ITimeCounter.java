package com.robindrew.common.date.time;

public interface ITimeCounter {

	void increment(long time);

	int getNumber();

	long getTotalTime();

	long getAverageTime();

	void reset();

}

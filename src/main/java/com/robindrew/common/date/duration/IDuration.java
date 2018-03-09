package com.robindrew.common.date.duration;

import java.util.concurrent.TimeUnit;

import com.robindrew.common.date.UnitTime;

public interface IDuration extends Comparable<IDuration> {

	long getDuration();

	long getDuration(TimeUnit unit);

	TimeUnit getTimeUnit();

	UnitTime toUnitTime();

	long getNanos();

	long getMillis();

	long getSeconds();
}

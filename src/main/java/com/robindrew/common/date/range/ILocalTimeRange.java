package com.robindrew.common.date.range;

import java.time.LocalTime;

public interface ILocalTimeRange {

	LocalTime getFrom();

	LocalTime getTo();

	boolean contains(LocalTime time);

}

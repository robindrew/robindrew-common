package com.robindrew.common.date.range;

import java.time.LocalTime;

public class LocalTimeRange implements ILocalTimeRange {

	private final LocalTime from;
	private final LocalTime to;

	public LocalTimeRange(LocalTime from, LocalTime to) {
		if (from == null) {
			throw new NullPointerException("from");
		}
		if (to == null) {
			throw new NullPointerException("to");
		}
		if (from.isAfter(to)) {
			throw new IllegalArgumentException("from=" + from + ", to=" + to);
		}
		this.from = from;
		this.to = to;
	}

	@Override
	public LocalTime getFrom() {
		return from;
	}

	@Override
	public LocalTime getTo() {
		return to;
	}

	@Override
	public boolean contains(LocalTime date) {
		return !date.isBefore(from) && !date.isAfter(to);
	}

}

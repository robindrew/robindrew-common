package com.robindrew.common.date.range;

import java.time.LocalDateTime;

public class LocalDateTimeRange implements ILocalDateTimeRange {

	private final LocalDateTime from;
	private final LocalDateTime to;

	public LocalDateTimeRange(LocalDateTime from, LocalDateTime to) {
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
	public LocalDateTime getFrom() {
		return from;
	}

	@Override
	public LocalDateTime getTo() {
		return to;
	}

	@Override
	public boolean contains(LocalDateTime date) {
		return !date.isBefore(from) && !date.isAfter(to);
	}

}

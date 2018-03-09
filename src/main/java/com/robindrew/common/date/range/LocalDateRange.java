package com.robindrew.common.date.range;

import java.time.LocalDate;

public class LocalDateRange implements ILocalDateRange {

	private final LocalDate from;
	private final LocalDate to;

	public LocalDateRange(LocalDate from, LocalDate to) {
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
	public LocalDate getFrom() {
		return from;
	}

	@Override
	public LocalDate getTo() {
		return to;
	}

	@Override
	public boolean contains(LocalDate date) {
		return !date.isBefore(from) && !date.isAfter(to);
	}

}

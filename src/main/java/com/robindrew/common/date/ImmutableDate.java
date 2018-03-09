package com.robindrew.common.date;

import java.util.Date;

/**
 * An Immutable Date.
 */
public final class ImmutableDate extends Date {

	private static final long serialVersionUID = 7460922728836911359L;

	/**
	 * Creates a new immutable date.
	 * @param date the date.
	 */
	public ImmutableDate(Date date) {
		this(date.getTime());
	}

	/**
	 * Creates a new immutable date.
	 * @param date the date and time.
	 */
	public ImmutableDate(long date) {
		super(date);
	}

	@Override
	public final void setTime(long date) {
		throw new IllegalStateException("date is immutable");
	}

	@Override
	@Deprecated
	public final void setYear(int year) {
		throw new IllegalStateException("date is immutable");
	}

	@Override
	@Deprecated
	public final void setMonth(int month) {
		throw new IllegalStateException("date is immutable");
	}

	@Override
	@Deprecated
	public final void setDate(int day) {
		throw new IllegalStateException("date is immutable");
	}

	@Override
	@Deprecated
	public final void setHours(int hours) {
		throw new IllegalStateException("date is immutable");
	}

	@Override
	@Deprecated
	public final void setMinutes(int minutes) {
		throw new IllegalStateException("date is immutable");
	}

	@Override
	@Deprecated
	public final void setSeconds(int seconds) {
		throw new IllegalStateException("date is immutable");
	}

	@Override
	public Object clone() {
		return this;
	}
}

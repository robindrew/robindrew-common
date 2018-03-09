package com.robindrew.common.date;

public enum Interval {

	/** Seconds. */
	SECOND(1000l),
	/** Minutes. */
	MINUTE(1000l * 60),
	/** Hours. */
	HOUR(1000l * 60 * 60),
	/** Days. */
	DAY(1000l * 60 * 60 * 24),
	/** Weeks. */
	WEEK(1000l * 60 * 60 * 24 * 7),
	/** Months. */
	MONTH(1000l * 60 * 60 * 24 * 30),
	/** Years. */
	YEAR(1000l * 60 * 60 * 24 * 365),
	/** Years. */
	DECADE(1000l * 60 * 60 * 24 * 365 * 10);

	private final long millis;

	private Interval(long millis) {
		this.millis = millis;
	}

	public long toMillis(long amount) {
		return millis * amount;
	}

	public long toSeconds(long amount) {
		return toMillis(amount) / (1000l);
	}

	public long toMinutes(long amount) {
		return toMillis(amount) / (1000l * 60);
	}

	public long toHours(long amount) {
		return toMillis(amount) / (1000l * 60 * 60);
	}

	public long toDays(long amount) {
		return toMillis(amount) / (1000l * 60 * 60 * 24);
	}

}

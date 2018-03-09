package com.robindrew.common.date;

import java.util.concurrent.TimeUnit;

/**
 * A Delay.
 */
public class Delay {

	/** The delay in millis. */
	private final long delayInMillis;
	/** The last expiry time. */
	private long lastExpiryTime = 0;

	/**
	 * Creates a new delay.
	 * @param amount the amount.
	 * @param unit the time unit.
	 */
	public Delay(long amount, TimeUnit unit) {
		if (amount < 1) {
			throw new IllegalArgumentException("delay=" + amount);
		}
		this.delayInMillis = unit.toMillis(amount);
		this.lastExpiryTime = System.currentTimeMillis();
	}

	/**
	 * Creates a new delay.
	 * @param unit the time unit.
	 */
	public Delay(UnitTime unit) {
		this(unit.getTime(), unit.getUnit());
	}

	/**
	 * Reset the last expiry time.
	 */
	public void reset() {
		lastExpiryTime = System.currentTimeMillis();
	}

	/**
	 * Returns the time elapsed since last reset (or created).
	 * @return the time elapsed.
	 */
	public long elapsed() {
		long elapsed = System.currentTimeMillis() - lastExpiryTime;
		return (elapsed < 0) ? 0 : elapsed;

	}

	/**
	 * Returns true if the delay has expired.
	 * @param resetIfExpired true to reset the expiry time if expired.
	 * @return true if the delay has expired.
	 */
	public boolean expired(boolean resetIfExpired) {
		long timeNow = System.currentTimeMillis();
		if (lastExpiryTime + delayInMillis > timeNow) {
			return false;
		}
		if (resetIfExpired) {
			lastExpiryTime = System.currentTimeMillis();
		}
		return true;
	}
}

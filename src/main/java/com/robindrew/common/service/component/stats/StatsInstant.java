package com.robindrew.common.service.component.stats;

import com.robindrew.common.text.Strings;
import com.robindrew.common.util.Check;

public class StatsInstant implements IStatsInstant {

	private final String key;
	private final long value;
	private final long timestamp;

	public StatsInstant(String key, long value, long timestamp) {
		this.key = Check.notEmpty("key", key);
		this.value = value;
		this.timestamp = timestamp;
	}

	public StatsInstant(IStatsInstant instant) {
		this.key = instant.getKey();
		this.value = instant.getValue();
		this.timestamp = instant.getTimestamp();
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public long getTimestamp() {
		return timestamp;
	}

	@Override
	public long getValue() {
		return value;
	}

	@Override
	public String toString() {
		return Strings.toString(this);
	}

	@Override
	public int hashCode() {
		return (int) (key.hashCode() + value + timestamp);
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object instanceof IStatsInstant) {
			IStatsInstant that = (IStatsInstant) object;
			if (this.getTimestamp() != that.getTimestamp()) {
				return false;
			}
			if (this.getValue() != that.getValue()) {
				return false;
			}
			return this.getKey().equals(that.getKey());
		}
		return false;
	}

}

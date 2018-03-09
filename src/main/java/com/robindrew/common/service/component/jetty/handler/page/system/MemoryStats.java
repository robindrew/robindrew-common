package com.robindrew.common.service.component.jetty.handler.page.system;

import java.util.LinkedHashSet;
import java.util.Set;

import com.robindrew.common.service.component.stats.IStatsInstant;
import com.robindrew.common.service.component.stats.StatsInstantSet;
import com.robindrew.common.text.Strings;

public class MemoryStats {

	public static Set<MemoryStats> toSet(StatsInstantSet usedSet, StatsInstantSet maxSet) {
		Set<MemoryStats> set = new LinkedHashSet<>();
		for (long timestamp : usedSet.getTimestamps()) {
			IStatsInstant used = usedSet.getInstant(timestamp);
			IStatsInstant max = maxSet.getInstant(timestamp);
			if (used != null && max != null) {
				set.add(new MemoryStats(used, max));
			}
		}
		return set;
	}

	private final IStatsInstant used;
	private final IStatsInstant max;

	public MemoryStats(IStatsInstant used, IStatsInstant max) {
		if (used.getTimestamp() != max.getTimestamp()) {
			throw new IllegalArgumentException("used=" + used + ", max=" + max);
		}
		this.used = used;
		this.max = max;
	}

	public String getTime() {
		return Strings.date(used.getTimestamp(), "HH:mm");
	}

	public long getUsed() {
		return used.getValue() / (1024 * 1024);
	}

	public long getMax() {
		return max.getValue() / (1024 * 1024);
	}

}

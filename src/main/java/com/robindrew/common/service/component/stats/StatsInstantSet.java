package com.robindrew.common.service.component.stats;

import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import com.google.common.collect.ImmutableSet;

public class StatsInstantSet {

	private final String key;
	private final Map<Long, IStatsInstant> instantMap = new TreeMap<>();

	public StatsInstantSet(Set<IStatsInstant> instants) {
		if (instants.isEmpty()) {
			throw new IllegalArgumentException("instants is empty");
		}
		this.key = instants.iterator().next().getKey();

		for (IStatsInstant instant : instants) {
			if (!instant.getKey().equals(key)) {
				throw new IllegalArgumentException("key=" + key + ", instants=" + instants);
			}
			instantMap.put(instant.getTimestamp(), instant);
		}
	}

	public Set<Long> getTimestamps() {
		return ImmutableSet.copyOf(instantMap.keySet());
	}

	public IStatsInstant getInstant(long timestamp) {
		return instantMap.get(timestamp);
	}
}

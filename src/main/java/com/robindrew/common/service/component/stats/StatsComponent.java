package com.robindrew.common.service.component.stats;

import static com.robindrew.common.dependency.DependencyFactory.setDependency;
import static java.util.concurrent.TimeUnit.SECONDS;

import com.robindrew.common.properties.map.type.IProperty;
import com.robindrew.common.properties.map.type.IntegerProperty;
import com.robindrew.common.service.component.AbstractScheduledComponent;
import com.robindrew.common.util.Java;

/**
 * The stats component.
 */
public class StatsComponent extends AbstractScheduledComponent {

	public static final String JAVA_HEAP_MAX = "javaHeapMax";
	public static final String JAVA_HEAP_USED = "javaHeapUsed";
	public static final String JAVA_HEAP_FREE = "javaHeapFree";

	public static final String SYSTEM_MEMORY_MAX = "systemMemoryMax";
	public static final String SYSTEM_MEMORY_USED = "systemMemoryUsed";
	public static final String SYSTEM_MEMORY_FREE = "systemMemoryFree";

	private static final IProperty<Integer> heartbeatFrequencySeconds = new IntegerProperty("stats.frequency.seconds").defaultValue(60);

	private final IStatsCache cache = new StatsCache(8);

	@Override
	protected void startupComponent() throws Exception {
		setDependency(IStatsCache.class, cache);
	}

	@Override
	protected void shutdownComponent() throws Exception {
		// Nothing to do ...
	}

	@Override
	protected void runOneIteration() throws Exception {
		long timestamp = (System.currentTimeMillis() / 1000) * 1000;

		setJavaHeapStats(timestamp);
		setSystemMemoryStats(timestamp);
	}

	private void setJavaHeapStats(long timestamp) {
		long free = Java.freeMemory();
		long max = Java.maxMemory();
		long used = max - free;

		IStatsInstant javaHeapUsed = new StatsInstant(JAVA_HEAP_USED, used, timestamp);
		IStatsInstant javaHeapFree = new StatsInstant(JAVA_HEAP_FREE, free, timestamp);
		IStatsInstant javaHeapMax = new StatsInstant(JAVA_HEAP_MAX, max, timestamp);

		cache.put(javaHeapUsed);
		cache.put(javaHeapFree);
		cache.put(javaHeapMax);
	}

	private void setSystemMemoryStats(long timestamp) {
		long free = Java.getSystemFreeMemory(0);
		long max = Java.getSystemMaxMemory(1);
		long used = max - free;

		IStatsInstant systemMemoryUsed = new StatsInstant(SYSTEM_MEMORY_USED, used, timestamp);
		IStatsInstant systemMemoryFree = new StatsInstant(SYSTEM_MEMORY_FREE, free, timestamp);
		IStatsInstant systemMemoryMax = new StatsInstant(SYSTEM_MEMORY_MAX, max, timestamp);

		cache.put(systemMemoryUsed);
		cache.put(systemMemoryFree);
		cache.put(systemMemoryMax);
	}

	@Override
	protected Scheduler scheduler() {
		int frequency = heartbeatFrequencySeconds.get();
		return Scheduler.newFixedRateSchedule(0, frequency, SECONDS);
	}

	public IStatsCache getStatsCache() {
		return cache;
	}

}

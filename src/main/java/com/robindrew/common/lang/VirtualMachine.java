package com.robindrew.common.lang;

import static com.robindrew.common.lang.GarbageCollector.getGarbageCollectors;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import com.google.common.collect.ImmutableList;

public class VirtualMachine {

	private final long timestamp;

	private final int availableProcessors;

	private final long heapMemoryFree;
	private final long heapMemoryTotal;
	private final long heapMemoryMax;

	private final List<GarbageCollector> collectors;

	public VirtualMachine(Runtime runtime) {

		// Timestamp
		this.timestamp = System.currentTimeMillis();

		// Processors
		this.availableProcessors = runtime.availableProcessors();

		// Heap Memory
		this.heapMemoryFree = runtime.freeMemory();
		this.heapMemoryTotal = runtime.totalMemory();
		this.heapMemoryMax = runtime.maxMemory();

		// Garbage Collection
		this.collectors = getGarbageCollectors();
	}

	public long getTimestamp() {
		return timestamp;
	}

	public int getAvailableProcessors() {
		return availableProcessors;
	}

	public long getHeapMemoryFree() {
		return heapMemoryFree;
	}

	public long getHeapMemoryTotal() {
		return heapMemoryTotal;
	}

	public long getHeapMemoryMax() {
		return heapMemoryMax;
	}

	public long getHeapMemoryUsed() {
		return heapMemoryTotal - heapMemoryFree;
	}

	public List<GarbageCollector> getCollectors() {
		return ImmutableList.copyOf(collectors);
	}

	public Map<String, GarbageCollector> getCollectorMap() {
		TreeMap<String, GarbageCollector> map = new TreeMap<>();
		for (GarbageCollector collector : collectors) {
			map.put(collector.getName(), collector);
		}
		return map;
	}

}

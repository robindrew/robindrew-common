package com.robindrew.common.lang;

import static com.robindrew.common.util.Check.notEmpty;
import static java.lang.management.ManagementFactory.getGarbageCollectorMXBeans;

import java.lang.management.GarbageCollectorMXBean;
import java.util.ArrayList;
import java.util.List;

public class GarbageCollector {

	public static List<GarbageCollector> getGarbageCollectors() {
		List<GarbageCollectorMXBean> beans = getGarbageCollectorMXBeans();
		List<GarbageCollector> collectors = new ArrayList<>(beans.size());
		for (GarbageCollectorMXBean bean : beans) {
			collectors.add(new GarbageCollector(bean));
		}
		return collectors;
	}

	private final String name;
	private final long count;
	private final long time;

	public GarbageCollector(GarbageCollectorMXBean bean) {
		this.name = bean.getName();
		this.count = bean.getCollectionCount();
		this.time = bean.getCollectionTime();
	}

	public GarbageCollector(String name, long count, long time) {
		if (count < 0) {
			throw new IllegalArgumentException("count=" + count);
		}
		if (time < 0) {
			throw new IllegalArgumentException("time=" + time);
		}
		this.name = notEmpty("name", name);
		this.count = count;
		this.time = time;
	}

	public String getName() {
		return name;
	}

	public long getCount() {
		return count;
	}

	public long getTime() {
		return time;
	}

	@Override
	public String toString() {
		return "GarbageCollector[name=" + name + ", count=" + count + ", time=" + time + "]";
	}

	public GarbageCollector diff(GarbageCollector collector) {
		if (!getName().equals(collector.getName())) {
			throw new IllegalArgumentException("collector names do not match: " + getName() + " != " + collector.getName());
		}

		long count = Math.abs(getCount() - collector.getCount());
		long time = Math.abs(getTime() - collector.getTime());
		return new GarbageCollector(getName(), count, time);
	}

}

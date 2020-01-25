package com.robindrew.common.service.component.heartbeat;

import static com.robindrew.common.text.Strings.bytes;
import static com.robindrew.common.text.Strings.number;
import static com.robindrew.common.text.Strings.percent;
import static java.util.concurrent.TimeUnit.SECONDS;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.lang.GarbageCollector;
import com.robindrew.common.lang.VirtualMachine;
import com.robindrew.common.properties.map.type.IProperty;
import com.robindrew.common.properties.map.type.IntegerProperty;
import com.robindrew.common.service.component.AbstractScheduledComponent;
import com.robindrew.common.text.Strings;

/**
 * The heartbeat component.
 */
public class HeartbeatComponent extends AbstractScheduledComponent {

	private static final Logger log = LoggerFactory.getLogger(HeartbeatComponent.class);

	private static final IProperty<Integer> propertyFrequency = new IntegerProperty("heartbeat.frequency.seconds").defaultValue(20);

	private final Runtime runtime = Runtime.getRuntime();
	private volatile VirtualMachine previous = null;

	@Override
	protected void startupComponent() throws Exception {
		// Nothing to do ...

		VirtualMachine latest = new VirtualMachine(runtime);

		log.info("[Processors] {}", latest.getAvailableProcessors());
		log.info("[Heap] {}", bytes(latest.getHeapMemoryMax()));

		for (GarbageCollector collector : latest.getCollectors()) {
			log.info("[Collector] {}", collector.getName());
		}

		previous = latest;
	}

	@Override
	protected void shutdownComponent() throws Exception {
		// Nothing to do ...
	}

	@Override
	protected void runOneIteration() throws Exception {

		VirtualMachine latest = new VirtualMachine(runtime);

		// Heap
		reportHeap(latest);

		// Garbage!
		reportGarbageCollection(previous.getCollectorMap(), latest.getCollectorMap());

		previous = latest;
	}

	private void reportGarbageCollection(Map<String, GarbageCollector> previousMap, Map<String, GarbageCollector> latestMap) {
		for (String name : latestMap.keySet()) {
			GarbageCollector previous = previousMap.get(name);
			GarbageCollector latest = latestMap.get(name);
			if (previous != null && latest != null) {
				GarbageCollector diff = latest.diff(previous);
				if (diff.getCount() > 0) {
					String plural = (diff.getCount() > 1) ? "s" : "";
					log.info("[Garbage] {} ({} collection{} in {})", diff.getName(), number(diff.getCount()), plural, Strings.time(diff.getTime()));
				}
			}
		}
	}

	private void reportHeap(VirtualMachine latest) {
		long used = latest.getHeapMemoryUsed();
		long total = latest.getHeapMemoryMax();
		log.info("[Heap] {} Used / {} Max ({})", bytes(used), bytes(total), percent(used, total));
	}

	@Override
	protected Scheduler scheduler() {
		int frequency = propertyFrequency.get();
		return Scheduler.newFixedRateSchedule(0, frequency, SECONDS);
	}

}

package com.robindrew.common.service.component.heartbeat;

import static com.robindrew.common.text.Strings.bytes;
import static com.robindrew.common.text.Strings.percent;
import static java.util.concurrent.TimeUnit.SECONDS;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.robindrew.common.properties.map.type.IProperty;
import com.robindrew.common.properties.map.type.IntegerProperty;
import com.robindrew.common.service.component.AbstractScheduledComponent;
import com.robindrew.common.util.Java;

/**
 * The heartbeat component.
 */
public class HeartbeatComponent extends AbstractScheduledComponent {

	private static final Logger log = LoggerFactory.getLogger(HeartbeatComponent.class);

	private static final IProperty<Integer> propertyFrequency = new IntegerProperty("heartbeat.frequency.seconds").defaultValue(60);

	@Override
	protected void startupComponent() throws Exception {
		// Nothing to do ...
	}

	@Override
	protected void shutdownComponent() throws Exception {
		// Nothing to do ...
	}

	@Override
	protected void runOneIteration() throws Exception {
		long used = Java.usedMemory();
		long total = Java.maxMemory();
		log.info("[Heap] {} Used / {} Max ({})", bytes(used), bytes(total), percent(used, total));
	}

	@Override
	protected Scheduler scheduler() {
		int frequency = propertyFrequency.get();
		return Scheduler.newFixedRateSchedule(0, frequency, SECONDS);
	}

}

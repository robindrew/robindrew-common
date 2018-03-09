package com.robindrew.common.concurrent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import com.robindrew.common.util.Check;

public class LoopingEventConsumerThread<E> extends LoopingThread {

	private final BlockingDeque<E> queue = new LinkedBlockingDeque<>();
	private final IEventConsumer<E> consumer;

	public LoopingEventConsumerThread(String name, IEventConsumer<E> consumer) {
		super(name);
		this.consumer = Check.notNull("consumer", consumer);
	}

	@Override
	public void runOneIteration() {
		List<E> eventList = new ArrayList<>();
		queue.drainTo(eventList);
		if (!eventList.isEmpty()) {
			consumer.consumeEvents(eventList);
		}
	}

	public void publishEvent(E event) {
		if (isClosed()) {
			throw new IllegalStateException("Thread is closed");
		}
		Check.notNull("event", event);
		queue.addLast(event);
	}

	public void publishEvents(Collection<? extends E> events) {
		if (isClosed()) {
			throw new IllegalStateException("Thread is closed");
		}
		Check.notEmpty("events", events);
		queue.addAll(events);
	}

}

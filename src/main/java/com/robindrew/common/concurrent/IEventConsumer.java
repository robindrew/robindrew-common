package com.robindrew.common.concurrent;

import java.util.List;

public interface IEventConsumer<E> {

	void consumeEvents(List<E> events);

}

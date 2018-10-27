package com.robindrew.common.collect.capacity;

import java.util.Collection;
import java.util.List;

public interface ICapacityList<E> extends Collection<E> {

	int getCapacity();

	List<E> toList();

}

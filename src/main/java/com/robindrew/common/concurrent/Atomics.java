package com.robindrew.common.concurrent;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiPredicate;

public class Atomics {

	public static <V> boolean setConditional(AtomicReference<V> reference, V newValue, BiPredicate<V, V> condition) {

		// Lock-free guaranteed set (if condition holds)
		while (true) {
			V oldValue = reference.get();
			if (!condition.test(oldValue, newValue)) {
				return false;
			}
			if (reference.compareAndSet(oldValue, newValue)) {
				return true;
			}
		}
	}

}

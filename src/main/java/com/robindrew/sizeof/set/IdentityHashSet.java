package com.robindrew.sizeof.set;

import java.util.AbstractSet;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * An Identity Set
 */
public final class IdentityHashSet extends AbstractSet<Object> {

    /**
     * The instance map.
     */
    private final Map<Object, Object> instanceMap = new IdentityHashMap<Object, Object>();
    /**
     * Place-holder in the map to indicate the presence of a value.
     */
    private static final Object PRESENT = new Object();

    /**
     * Clear the set.
     */
    @Override
    public void clear() {
        instanceMap.clear();
    }

    /**
     * Returns the size of the set.
     *
     * @return the size of the set.
     */
    @Override
    public int size() {
        return instanceMap.size();
    }

    /**
     * Returns an iterator over the values.
     *
     * @return an iterator over the values.
     */
    @Override
    public Iterator<Object> iterator() {
        return instanceMap.keySet().iterator();
    }

    /**
     * Put the given instance in the map.
     *
     * @param instance the instance.
     * @return true if not already in the map.
     */
    @Override
    public final boolean add(Object instance) {
        return instanceMap.put(instance, PRESENT) == null;
	}

}

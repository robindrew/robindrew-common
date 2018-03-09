package com.robindrew.common.io.file.objectstore;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.ImmutableList;

public abstract class CachedObjectStoreFile<E> extends ObjectStoreFile<E> implements Iterable<E> {

	private volatile List<E> cached = null;

	protected CachedObjectStoreFile(File file) {
		super(file);
	}

	@Override
	public List<E> getAll() {
		if (cached == null) {
			cached = super.getAll();
		}
		return cached;
	}

	@Override
	public void setAll(Collection<? extends E> elements) {
		super.setAll(elements);
		cached = ImmutableList.copyOf(elements);
	}

	@Override
	public Iterator<E> iterator() {
		return getAll().iterator();
	}

}

package com.robindrew.common.io.file;

import java.io.File;

import com.google.common.base.Optional;

public interface ICachedFile<V> {

	File getFile();

	boolean isSet();

	V get();

	Optional<V> get(boolean readFromFile);

	void set(V value);

}

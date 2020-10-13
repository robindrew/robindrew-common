package com.robindrew.common.io.file.reference;

import java.io.File;
import java.io.IOException;
import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

import com.google.common.base.Optional;
import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.robindrew.common.io.file.ICachedFile;
import com.robindrew.common.io.persister.IObjectPersister;
import com.robindrew.common.util.Java;

public class CachedFileReference<V> implements ICachedFile<V> {

	public static enum ReferenceType {
		WEAK, SOFT;
	}

	private final File file;
	private final IObjectPersister<V> persister;
	private volatile ReferenceType type = ReferenceType.SOFT;
	private volatile Reference<V> reference;

	public CachedFileReference(File file, IObjectPersister<V> persister) {
		if (file == null) {
			throw new NullPointerException("file");
		}
		if (persister == null) {
			throw new NullPointerException("persister");
		}
		this.file = file;
		this.persister = persister;
	}

	@Override
	public File getFile() {
		return file;
	}

	@Override
	public V get() {
		Optional<V> value = get(true);
		if (!value.isPresent()) {
			throw new IllegalStateException("reference not set");
		}
		return value.get();
	}

	@Override
	public Optional<V> get(boolean readFromFile) {
		if (reference == null) {
			return Optional.absent();
		}

		V value;
		synchronized (this) {
			value = reference.get();
			if (value == null && readFromFile) {
				value = readFromFile();
				reference = newReference(value);
			}
		}
		return Optional.fromNullable(value);
	}

	@Override
	public void set(V value) {
		if (value == null) {
			throw new NullPointerException("value");
		}
		synchronized (this) {
			writeToFile(value);
			this.reference = newReference(value);
		}
	}

	private V readFromFile() {
		ByteSource source = Files.asByteSource(file);
		try {
			return persister.readFrom(source);
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	private void writeToFile(V value) {
		ByteSink sink = Files.asByteSink(file);
		try {
			persister.writeTo(sink, value);
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	private Reference<V> newReference(V value) {
		switch (type) {
			case SOFT:
				return new SoftReference<V>(value);
			case WEAK:
				return new WeakReference<V>(value);
			default:
				throw new IllegalStateException("reference type not supported: " + type);
		}
	}

	@Override
	public boolean isSet() {
		return reference != null;
	}

}

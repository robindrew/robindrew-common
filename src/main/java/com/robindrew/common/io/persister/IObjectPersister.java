package com.robindrew.common.io.persister;

import java.io.IOException;

import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;

public interface IObjectPersister<V> {

	void writeTo(ByteSink sink, V value) throws IOException;

	V readFrom(ByteSource source) throws IOException;

}

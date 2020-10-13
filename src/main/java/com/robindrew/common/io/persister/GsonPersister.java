package com.robindrew.common.io.persister;

import static com.google.common.base.Charsets.UTF_8;

import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.nio.charset.Charset;

import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.google.gson.Gson;

public class GsonPersister<V> implements IObjectPersister<V> {

	private final Gson gson;
	private final Class<V> type;
	private Charset charset = UTF_8;

	public GsonPersister(Class<V> type) {
		this(type, new Gson());
	}

	public GsonPersister(Class<V> type, Gson gson) {
		if (type == null) {
			throw new NullPointerException("type");
		}
		if (gson == null) {
			throw new NullPointerException("gson");
		}
		this.type = type;
		this.gson = gson;
	}

	@Override
	public void writeTo(ByteSink sink, V value) throws IOException {
		try (Writer writer = sink.asCharSink(charset).openBufferedStream()) {
			gson.toJson(value, writer);
		}
	}

	@Override
	public V readFrom(ByteSource source) throws IOException {
		try (Reader reader = source.asCharSource(charset).openBufferedStream()) {
			return gson.fromJson(reader, type);
		}
	}

}

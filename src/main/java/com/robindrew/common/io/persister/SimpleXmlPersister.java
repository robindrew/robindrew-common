package com.robindrew.common.io.persister;

import static com.google.common.base.Charsets.UTF_8;

import java.io.IOException;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.Charset;

import org.simpleframework.xml.core.Persister;

import com.google.common.io.ByteSink;
import com.google.common.io.ByteSource;
import com.robindrew.common.util.Java;

public class SimpleXmlPersister<V> implements IObjectPersister<V> {

	private final Persister persister;
	private final Class<V> type;
	private Charset charset = UTF_8;

	public SimpleXmlPersister(Class<V> type) {
		this(type, new Persister());
	}

	public SimpleXmlPersister(Class<V> type, Persister persister) {
		if (type == null) {
			throw new NullPointerException("type");
		}
		if (persister == null) {
			throw new NullPointerException("persister");
		}
		this.type = type;
		this.persister = persister;
	}

	@Override
	public void writeTo(ByteSink sink, V value) throws IOException {
		try (Writer writer = sink.asCharSink(charset).openBufferedStream()) {
			persister.write(value, writer);
		} catch (Exception e) {
			throw new IOException("Failed to write value of type " + type + " to persister", e);
		}
	}

	@Override
	public V readFrom(ByteSource source) throws IOException {
		try (Reader reader = source.asCharSource(charset).openBufferedStream()) {
			return persister.read(type, reader);
		} catch (Exception e) {
			throw new IOException("Failed to read value of type " + type + " from persister", e);
		}
	}
	
	public String writeToString(V value) {
		try {
			StringWriter writer = new StringWriter();
			persister.write(value, writer);
			return writer.toString();
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

}

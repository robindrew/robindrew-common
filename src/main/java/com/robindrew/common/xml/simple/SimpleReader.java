package com.robindrew.common.xml.simple;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.io.StringReader;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

public class SimpleReader implements ISimpleReader {

	private final Serializer serializer = new Persister();

	@Override
	public <T> T readFile(Class<T> type, File file) {
		try {
			synchronized (serializer) {
				return serializer.read(type, file);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to read file: " + file, e);
		}
	}

	@Override
	public <T> T readFile(Class<T> type, String filename) {
		return readFile(type, new File(filename));
	}

	@Override
	public <T> T readFile(T instance, File file) {
		try {
			synchronized (serializer) {
				return serializer.read(instance, file);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to read file: " + file, e);
		}
	}

	@Override
	public <T> T readFile(T instance, String filename) {
		return readFile(instance, new File(filename));
	}

	@Override
	public <T> T readString(Class<T> type, String xml) {
		try {
			Reader reader = new StringReader(xml);
			synchronized (serializer) {
				return serializer.read(type, reader);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse: " + xml, e);
		}
	}

	@Override
	public <T> T readString(T instance, String xml) {
		try {
			Reader reader = new StringReader(xml);
			synchronized (serializer) {
				return serializer.read(instance, reader);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to parse: " + xml, e);
		}
	}

	@Override
	public <T> T readResource(T instance, String name) {
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(name)) {
			if (input == null) {
				throw new IllegalArgumentException("Resource not found: '" + name + "'");
			}
			synchronized (serializer) {
				return serializer.read(instance, input);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to read resource: " + name, e);
		}
	}

	@Override
	public <T> T readResource(Class<T> type, String name) {
		try (InputStream input = getClass().getClassLoader().getResourceAsStream(name)) {
			if (input == null) {
				throw new IllegalArgumentException("Resource not found: '" + name + "'");
			}
			synchronized (serializer) {
				return serializer.read(type, input);
			}
		} catch (Exception e) {
			throw new RuntimeException("Failed to read resource: " + name, e);
		}
	}

}

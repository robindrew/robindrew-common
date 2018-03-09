package com.robindrew.common.xml.simple;

import java.io.File;
import java.io.StringWriter;
import java.io.Writer;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import com.robindrew.common.util.Java;

public class SimpleWriter implements ISimpleWriter {

	private final Serializer serializer = new Persister();

	@Override
	public <T> String writeToString(T instance) {
		try {
			Writer writer = new StringWriter();
			synchronized (serializer) {
				serializer.write(instance, writer);
			}
			return writer.toString();
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public <T> void writeToFile(T instance, String filename) {
		writeToFile(instance, new File(filename));
	}

	@Override
	public <T> void writeToFile(T instance, File file) {
		try {
			synchronized (serializer) {
				serializer.write(instance, file);
			}
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

}

package com.robindrew.common.xml.xstream;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.TreeMap;
import java.util.TreeSet;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.XStreamException;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

public class AliasXStream implements IXStream {

	private final XStream xstream;

	public AliasXStream() {
		this.xstream = new XStream(new DomDriver());

		// Primitives
		attribute(byte.class);
		attribute(short.class);
		attribute(int.class);
		attribute(long.class);
		attribute(boolean.class);
		attribute(char.class);
		attribute(float.class);
		attribute(double.class);

		// Objects
		attribute(Byte.class);
		attribute(Short.class);
		attribute(Integer.class);
		attribute(Long.class);
		attribute(Boolean.class);
		attribute(Character.class);
		attribute(Float.class);
		attribute(Double.class);
		attribute(Date.class);

		// SQL
		attribute(java.sql.Date.class);
		attribute(java.sql.Time.class);
		attribute(java.sql.Timestamp.class);

		// Common Aliases
		alias(String.class);
		alias(ArrayList.class);
		alias(LinkedList.class);
		alias(HashMap.class);
		alias(HashSet.class);
		alias(TreeMap.class);
		alias(TreeSet.class);
		alias(LinkedHashMap.class);
		alias(LinkedHashSet.class);
	}

	protected XStream getXStream() {
		return xstream;
	}

	@Override
	public void alias(Class<?> type) {
		String name = type.getSimpleName();
		xstream.alias(name, type);
	}

	@Override
	public void aliasAll(Collection<? extends Class<?>> types) {
		for (Class<?> type : types) {
			alias(type);
		}
	}

	@Override
	public void attribute(Class<?> type) {
		xstream.useAttributeFor(type);
	}

	@Override
	public <T> String toXml(T type) {
		return xstream.toXML(type);
	}

	@Override
	public <T> void toXml(T type, OutputStream output) {
		xstream.toXML(type, output);
	}

	@Override
	public <T> void toXml(T type, Writer writer) {
		xstream.toXML(type, writer);
	}

	@Override
	public <T> void toXml(T type, File file) {
		try (OutputStream output = new BufferedOutputStream(new FileOutputStream(file))) {
			toXml(type, output);
		} catch (IOException ioe) {
			throw new XStreamException(ioe);
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T fromXml(String xml) {

		return (T) xstream.fromXML(xml);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T fromXml(InputStream input) {

		return (T) xstream.fromXML(input);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T fromXml(Reader reader) {

		return (T) xstream.fromXML(reader);
	}

	@Override
	@SuppressWarnings("unchecked")
	public <T> T fromXml(File file) {
		try {
			try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {
				return (T) fromXml(input);
			}
		} catch (IOException ioe) {
			throw new XStreamException(ioe);
		}
	}

	@Override
	public String encodeToString(Object object) {
		return toXml(object);
	}

	@Override
	public <T> String toXml(T type, boolean formatted) {
		if (!formatted) {
			return toXml(type);
		}
		StringWriter writer = new StringWriter();
		xstream.marshal(type, new PrettyPrintWriter(writer));
		return writer.toString();
	}
}

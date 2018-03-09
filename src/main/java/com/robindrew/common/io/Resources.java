package com.robindrew.common.io;

import static com.google.common.base.Charsets.UTF_8;

import java.io.IOException;
import java.net.URL;

import com.google.common.io.ByteSource;
import com.google.common.io.CharSource;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Java;

public class Resources {

	public static final String toString(String resourceName) {
		Check.notEmpty("resourceName", resourceName);
		try {
			CharSource source = toCharSource(resourceName);
			return source.read();
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	public static final CharSource toCharSource(String resourceName) {
		Check.notEmpty("resourceName", resourceName);
		URL resource = com.google.common.io.Resources.getResource(resourceName);
		if (resource == null) {
			throw new IllegalArgumentException("resource not found on classpath: '" + resourceName + "'");
		}
		return com.google.common.io.Resources.asCharSource(resource, UTF_8);
	}

	public static final ByteSource toByteSource(String resourceName) {
		Check.notEmpty("resourceName", resourceName);
		URL resource = com.google.common.io.Resources.getResource(resourceName);
		if (resource == null) {
			throw new IllegalArgumentException("resource not found on classpath: '" + resourceName + "'");
		}
		return com.google.common.io.Resources.asByteSource(resource);
	}

}

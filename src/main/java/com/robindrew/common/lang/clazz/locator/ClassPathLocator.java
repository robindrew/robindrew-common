package com.robindrew.common.lang.clazz.locator;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Splitter;
import com.google.common.io.ByteStreams;
import com.google.common.io.Files;

public class ClassPathLocator implements IClassLocator {

	private static final Logger log = LoggerFactory.getLogger(ClassPathLocator.class);

	private final Map<String, byte[]> cache = new ConcurrentHashMap<>();
	private final List<String> pathList;

	public ClassPathLocator() {
		String value = System.getProperty("java.class.path");
		String separator = System.getProperty("path.separator");
		pathList = Splitter.on(separator).splitToList(value);
	}

	@Override
	public byte[] locateClass(String className) {
		if (cache.containsKey(className)) {
			return cache.get(className);
		}

		String classFilePath = className.replace('.', '/') + ".class";
		for (String path : pathList) {
			byte[] bytes = null;

			// Load from jar file
			if (path.toLowerCase().endsWith(".jar")) {
				bytes = readFromJar(className, classFilePath, path);
			}

			// Load from regular file
			else {
				bytes = readFromFile(className, classFilePath, path);
			}

			// Cache and return the binary class
			if (bytes != null) {
				cache.put(className, bytes);
				return bytes;
			}
		}

		// Unable to locate file
		return null;
	}

	private byte[] readFromJar(String className, String classFilePath, String path) {
		try (JarInputStream input = new JarInputStream(new FileInputStream(new File(path)))) {
			while (true) {
				JarEntry entry = input.getNextJarEntry();
				if (entry == null) {
					return null;
				}

				if (entry.getName().equals(classFilePath)) {
					log.info("[Located Class] " + className + " -> " + path + "#" + entry.getName());
					return ByteStreams.toByteArray(input);
				}
			}
		} catch (Exception e) {

			return null;
		}
	}

	private byte[] readFromFile(String className, String classFilePath, String path) {
		File directory = new File(path);
		if (directory.isDirectory() && directory.exists()) {
			File file = new File(directory, classFilePath);
			if (file.exists()) {
				log.info("[Located Class] " + className + " -> " + file);
				try {
					return Files.toByteArray(file);
				} catch (Exception e) {
					log.warn("Failed to read class from file: " + file, e);
				}
			}
		}
		return null;
	}

}

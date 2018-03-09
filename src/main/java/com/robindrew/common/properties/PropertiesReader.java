package com.robindrew.common.properties;

import static com.robindrew.common.util.Check.notNull;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Charsets;
import com.google.common.io.ByteSource;
import com.google.common.io.Files;
import com.robindrew.common.io.Resources;
import com.robindrew.common.util.Java;

public class PropertiesReader {

	private static final Logger log = LoggerFactory.getLogger(PropertiesReader.class);

	public static enum Type {
		FILE, RESOURCE;
	}

	public static final Map<String, String> toMap(java.util.Properties properties) {
		Map<String, String> map = new TreeMap<>();
		for (Entry<Object, Object> entry : properties.entrySet()) {
			Object key = entry.getKey();
			Object value = entry.getValue();
			map.put((String) key, (String) value);
		}
		return map;
	}

	public static final Map<String, String> readFromFile(String filename) {
		return fileReader().readFrom(filename);
	}

	public static final Map<String, String> readFromFile(File file) {
		return fileReader().readFrom(file.getAbsolutePath());
	}

	public static final Map<String, String> readFromResource(String resourceName) {
		return resourceReader().readFrom(resourceName);
	}

	public static final PropertiesReader resourceReader() {
		return new PropertiesReader(Type.RESOURCE);
	}

	public static final PropertiesReader fileReader() {
		return new PropertiesReader(Type.FILE);
	}

	private Charset charset = Charsets.UTF_8;
	private final Type type;

	public PropertiesReader(Type type) {
		this.type = notNull("type", type);
	}

	public Map<String, String> readFrom(String name) {
		try {

			switch (type) {

				// Read from a file?
				case FILE:
					File file = new File(name);
					ByteSource source = Files.asByteSource(file);
					return readFrom(name, source);

				// Read from a resource?
				case RESOURCE:
					source = Resources.toByteSource(name);
					return readFrom(name, source);

				default:
					throw new IllegalStateException("Unsupported type: " + type);
			}

		} catch (Exception e) {
			throw new PropertiesException("Failed to read properties from: '" + name + "'", e);
		}
	}

	public Map<String, String> readFrom(String name, ByteSource source) {
		log.info("[Reading Properties] {}", name);

		boolean xml = name.toLowerCase().endsWith(".xml");
		try (InputStream input = source.openBufferedStream()) {
			Map<String, String> map;

			// XML properties format?
			if (xml) {
				map = readXmlFormat(input);
			}

			// Standard properties format?
			else {
				map = readStandardFormat(input);
			}

			log.info("[Read Properties] {} properties from {}", map.size(), name);
			return map;

		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	private Map<String, String> readXmlFormat(InputStream input) throws Exception {
		java.util.Properties properties = new java.util.Properties();
		properties.loadFromXML(input);
		return toMap(properties);
	}

	private Map<String, String> readStandardFormat(InputStream input) throws Exception {

		int lineNumber = 0;
		Map<String, String> map = new LinkedHashMap<>();
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, charset))) {
			while (true) {
				String line = reader.readLine();

				// End of file?
				if (line == null) {
					break;
				}
				lineNumber++;

				// Read a single property (or import)
				readPropertyOrImport(line, lineNumber, map);
			}
		}
		return map;
	}

	private void readPropertyOrImport(String line, int lineNumber, Map<String, String> map) {

		// Empty line?
		if (line.trim().isEmpty()) {
			return;
		}

		// Comment?
		if (line.startsWith("#")) {

			// Import?
			if (line.toLowerCase().startsWith("#import")) {
				String name = line.substring("#import".length()).trim();
				log.info("[Import Properties] {} (line #{})", name, lineNumber);
				Map<String, String> importMap = readFrom(name);
				map.putAll(importMap);
			}
			return;
		}

		// Property
		int index = getIndex(line, lineNumber);
		String key = line.substring(0, index).trim();
		String value = line.substring(index + 1).trim();
		String override = map.put(key, value);
		if (override == null) {
			log.info("[Property] {} = {}", key, value);
		} else {
			if (!override.equals(value)) {
				log.info("[Override] {} = {}", key, value);
			}
		}
	}

	private int getIndex(String line, int lineNumber) {
		for (int i = 0; i < line.length(); i++) {
			char c = line.charAt(i);
			if (c == '=') {
				return i;
			}
			if (Character.isWhitespace(c)) {
				return i;
			}
		}
		throw new PropertiesException("Unable to locate key/value property separator in line: '" + line + "' (line #" + lineNumber + ")");
	}

}

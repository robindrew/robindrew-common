package com.robindrew.common.io.lineprocessor;

import java.io.IOException;
import java.util.Map;

import com.google.common.io.LineProcessor;

public class MapLineProcessor implements LineProcessor<Map<String, String>> {

	private final Map<String, String> lines;
	private String entrySeparator;

	public MapLineProcessor(Map<String, String> lines, String entrySeparator) {
		if (lines == null) {
			throw new NullPointerException("lines");
		}
		if (entrySeparator.isEmpty()) {
			throw new IllegalArgumentException("entrySeparator");
		}
		this.lines = lines;
		this.entrySeparator = entrySeparator;
	}

	@Override
	public boolean processLine(String line) throws IOException {
		int index = line.indexOf(entrySeparator);
		String key = line.substring(0, index);
		String value = line.substring(index + entrySeparator.length());
		lines.put(key, value);
		return true;
	}

	@Override
	public Map<String, String> getResult() {
		return lines;
	}
}

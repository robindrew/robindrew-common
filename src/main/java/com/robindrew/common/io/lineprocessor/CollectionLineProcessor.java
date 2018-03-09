package com.robindrew.common.io.lineprocessor;

import java.io.IOException;
import java.util.Collection;

import com.google.common.io.LineProcessor;

public class CollectionLineProcessor implements LineProcessor<Collection<String>> {

	private final Collection<String> lines;

	public CollectionLineProcessor(Collection<String> lines) {
		if (lines == null) {
			throw new NullPointerException("lines");
		}
		this.lines = lines;
	}

	@Override
	public boolean processLine(String line) throws IOException {
		lines.add(line);
		return true;
	}

	@Override
	public Collection<String> getResult() {
		return lines;
	}
}

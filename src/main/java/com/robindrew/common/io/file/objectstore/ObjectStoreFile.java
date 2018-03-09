package com.robindrew.common.io.file.objectstore;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.io.LineProcessor;
import com.robindrew.common.io.Files;

public abstract class ObjectStoreFile<E> implements IObjectStoreFile<E> {

	private final File file;

	protected ObjectStoreFile(File file) {
		this.file = file;
	}

	protected File getFile() {
		return file;
	}

	@Override
	public List<E> getAll() {
		if (!file.exists()) {
			return Collections.emptyList();
		}
		return Files.readFromLines(file, new LineParser());
	}

	@Override
	public void setAll(Collection<? extends E> elements) {
		Files.writeFromLines(file, new LineIterable(elements));
	}

	private class LineIterable implements Iterable<String> {

		private final Collection<? extends E> elements;

		public LineIterable(Collection<? extends E> elements) {
			this.elements = elements;
		}

		@Override
		public Iterator<String> iterator() {
			return new LineIterator(elements);
		}
	}

	private class LineIterator implements Iterator<String> {

		private final Iterator<? extends E> elements;

		public LineIterator(Collection<? extends E> elements) {
			this.elements = elements.iterator();
		}

		@Override
		public boolean hasNext() {
			return elements.hasNext();
		}

		@Override
		public String next() {
			E element = elements.next();
			return formatToLine(element);
		}
	}

	private class LineParser implements LineProcessor<List<E>> {

		private final List<E> list = new ArrayList<>();

		@Override
		public boolean processLine(String line) throws IOException {
			E element = parseFromLine(line);
			if (element != null) {
				list.add(element);
			}
			return true;
		}

		@Override
		public List<E> getResult() {
			return list;
		}

	}

}

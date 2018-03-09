package com.robindrew.common.text.parser;

import java.util.ArrayList;
import java.util.List;

public abstract class ArrayParser<A> extends ObjectParser<A[]> {

	private static int indexOf(String text, int index, char delimiter) {
		boolean skip = false;
		for (int i = index; i < text.length(); i++) {
			if (skip) {
				skip = false;
				continue;
			}
			char c = text.charAt(i);
			if (c == '\\') {
				skip = true;
				continue;
			}
			if (c == delimiter) {
				return i;
			}
		}
		return -1;
	}

	private char delimiter = ',';

	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}

	@Override
	protected A[] parseObject(String text) {
		if (text.length() == 0) {
			return newArray(0);
		}

		List<A> array = new ArrayList<A>();
		int index = 0;
		while (true) {
			int endIndex = indexOf(text, index, delimiter);

			// Last element?
			if (endIndex == -1) {
				A element = parseElement(clean(text.substring(index)));
				array.add(element);
				break;
			}
			A element = parseElement(clean(text.substring(index, endIndex)));
			array.add(element);
			index = endIndex + 1;
		}
		return array.toArray(newArray(array.size()));
	}

	private String clean(String text) {
		return text = text.replace("\\\\", "\\");
	}

	protected abstract A[] newArray(int length);

	protected abstract A parseElement(String text);
}

package com.robindrew.common.text.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;

public abstract class StringCollectionParser<L extends Collection<String>> extends ObjectParser<L> {

	private char delimiter = ',';

	public void setDelimiter(char delimiter) {
		this.delimiter = delimiter;
	}

	@Override
	protected L parseObject(String text) {
		L collection = newCollection();
		int index = 0;
		while (true) {
			int endIndex = indexOf(text, index);

			// Last element?
			if (endIndex == -1) {
				collection.add(text.substring(index));
				break;
			}
			collection.add(text.substring(index, endIndex));
			index = endIndex + 1;
		}
		return collection;
	}

	private int indexOf(String text, int index) {
		int delimiterIndex = text.indexOf(delimiter, index);

		// Escaped?
		if (delimiterIndex > 0 && text.charAt(delimiterIndex - 1) == '\\') {
			return indexOf(text, delimiterIndex + 1);
		}
		return delimiterIndex;
	}

	protected abstract L newCollection();

	/**
	 * Returns a {@link StringCollectionParser} for {@link ArrayList}.
	 */
	public static StringCollectionParser<ArrayList<String>> newArrayListParser() {
		return new StringCollectionParser<ArrayList<String>>() {

			@Override
			protected ArrayList<String> newCollection() {
				return new ArrayList<String>();
			}
		};
	}

	/**
	 * Returns a {@link StringCollectionParser} for {@link LinkedList}.
	 */
	public static StringCollectionParser<LinkedList<String>> newLinkedListParser() {
		return new StringCollectionParser<LinkedList<String>>() {

			@Override
			protected LinkedList<String> newCollection() {
				return new LinkedList<String>();
			}
		};
	}

	/**
	 * Returns a {@link StringCollectionParser} for {@link HashSet}.
	 */
	public static StringCollectionParser<HashSet<String>> newHashSetParser() {
		return new StringCollectionParser<HashSet<String>>() {

			@Override
			protected HashSet<String> newCollection() {
				return new HashSet<String>();
			}
		};
	}

	/**
	 * Returns a {@link StringCollectionParser} for {@link LinkedHashSet}.
	 */
	public static StringCollectionParser<LinkedHashSet<String>> newLinkedHashSetParser() {
		return new StringCollectionParser<LinkedHashSet<String>>() {

			@Override
			protected LinkedHashSet<String> newCollection() {
				return new LinkedHashSet<String>();
			}
		};
	}

}

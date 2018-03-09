package com.robindrew.common.text.tokenizer;

public class CharDelimiters {

	private static final char[] WHITESPACE = new char[] { ' ', '\t', '\b', '\r', '\n' };

	private char[] delimiters = new char[0];

	public CharDelimiters character(char character) {
		return characters(new char[] { character });
	}

	public CharDelimiters characters(char... characters) {
		StringBuilder builder = new StringBuilder();
		builder.append(delimiters);
		builder.append(characters);
		delimiters = builder.toString().toCharArray();
		return this;
	}

	public CharDelimiters whitespace() {
		return characters(WHITESPACE);
	}

	public boolean contains(char c) {
		for (char delimiter : delimiters) {
			if (delimiter == c) {
				return true;
			}
		}
		return false;
	}

}

package com.robindrew.common.text.tokenizer;

public class CharTokenizer implements IStringTokenizer {

	private final String text;
	private final CharDelimiters delimiters;

	private int index = 0;
	private String next = null;

	public CharTokenizer(String text, CharDelimiters delimiters) {
		if (text == null) {
			throw new NullPointerException("text");
		}
		if (delimiters == null) {
			throw new NullPointerException("delimiters");
		}
		this.text = text;
		this.delimiters = delimiters;
	}

	@Override
	public boolean hasNext() {
		if (next == null) {
			next = next(true);
		}
		return next != null;
	}

	@Override
	public String next(boolean optional) {
		if (next != null) {
			String token = next;
			next = null;
			return token;
		}

		skipDelimiters();
		if (index == text.length()) {
			if (!optional) {
				throw new IllegalStateException("No more tokens");
			}
		}

		int startIndex = index;
		while (index < text.length()) {
			char c = text.charAt(index);
			if (delimiters.contains(c)) {
				break;
			}
			index++;
		}

		return text.substring(startIndex, index);
	}

	private void skipDelimiters() {
		String text = this.text;
		while (index < text.length()) {
			char c = text.charAt(index);
			if (!delimiters.contains(c)) {
				break;
			}
			index++;
		}
	}

}

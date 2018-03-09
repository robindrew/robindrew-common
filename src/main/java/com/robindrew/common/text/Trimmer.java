package com.robindrew.common.text;

public class Trimmer {

	private final int trimLength;
	private final boolean trimNewline;

	public Trimmer(int trimLength, boolean trimNewline) {
		if (trimLength < 1) {
			throw new IllegalArgumentException("trimLength=" + trimLength);
		}
		this.trimLength = trimLength;
		this.trimNewline = trimNewline;
	}

	public String trim(String text) {
		int length = text.length();
		boolean trimmed = false;

		// Trim to max length
		if (text.length() > trimLength) {
			trimmed = true;
			text = text.substring(0, trimLength);
		}

		if (trimNewline) {

			// Trim to first '\r'
			int index1 = text.indexOf('\r');
			if (index1 != -1) {
				trimmed = true;
				text = text.substring(0, index1);
			}

			// Trim to first '\n'
			int index2 = text.indexOf('\n');
			if (index2 != -1) {
				trimmed = true;
				text = text.substring(0, index2);
			}
		}

		// If trimmed, annotate
		if (trimmed) {
			text += " ... (" + length + " chars)";
		}
		return text;
	}
}

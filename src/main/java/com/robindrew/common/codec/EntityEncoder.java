package com.robindrew.common.codec;

/**
 * XML & HTML entity encoder.
 * <p>
 * TODO: add option to encode full html symbol set
 */
public class EntityEncoder implements IStringEncoder, IStringDecoder {

	public static final String AMPERSAND = "&amp;";
	public static final String LESS_THAN = "&lt;";
	public static final String GREATER_THAN = "&gt;";
	public static final String QUOTE = "&quot;";
	public static final String APOSTROPHE = "&apos;";

	@Override
	public String encodeToString(CharSequence text) {
		if (text == null) {
			throw new NullPointerException("text");
		}
		StringBuilder encoded = new StringBuilder();
		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			encodeEntity(encoded, c);
		}
		return encoded.toString();
	}

	public StringBuilder encodeEntity(StringBuilder encoded, char c) {
		switch (c) {
			case '&':
				return encoded.append(AMPERSAND);
			case '<':
				return encoded.append(LESS_THAN);
			case '>':
				return encoded.append(GREATER_THAN);
			case '\"':
				return encoded.append(QUOTE);
			case '\'':
				return encoded.append(APOSTROPHE);
			default:
				return encoded.append(c);
		}
	}

	public String decodeEntity(String entity) {
		if (entity.equals(AMPERSAND)) {
			return "&";
		}
		if (entity.equals(LESS_THAN)) {
			return "<";
		}
		if (entity.equals(GREATER_THAN)) {
			return ">";
		}
		if (entity.equals(QUOTE)) {
			return "\"";
		}
		if (entity.equals(APOSTROPHE)) {
			return "\'";
		}
		return entity;
	}

	@Override
	public String encodeToString(Object object) {
		if (object == null) {
			throw new NullPointerException("object");
		}
		return encodeToString(object.toString());
	}

	@Override
	public Object decodeToObject(String text) {
		return decodeToString(text);
	}

	@Override
	public String decodeToString(CharSequence text) {
		if (text == null) {
			throw new NullPointerException("text");
		}
		String encoded = text.toString();
		StringBuilder decoded = new StringBuilder();
		int index = 0;
		while (true) {
			int start = encoded.indexOf('&', index);
			if (start == -1) {
				String append = encoded.substring(index);
				decoded.append(append);
				break;
			}
			int end = encoded.indexOf(';', start + 1);
			if (end == -1) {
				String append = encoded.substring(index);
				decoded.append(append);
				break;
			}
			end++;

			String append = encoded.substring(index, start);
			decoded.append(append);

			String entity = encoded.substring(start, end);
			entity = decodeEntity(entity);
			decoded.append(entity);

			index = end;
		}
		return decoded.toString();
	}

}

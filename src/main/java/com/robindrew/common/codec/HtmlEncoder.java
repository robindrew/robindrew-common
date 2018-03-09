package com.robindrew.common.codec;

/**
 * XML & HTML entity encoder.
 * <p>
 * TODO: add option to encode full html symbol set
 */
public class HtmlEncoder extends EntityEncoder implements IStringEncoder {

	public static final String EXCLAMATION = "&#33;";
	public static final String PERCENT = "&#37;";
	public static final String DOLLAR = "&#36;";
	public static final String POUND = "&#163;";
	public static final String CENT = "&#162;";
	public static final String COPYRIGHT = "&#169;";
	public static final String REGISTERED = "&#174;";
	public static final String AT = "&#64;";
	public static final String CARET = "&#94;";

	@Override
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
			case '%':
				return encoded.append(PERCENT);
			case '!':
				return encoded.append(EXCLAMATION);
			case '$':
				return encoded.append(DOLLAR);
			case '£':
				return encoded.append(POUND);
			case '¢':
				return encoded.append(CENT);
			case '©':
				return encoded.append(COPYRIGHT);
			case '®':
				return encoded.append(REGISTERED);
			case '@':
				return encoded.append(AT);
			case '^':
				return encoded.append(CARET);
			default:
				return encoded.append(c);
		}
	}

	@Override
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
		if (entity.equals(PERCENT)) {
			return "%";
		}
		if (entity.equals(EXCLAMATION)) {
			return "!";
		}
		if (entity.equals(DOLLAR)) {
			return "$";
		}
		if (entity.equals(POUND)) {
			return "£";
		}
		if (entity.equals(CENT)) {
			return "¢";
		}
		if (entity.equals(COPYRIGHT)) {
			return "©";
		}
		if (entity.equals(REGISTERED)) {
			return "®";
		}
		if (entity.equals(AT)) {
			return "@";
		}
		if (entity.equals(CARET)) {
			return "^";
		}
		return entity;
	}
}

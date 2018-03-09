package com.robindrew.common.xml;

import java.util.LinkedList;

/**
 * An XML Builder.
 */
public class XmlBuilder implements IXmlBuilder {

	private static final String DECLARATION = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";

	/** The xml. */
	private final StringBuilder xml = new StringBuilder();

	/** Indicates if the XML should be formatted. */
	private final boolean format;
	/** The indentation spacer to use when formatting. */
	private final String indent;
	/** The new line to use when formatting. */
	private final String newLine;
	/** The names list. */
	private LinkedList<String> names = new LinkedList<String>();
	/** Indicates if attributes have been appended. */
	private boolean attributes = false;

	/**
	 * Creates a new XML builder.
	 */
	public XmlBuilder() {
		this.format = true;
		this.indent = "\t";
		this.newLine = "\n";
	}

	/**
	 * Creates a new XML builder.
	 */
	public XmlBuilder(boolean format) {
		this.format = format;
		this.indent = "\t";
		this.newLine = "\n";
	}

	/**
	 * Creates a new XML builder.
	 * @param indent the indentation spacer.
	 */
	public XmlBuilder(String indent) {
		if (indent == null) {
			throw new NullPointerException();
		}
		this.format = true;
		this.indent = indent;
		this.newLine = "\n";
	}

	/**
	 * Append a new line.
	 */
	private final void newLine() {
		if (format) {
			xml.append(newLine);
		}
	}

	/**
	 * Append an indent (of approprate depth.
	 */
	private final void indent() {
		if (format) {
			final int depth = names.size();
			if (depth > 0) {
				for (int i = 0; i < depth; i++) {
					xml.append(indent);
				}
			}
		}
	}

	@Override
	public XmlBuilder declaration() {
		xml.append(DECLARATION);
		newLine();
		return this;
	}

	/**
	 * Open a new element.
	 * @param name the element name.
	 * @return this builder.
	 */
	@Override
	public XmlBuilder open(String name) {
		if (name == null) {
			throw new NullPointerException();
		}
		if (attributes) {
			xml.append('>');
			newLine();
		}
		indent();
		xml.append('<');
		xml.append(name);
		attributes = true;
		names.addLast(name);
		return this;
	}

	/**
	 * Append a new element.
	 * @param name the element name.
	 * @return this builder.
	 */
	@Override
	public XmlBuilder element(String name) {
		open(name);
		close();
		return this;
	}

	/**
	 * Append a new element.
	 * @param name the element name.
	 * @param value the element value.
	 * @return this builder.
	 */
	@Override
	public XmlBuilder element(String name, Object value) {
		if (name == null) {
			throw new NullPointerException();
		}
		if (attributes) {
			attributes = false;
			xml.append('>');
			newLine();
		}
		indent();
		xml.append('<');
		xml.append(name);
		xml.append('>');
		xml.append(value);
		xml.append("</");
		xml.append(name);
		xml.append('>');
		newLine();
		return this;
	}

	/**
	 * Append an empty element.
	 * @param name the element name.
	 * @return this builder.
	 */
	@Override
	public XmlBuilder empty(String name) {
		if (name == null) {
			throw new NullPointerException();
		}
		if (attributes) {
			attributes = false;
			xml.append('>');
			newLine();
		}
		indent();
		xml.append('<');
		xml.append(name);
		xml.append("/>");
		newLine();
		return this;
	}

	/**
	 * Close the current element.
	 * @return this builder.
	 */
	@Override
	public XmlBuilder close() {
		String name = names.removeLast();
		if (name == null) {
			throw new IllegalStateException();
		}
		if (attributes) {
			attributes = false;
			xml.append("/>");
			newLine();
		} else {
			indent();
			xml.append('<');
			xml.append('/');
			xml.append(name);
			xml.append('>');
			newLine();
		}
		return this;
	}

	/**
	 * Close the current element.
	 * @param value the value of the element.
	 * @return this builder.
	 */
	@Override
	public XmlBuilder close(String value) {
		boolean multiLine = value.length() > 80 || value.indexOf('\n') != -1;
		String name = names.removeLast();
		if (name == null) {
			throw new IllegalStateException();
		}
		if (attributes) {
			attributes = false;
			xml.append(">");
			if (multiLine) {
				newLine();
			}
		}
		xml.append(value);
		if (multiLine && !value.endsWith(newLine)) {
			newLine();
			indent();
		}
		xml.append('<');
		xml.append('/');
		xml.append(name);
		xml.append('>');
		newLine();
		return this;
	}

	/**
	 * Append an attribute.
	 * @param name the name.
	 * @param value the value.
	 * @return this builder.
	 */
	@Override
	public XmlBuilder attribute(String name, Object value) {
		if (name == null) {
			throw new NullPointerException();
		}
		xml.append(' ');
		xml.append(name);
		xml.append('=');
		xml.append('\"');
		xml.append(value);
		xml.append('\"');
		return this;
	}

	/**
	 * Append an attribute.
	 * @param name the name.
	 * @param value the value.
	 * @return this builder.
	 */
	@Override
	public XmlBuilder attribute(String name, int value) {
		if (name == null) {
			throw new NullPointerException();
		}
		xml.append(' ');
		xml.append(name);
		xml.append('=');
		xml.append('\"');
		xml.append(value);
		xml.append('\"');
		return this;
	}

	/**
	 * Append an attribute.
	 * @param name the name.
	 * @param value the value.
	 * @return this builder.
	 */
	@Override
	public XmlBuilder attribute(String name, long value) {
		if (name == null) {
			throw new NullPointerException();
		}
		xml.append(' ');
		xml.append(name);
		xml.append('=');
		xml.append('\"');
		xml.append(value);
		xml.append('\"');
		return this;
	}

	@Override
	public String toString() {
		if (xml.length() > 0) {
			// Remove any trailing new line
			if (format) {
				int index = xml.length() - 1;
				if (xml.charAt(index) == '\n') {
					xml.deleteCharAt(index);
				}
			}
		}
		return xml.toString();
	}

	@Override
	public String toXml() {
		return toString();
	}

}

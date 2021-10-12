package com.robindrew.common.html.element;

import static com.robindrew.common.html.element.HtmlElements.appendOptionalAttribute;

public class Option extends HtmlElement {

	public static Option withContent(Object content) {
		return new Option().setContent(content);
	}

	public static Option withValue(Object value, Object content) {
		return new Option().setValue(value).setContent(content);
	}

	private boolean selected = false;
	private Object value = null;
	private Object content = "";

	@Override
	public String getElementName() {
		return "option";
	}

	public Object getValue() {
		return value;
	}

	public Option setValue(Object value) {
		this.value = value;
		return this;
	}

	public boolean isSelected() {
		return selected;
	}

	public Object getContent() {
		return content;
	}

	public Option setSelected(boolean selected) {
		this.selected = selected;
		return this;
	}
	
	public Option selected() {
		return setSelected(true);
	}

	public Option setContent(Object content) {
		if (content == null) {
			throw new NullPointerException("content");
		}
		this.content = content;
		return this;
	}

	public boolean isEmpty() {
		return false;
	}

	@Override
	public void appendAttributes(StringBuilder html) {
		appendStandardAttributes(html);
		appendOptionalAttribute(html, "value", value);
		appendOptionalAttribute(html, "selected", selected);
	}

	@Override
	public void appendContent(StringBuilder html) {
		html.append(content);
	}

}

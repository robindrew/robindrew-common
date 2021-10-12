package com.robindrew.common.html.element;

import java.util.Map;
import java.util.Map.Entry;

public class HtmlElements {

	private HtmlElements() {
	}

	public static StringBuilder appendOpenElement(StringBuilder html, String name) {
		return html.append('<').append(name);
	}

	public static StringBuilder appendOpenElement(StringBuilder html, IHtmlElement element) {
		return appendOpenElement(html, element.getElementName());
	}

	public static StringBuilder appendCloseElement(StringBuilder html, boolean empty) {
		if (empty) {
			html.append('/');
		}
		return html.append('>');
	}

	public static StringBuilder appendCloseElement(StringBuilder html, IHtmlElement element) {
		return appendCloseElement(html, element.getElementName());
	}

	public static StringBuilder appendCloseElement(StringBuilder html, String name) {
		return html.append('<').append('/').append(name).append('>');
	}

	public static StringBuilder appendElement(StringBuilder html, String name) {
		return html.append('<').append(name).append('/').append('>');
	}

	public static StringBuilder appendElement(StringBuilder html, String name, Object content) {
		html.append('<').append(name).append('>');
		html.append(content);
		appendCloseElement(html, name);
		return html;
	}

	public static StringBuilder appendElement(StringBuilder html, String name, Map<? extends Object, ? extends Object> attributeMap) {
		html.append('<').append(name);
		appendAttributes(html, attributeMap);
		html.append('/').append('>');
		return html;
	}

	public static StringBuilder appendAttribute(StringBuilder html, Object key, Object value) {
		if (key == null) {
			throw new NullPointerException("key");
		}
		if (value == null) {
			throw new NullPointerException("value");
		}
		return html.append(' ').append(key).append('=').append('\"').append(value).append('\"');
	}

	public static StringBuilder appendOptionalAttribute(StringBuilder html, Object key, Object value) {
		if (value == null) {
			return html;
		}
		return appendAttribute(html, key, value);
	}

	public static StringBuilder appendOptionalAttribute(StringBuilder html, Object key, boolean append) {
		if (!append) {
			return html;
		}
		return appendAttribute(html, key);

	}

	public static StringBuilder appendAttribute(StringBuilder html, Object key) {
		if (key == null) {
			throw new NullPointerException("key");
		}
		return html.append(' ').append(key);

	}

	public static StringBuilder appendAttributes(StringBuilder html, Map<? extends Object, ? extends Object> attributeMap) {
		for (Entry<? extends Object, ? extends Object> entry : attributeMap.entrySet()) {
			appendAttribute(html, entry.getKey(), entry.getValue());
		}
		return html;
	}

	public static StringBuilder appendElement(StringBuilder html, IHtmlElement element) {

		// Element
		appendOpenElement(html, element);
		element.appendAttributes(html);

		// No Content
		if (element.isEmpty()) {
			appendCloseElement(html, true);
			return html;
		}

		// Content
		appendCloseElement(html, false);
		element.appendContent(html);

		// Close
		appendCloseElement(html, element);
		return html;
	}

}

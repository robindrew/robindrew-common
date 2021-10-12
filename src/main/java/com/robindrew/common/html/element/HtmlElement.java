package com.robindrew.common.html.element;

import static com.robindrew.common.html.element.HtmlElements.appendElement;
import static com.robindrew.common.html.element.HtmlElements.appendAttribute;

public abstract class HtmlElement implements IHtmlElement {

	private String htmlId;
	private String htmlClass;
	private String htmlStyle;

	public StringBuilder appendStandardAttributes(StringBuilder html) {
		if (htmlId != null) {
			appendAttribute(html, "id", htmlId);
		}
		if (htmlId != null) {
			appendAttribute(html, "class", htmlClass);
		}
		if (htmlStyle != null) {
			appendAttribute(html, "style", htmlClass);
		}
		return html;
	}

	public String getHtmlId() {
		return htmlId;
	}

	public String getHtmlClass() {
		return htmlClass;
	}

	public HtmlElement setId(String htmlId) {
		this.htmlId = htmlId;
		return this;
	}

	public HtmlElement setClass(String htmlClass) {
		this.htmlClass = htmlClass;
		return this;
	}

	public String getHtmlStyle() {
		return htmlStyle;
	}

	public HtmlElement setStyle(String htmlStyle) {
		this.htmlStyle = htmlStyle;
		return this;
	}

	public boolean isEmpty() {
		return true;
	}

	@Override
	public void appendAttributes(StringBuilder html) {
		appendStandardAttributes(html);
	}

	@Override
	public StringBuilder appendTo(StringBuilder html) {
		return appendElement(html, this);
	}

}

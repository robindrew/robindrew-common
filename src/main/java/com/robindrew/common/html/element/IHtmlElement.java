package com.robindrew.common.html.element;

public interface IHtmlElement {

	String getHtmlId();

	String getHtmlClass();

	String getElementName();

	boolean isEmpty();

	void appendAttributes(StringBuilder html);

	void appendContent(StringBuilder html);

	StringBuilder appendTo(StringBuilder html);

}

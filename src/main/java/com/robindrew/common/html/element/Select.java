package com.robindrew.common.html.element;

import static com.robindrew.common.html.element.HtmlElements.appendAttribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Select extends HtmlElement {

	public static Select withOptions(String name, Object... options) {
		return new Select(name).addOptions(options);
	}

	public static Select withOptions(String name, Collection<? extends Object> options) {
		return new Select(name).addOptions(options);
	}

	public static Select withOptions(String name, Map<? extends Object, ? extends Object> options) {
		Select select = new Select(name);
		for (Entry<? extends Object, ? extends Object> entry : options.entrySet()) {
			select.addOption(entry.getKey(), entry.getValue());
		}
		return select;
	}

	private final String name;
	private final List<Option> optionList = new ArrayList<>();

	public Select(String name) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		this.name = name;
	}

	@Override
	public String getElementName() {
		return "select";
	}

	public Option addOption(Object content) {
		if (content == null) {
			throw new NullPointerException("content");
		}
		Option option = Option.withContent(content);
		add(option);
		return option;
	}

	public Option addOption(Object value, Object content) {
		if (content == null) {
			throw new NullPointerException("content");
		}
		Option option = Option.withValue(value, content);
		add(option);
		return option;
	}

	public Select addOptions(Collection<? extends Object> options) {
		for (Object content : options) {
			addOption(content);
		}
		return this;
	}

	public Select addOptions(Object... options) {
		for (Object content : options) {
			addOption(content);
		}
		return this;
	}

	public Select add(Option option) {
		if (option == null) {
			throw new NullPointerException("option");
		}
		optionList.add(option);
		return this;
	}

	@Override
	public void appendAttributes(StringBuilder html) {
		appendAttribute(html, "name", name);
	}

	@Override
	public void appendContent(StringBuilder html) {
		for (Option option : optionList) {
			option.appendTo(html);
		}
	}

}

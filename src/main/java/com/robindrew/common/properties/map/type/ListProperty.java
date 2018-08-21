package com.robindrew.common.properties.map.type;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.collect.Lists;
import com.robindrew.common.text.parser.IStringParser;
import com.robindrew.common.util.Check;

/**
 * A List Property.
 */
public class ListProperty<E> extends AbstractProperty<List<E>> {

	private IStringParser<E> parser = null;
	private String regex = ",";

	public ListProperty(String... keys) {
		super(keys);
	}

	public ListProperty(Collection<String> keys) {
		super(keys);
	}

	public ListProperty<E> parser(IStringParser<E> parser) {
		this.parser = Check.notNull("parser", parser);
		return this;
	}

	public ListProperty<E> regex(String regex) {
		this.regex = Check.notEmpty("regex", regex);
		return this;
	}

	@Override
	public ListProperty<E> notNull(boolean notNull) {
		super.notNull(notNull);
		return this;
	}

	@Override
	public ListProperty<E> defaultValue(List<E> list) {
		super.defaultValue(list);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected List<E> parseValue(String key, String value) {
		String[] values = value.split(regex);
		if (parser == null) {
			return (List<E>) Lists.newArrayList(values);
		}
		List<E> list = new ArrayList<E>();
		for (String element : values) {
			list.add(parser.parse(element));
		}
		return list;
	}

}

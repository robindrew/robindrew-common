package com.robindrew.common.properties.map.type;

import static java.util.Arrays.asList;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.common.collect.Sets;
import com.robindrew.common.text.parser.IStringParser;
import com.robindrew.common.util.Check;

/**
 * A Set Property.
 */
public class SetProperty<E> extends AbstractProperty<Set<E>> {

	private IStringParser<E> parser = null;
	private String regex = ",";

	public SetProperty(String... keys) {
		super(keys);
	}

	public SetProperty(Collection<String> keys) {
		super(keys);
	}

	public SetProperty<E> parser(IStringParser<E> parser) {
		this.parser = Check.notNull("parser", parser);
		return this;
	}

	public SetProperty<E> regex(String regex) {
		this.regex = Check.notEmpty("regex", regex);
		return this;
	}

	@Override
	public SetProperty<E> notNull(boolean notNull) {
		super.notNull(notNull);
		return this;
	}

	@Override
	public SetProperty<E> defaultValue(Set<E> list) {
		super.defaultValue(list);
		return this;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected Set<E> parseValue(String key, String value) {
		String[] values = value.split(regex);
		if (parser == null) {
			return (Set<E>) Sets.newLinkedHashSet(asList(values));
		}
		Set<E> list = new LinkedHashSet<E>();
		for (String element : values) {
			list.add(parser.parse(element));
		}
		return list;
	}

}

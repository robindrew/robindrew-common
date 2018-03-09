package com.robindrew.common.properties.map;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import com.robindrew.common.util.Check;

public class CompositePropertyMap extends AbstractPropertyMap {

	public static final CompositePropertyMap newComposite(String source) {
		return new CompositePropertyMap(source);
	}

	private final List<IPropertyMap> maps = new CopyOnWriteArrayList<>();

	public CompositePropertyMap(String source) {
		super(source);
	}

	@Override
	public String getSource() {
		List<String> names = new ArrayList<>();
		for (IPropertyMap map : maps) {
			names.add(map.getSource());
		}
		return super.getSource() + names;
	}

	@Override
	public Map<String, SourceProperty> asSourceMap(boolean forDisplay) {
		Map<String, SourceProperty> newMap = new LinkedHashMap<>();

		ListIterator<IPropertyMap> iterator = maps.listIterator(maps.size());

		// Iterate in reverse
		while (iterator.hasPrevious()) {
			IPropertyMap map = iterator.previous();
			newMap.putAll(map.asSourceMap(forDisplay));
		}

		return newMap;
	}

	@Override
	protected String getProperty(String key) {

		// Look through the composed maps in insertion order
		for (IPropertyMap map : maps) {
			String value = map.get(key, null);
			if (value != null) {
				return value;
			}
		}
		return null;
	}

	public CompositePropertyMap with(IPropertyMap propertyMap) {
		Check.notNull("propertyMap", propertyMap);
		maps.add(propertyMap);
		return this;
	}

	public CompositePropertyMap with(Collection<? extends IPropertyMap> propertyMaps) {
		Check.notEmpty("propertyMaps", propertyMaps);
		for (IPropertyMap propertyMap : propertyMaps) {
			with(propertyMap);
		}
		return this;
	}

	public CompositePropertyMap withEnv() {
		return with(new EnvironmentPropertyMap());
	}

	public CompositePropertyMap withSystem() {
		return with(new SystemPropertyMap());
	}

	@Override
	public boolean isEmpty() {
		for (IPropertyMap map : maps) {
			if (!map.isEmpty()) {
				return false;
			}
		}
		return true;
	}
}

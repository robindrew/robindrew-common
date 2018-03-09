package com.robindrew.common.template.velocity.map;

import java.util.HashMap;
import java.util.Map;

import org.apache.velocity.exception.ResourceNotFoundException;

import com.google.common.base.Charsets;

public class VelocityMap implements IVelocityMap {

	private final Map<String, byte[]> map = new HashMap<String, byte[]>();

	@Override
	public void put(String name, String text) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		if (text == null) {
			throw new NullPointerException("text");
		}
		byte[] bytes = text.getBytes(Charsets.UTF_8);
		synchronized (map) {
			map.put(name, bytes);
		}
	}

	@Override
	public byte[] get(String name) {
		synchronized (map) {
			byte[] bytes = map.get(name);
			if (bytes == null) {
				throw new ResourceNotFoundException(name);
			}
			return bytes;
		}
	}

}

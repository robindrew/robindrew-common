package com.robindrew.common.template.velocity.map;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

public class VelocityMapResourceLoader extends ResourceLoader {

	private IVelocityMap map = null;

	@Override
	public InputStream getResourceStream(String name) throws ResourceNotFoundException {
		byte[] bytes = map.get(name);
		if (bytes == null) {
			return null;
		}
		return new ByteArrayInputStream(bytes);
	}

	@Override
	public long getLastModified(Resource resource) {
		return 0;
	}

	@Override
	public void init(ExtendedProperties properties) {
		this.map = (IVelocityMap) properties.getProperty("map");
		if (map == null) {
			throw new VelocityException("mandatory property not found: map");
		}
	}

	@Override
	public boolean isSourceModified(Resource resource) {
		return false;
	}
}

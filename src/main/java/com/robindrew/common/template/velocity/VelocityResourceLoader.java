package com.robindrew.common.template.velocity;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;

import com.google.common.io.ByteSource;
import com.robindrew.common.io.Resources;
import com.robindrew.common.util.Java;

public class VelocityResourceLoader extends ResourceLoader {

	private volatile String path = null;
	private volatile String extension = "template";
	private volatile boolean caching = false;

	public boolean isCaching() {
		return caching;
	}

	@Override
	public long getLastModified(Resource resource) {
		return 0;
	}

	@Override
	public boolean isSourceModified(Resource resource) {
		return false;
	}

	@Override
	public void init(ExtendedProperties properties) {
		path = properties.getString("path");
		extension = properties.getString("extension");
		caching = properties.getBoolean("caching");
	}

	@Override
	public InputStream getResourceStream(String name) throws ResourceNotFoundException {
		if (path != null && !name.startsWith(path)) {
			name = path + "/" + name;
		}
		if (extension != null && !name.endsWith(extension)) {
			name = name + "." + extension;
		}
		ByteSource source = Resources.toByteSource(name);
		try {
			return source.openBufferedStream();
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

}

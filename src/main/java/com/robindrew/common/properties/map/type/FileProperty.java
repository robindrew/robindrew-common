package com.robindrew.common.properties.map.type;

import java.io.File;
import java.util.Collection;

import com.robindrew.common.properties.map.PropertyException;

/**
 * A File Property.
 */
public class FileProperty extends AbstractProperty<File> {

	private boolean exists = false;
	private boolean directory = false;

	public FileProperty(String... keys) {
		super(keys);
	}

	public FileProperty(Collection<String> keys) {
		super(keys);
	}

	public FileProperty checkExists() {
		this.exists = true;
		return this;
	}

	public FileProperty checkDirectory() {
		this.directory = true;
		return this;
	}

	public FileProperty existsDirectory() {
		this.exists = true;
		this.directory = true;
		return this;
	}

	@Override
	public FileProperty notNull(boolean notNull) {
		super.notNull(notNull);
		return this;
	}

	@Override
	public FileProperty defaultValue(File value) {
		super.defaultValue(value);
		return this;
	}

	@Override
	protected File parseValue(String key, String value) {
		File file = new File(value);
		if (exists && !file.exists()) {
			throw new PropertyException("File does not exist: " + file);
		}
		if (directory && !file.isDirectory()) {
			throw new PropertyException("Not a directory: " + file);
		}
		return file;
	}

}

package com.robindrew.common.lang;

import java.io.File;
import java.util.Arrays;

public class Args {

	private final String[] args;

	public Args(String[] args) {
		if (args == null) {
			throw new NullPointerException("args");
		}
		this.args = args;
	}

	@Override
	public String toString() {
		return Arrays.toString(args);
	}

	public String get(String name) {
		return get(name, false);
	}

	public String get(String name, boolean optional) {
		for (int i = 0; i < args.length - 1; i++) {
			String key = args[i];
			if (key.equals(name)) {
				return args[i + 1];
			}
		}
		if (optional) {
			return null;
		}
		throw new IllegalArgumentException("Argument '" + name + "' not found in " + toString());
	}

	public File getFile(String name, boolean exists) {
		String filename = get(name);
		File file = new File(filename);
		if (exists && !file.exists()) {
			throw new IllegalArgumentException("Argument '" + name + "' - file does not exist: " + file);
		}
		if (file.exists() && file.isDirectory()) {
			throw new IllegalArgumentException("Argument '" + name + "' - file is a directory: " + file);
		}
		return file;
	}

	public File getFile(String name) {
		return getFile(name, true);
	}

	public File getDirectory(String name, boolean exists) {
		String path = get(name);
		File directory = new File(path);
		System.out.println(directory.getAbsolutePath());
		System.out.println(directory.exists());
		if (exists && !directory.exists()) {
			throw new IllegalArgumentException("Argument '" + name + "' - directory does not exist: " + directory);
		}
		if (directory.exists() && !directory.isDirectory()) {
			throw new IllegalArgumentException("Argument '" + name + "' - directory is a file: " + directory);
		}
		return directory;
	}

	public File getDirectory(String name) {
		return getDirectory(name, true);
	}

	public <E extends Enum<E>> E getEnum(String name, Class<E> enumType) {
		String value = get(name);
		return Enum.valueOf(enumType, value);
	}

	public <E extends Enum<E>> E getEnum(String name, Class<E> enumType, E defaultValue) {
		String value = get(name, true);
		if (value == null) {
			return defaultValue;
		}
		return Enum.valueOf(enumType, value);
	}

}

package com.robindrew.common.util;

/**
 * A utility that exposes common JVM system properties.
 */
public class SystemProperties {

	/** Java Runtime Environment runtime version */
	public static final String JAVA_RUNTIME_VERSION = "java.runtime.version";
	/** Java Runtime Environment version */
	public static final String JAVA_VERSION = "java.version";
	/** Java Runtime Environment vendor */
	public static final String JAVA_VENDOR = "java.vendor";
	/** Java vendor URL */
	public static final String JAVA_VENDOR_URL = "java.vendor.url";

	/** Java installation directory */
	public static final String JAVA_HOME = "java.home";

	/** Java Virtual Machine specification version */
	public static final String JAVA_VM_SPECIFICATION_VERSION = "java.vm.specification.version";
	/** Java Virtual Machine specification vendor */
	public static final String JAVA_VM_SPECIFICATION_VENDOR = "java.vm.specification.vendor";
	/** Java Virtual Machine specification name */
	public static final String JAVA_VM_SPECIFICATION_NAME = "java.vm.specification.name";

	/** Java Virtual Machine implementation version */
	public static final String JAVA_VM_VERSION = "java.vm.version";
	/** Java Virtual Machine implementation vendor */
	public static final String JAVA_VM_VENDOR = "java.vm.vendor";
	/** Java Virtual Machine implementation name */
	public static final String JAVA_VM_NAME = "java.vm.name";

	/** Java Runtime Environment specification version */
	public static final String JAVA_SPECIFICATION_VERSION = "java.specification.version";
	/** Java Runtime Environment specification vendor */
	public static final String JAVA_SPECIFICATION_VENDOR = "java.specification.vendor";
	/** Java Runtime Environment specification name */
	public static final String JAVA_SPECIFICATION_NAME = "java.specification.name";

	/** Java class format version number */
	public static final String JAVA_CLASS_VERSION = "java.class.version";
	/** Java class path */
	public static final String JAVA_CLASS_PATH = "java.class.path";
	/** List of paths to search when loading libraries */
	public static final String JAVA_LIBRARY_PATH = "java.library.path";
	/** Default temp file path */
	public static final String JAVA_IO_TMPDIR = "java.io.tmpdir";
	/** Name of JIT compiler to use */
	public static final String JAVA_COMPILER = "java.compiler";
	/** Path of extension directory or directories */
	public static final String JAVA_EXT_DIRS = "java.ext.dirs";

	/** Java command. */
	public static final String SUN_JAVA_COMMAND = "sun.java.command";

	/** Operating system name */
	public static final String OS_NAME = "os.name";
	/** Operating system architecture */
	public static final String OS_ARCH = "os.arch";
	/** Operating system version */
	public static final String OS_VERSION = "os.version";

	/** File separator ("/" on UNIX, "\" on WINDOWS) */
	public static final String FILE_SEPARATOR = "file.separator";
	/** Path separator (":" on UNIX, ";" on WINDOWS) */
	public static final String PATH_SEPARATOR = "path.separator";
	/** Line separator ("\n" on UNIX, "\r\n" on WINDOWS) */
	public static final String LINE_SEPARATOR = "line.separator";

	/** User's account name */
	public static final String USER_NAME = "user.name";
	/** User's home directory */
	public static final String USER_HOME = "user.home";
	/** User's current working directory */
	public static final String USER_DIR = "user.dir";

	/** File encoding (e.g. UTF-8) */
	public static final String FILE_ENCODING = "file.encoding";
	/** User timezone (e.g. UTC) */
	public static final String USER_TIMEZONE = "user.timezone";

	/**
	 * Utility class - private constructor.
	 */
	private SystemProperties() {
	}

	public static final String getOperatingSystem() {
		return get(OS_NAME);
	}

	public static final String getUserName() {
		return get(USER_NAME);
	}

	public static final String getTimeZone() {
		return get(USER_TIMEZONE);
	}

	public static final String getFileEncoding() {
		return get(FILE_ENCODING);
	}

	public static final String getFileSeparator() {
		return get(FILE_SEPARATOR);
	}

	public static final String getPathSeparator() {
		return get(PATH_SEPARATOR);
	}

	public static final String getLineSeparator() {
		return get(LINE_SEPARATOR);
	}

	public static final String getClassPath() {
		return get(JAVA_CLASS_PATH);
	}

	public static final String getLibraryPath() {
		return get(JAVA_LIBRARY_PATH);
	}

	public static final String getJavaVersion() {
		return get(JAVA_VERSION);
	}

	public static String getJavaRuntimeVersion() {
		return get(JAVA_RUNTIME_VERSION);
	}

	public static final String getWorkingDirectory() {
		return get(USER_DIR);
	}

	public static final String getJavaCommand() {
		return get(SUN_JAVA_COMMAND);
	}

	/**
	 * Returns the system property with the given key.
	 * @param key the key.
	 * @return the value.
	 */
	public static final String get(String key) {
		return System.getProperty(key);
	}

	/**
	 * Returns the system property with the given key, returning the default value of not found.
	 * @param key the key.
	 * @param defaultValue the value to return if they key is not present.
	 * @return the value.
	 */
	public static final String get(String key, String defaultValue) {
		String value = get(key);
		return (value == null) ? defaultValue : value;
	}

	/**
	 * Returns the system property with the given key.
	 * @param key the key.
	 * @param optional false to throw an exception if the key is not found.
	 * @return the value.
	 */
	public static final String get(String key, boolean optional) {
		if (optional) {
			return get(key);
		}

		// Not optional ...
		String value = get(key);
		if (value == null && !optional) {
			throw new IllegalArgumentException("System property not found: " + key);
		}
		return value;
	}

	/**
	 * Set a system property.
	 * @param key the key to set.
	 * @param value the value to set.
	 */
	public static final void set(String key, String value) {
		if (key.isEmpty()) {
			throw new IllegalArgumentException("key is empty");
		}
		if (value == null) {
			throw new NullPointerException("value");
		}
		System.setProperty(key, value);
	}

}

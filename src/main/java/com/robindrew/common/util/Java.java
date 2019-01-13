package com.robindrew.common.util;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Throwables;
import com.sun.management.OperatingSystemMXBean;

/**
 * A utility that brings together common core language functionality in to a single class.
 */
@SuppressWarnings("restriction")
public class Java {

	private static final Logger log = LoggerFactory.getLogger(Java.class);

	/** Cache for the host, in case resolution fails */
	private static final AtomicReference<InetAddress> cachedHost = new AtomicReference<>();

	/**
	 * Utility class - private constructor.
	 */
	private Java() {
	}

	/**
	 * Returns the start time of the JVM process.
	 * @return the start time of the JVM process.
	 */
	public static long getStartTime() {
		return ManagementFactory.getRuntimeMXBean().getStartTime();
	}

	/**
	 * Returns the uptime of the JVM process.
	 * @return the uptime of the JVM process.
	 */
	public static long getUptime() {
		return ManagementFactory.getRuntimeMXBean().getUptime();
	}

	/**
	 * Returns an instant in time in nanoseconds.
	 * @return an instant in time in nanoseconds.
	 */
	public static long nanoTime() {
		return System.nanoTime();
	}

	/**
	 * Returns the current time in milliseconds.
	 * @return the current time in milliseconds.
	 */
	public static final long currentTimeMillis() {
		return System.currentTimeMillis();
	}

	/**
	 * Returns the current time in seconds.
	 * @return the current time in seconds.
	 */
	public static final int currentTimeSeconds() {
		return (int) (System.currentTimeMillis() / 1000);
	}

	/**
	 * Returns the maximum system memory the JVM can allocate.
	 * @return the maximum system memory the JVM can allocate.
	 */
	public static long maxMemory() {
		return Runtime.getRuntime().maxMemory();
	}

	/**
	 * Returns the current system memory the JVM has allocated.
	 * @return the current system memory the JVM has allocated.
	 */
	public static long totalMemory() {
		return Runtime.getRuntime().totalMemory();
	}

	/**
	 * Returns the free memory within the JVM.
	 * @return the free memory within the JVM.
	 */
	public static long freeMemory() {
		Runtime runtime = Runtime.getRuntime();
		long used = runtime.totalMemory() - runtime.freeMemory();
		return runtime.maxMemory() - used;
	}

	/**
	 * Returns the used memory within the JVM.
	 * @return the used memory within the JVM.
	 */
	public static long usedMemory() {
		Runtime runtime = Runtime.getRuntime();
		return runtime.totalMemory() - runtime.freeMemory();
	}

	/**
	 * Propagate the given throwable, converting any checked exception to a {@link RuntimeException}.
	 * @param t the throwable to propagate.
	 * @return nothing, a {@link RuntimeException} or {@link Error} is thrown.
	 */
	public static RuntimeException propagate(Throwable t) {
		Throwables.throwIfUnchecked(t);
		throw new RuntimeException(t);
	}

	/**
	 * Terminate the JVM. This method will block indefinitely.
	 * @see java.lang.Runtime#exit(int)
	 */
	public static void exit(int status) {
		Runtime.getRuntime().exit(status);
	}

	/**
	 * Terminate the JVM. This method is non-blocking.
	 * @see java.lang.Runtime#exit(int)
	 */
	public static void exitAsync(int status) {
		SystemExit.exitAsync(status);
	}

	/**
	 * Returns the working directory of this JVM process.
	 * @return the working directory of this JVM process.
	 */
	public static String getWorkingDirectory() {
		return SystemProperties.getWorkingDirectory();
	}

	/**
	 * Returns the process id of this JVM process.
	 * @return the process id of this JVM process.
	 */
	public static long getProcessId() {
		String name = ManagementFactory.getRuntimeMXBean().getName();
		int index = name.indexOf('@');
		return Long.parseLong(name.substring(0, index));
	}

	/**
	 * Returns the (primary) address of this JVM process.
	 * @return the (primary) address of this JVM process.
	 */
	public static InetAddress getLocalHost() {
		try {

			// Resolve the local host
			InetAddress host = InetAddress.getLocalHost();
			cachedHost.set(host);
			return host;

		} catch (Exception e) {

			// Fallback on the cached host (if available)
			InetAddress host = cachedHost.get();
			if (host != null) {
				return host;
			}

			throw propagate(e);
		}
	}

	/**
	 * Returns the (primary) hostname of this JVM process.
	 * @return the (primary) hostname of this JVM process.
	 */
	public static String getHostName() {
		return getLocalHost().getHostName();
	}

	/**
	 * Returns the (primary) host address of this JVM process.
	 * @return the (primary) host address of this JVM process.
	 */
	public static String getHostAddress() {
		return getLocalHost().getHostAddress();
	}

	public static String getOperatingSystem() {
		return SystemProperties.getOperatingSystem();
	}

	public static String getUserName() {
		return SystemProperties.getUserName();
	}

	public static String getTimeZone() {
		return SystemProperties.getTimeZone();
	}

	public static String getFileEncoding() {
		return SystemProperties.getFileEncoding();
	}

	public static String getFileSeparator() {
		return SystemProperties.getFileSeparator();
	}

	public static String getPathSeparator() {
		return SystemProperties.getPathSeparator();
	}

	public static String getLineSeparator() {
		return SystemProperties.getLineSeparator();
	}

	public static String getClassPath() {
		return SystemProperties.getClassPath();
	}

	public static String getLibraryPath() {
		return SystemProperties.getLibraryPath();
	}

	public static String getJavaVersion() {
		return SystemProperties.getJavaVersion();
	}

	public static String getJavaRuntimeVersion() {
		return SystemProperties.getJavaRuntimeVersion();
	}

	public static long getSystemMaxMemory(long defaultValue) {
		try {
			OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
			return bean.getTotalPhysicalMemorySize();
		} catch (Exception e) {
			log.warn("Unable to get system memory", e);
			return defaultValue;
		}
	}

	public static long getSystemUsedMemory(long defaultValue) {
		try {
			OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
			long free = bean.getFreePhysicalMemorySize();
			long total = bean.getTotalPhysicalMemorySize();
			return total - free;
		} catch (Exception e) {
			log.warn("Unable to get system memory", e);
			return defaultValue;
		}
	}

	public static long getSystemFreeMemory(long defaultValue) {
		try {
			OperatingSystemMXBean bean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
			return bean.getFreePhysicalMemorySize();
		} catch (Exception e) {
			log.warn("Unable to get system memory", e);
			return defaultValue;
		}
	}

}

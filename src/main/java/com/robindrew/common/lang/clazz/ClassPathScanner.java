package com.robindrew.common.lang.clazz;

import static java.util.Collections.synchronizedSet;

import java.io.File;
import java.io.FileInputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.robindrew.common.concurrent.CountDown;
import com.robindrew.common.concurrent.CountDownRunnable;
import com.robindrew.common.io.file.DirectoryLister;

public class ClassPathScanner implements IClassPathScanner {

	private static final Logger log = LoggerFactory.getLogger(ClassPathScanner.class);

	private static final String CLASSPATH_PROPERTY = "java.class.path";
	private static final String PATH_SEPARATOR_PROPERTY = "path.separator";

	private final boolean lowerCase;
	private volatile int threads = 6;
	private volatile int classCount = 0;
	private volatile int jarCount = 0;
	private volatile int directoryCount = 0;
	private Set<String> duplicateSet = synchronizedSet(new HashSet<String>());
	private Map<String, String> nameToFullMap = new ConcurrentHashMap<String, String>();

	public ClassPathScanner(boolean lowerCase) {
		this.lowerCase = lowerCase;
	}

	public ClassPathScanner() {
		this(false);
	}

	public void setThreads(int threads) {
		if (threads < 1) {
			throw new IllegalArgumentException("threads=" + threads);
		}
		this.threads = threads;
	}

	@Override
	public Map<String, String> scanClassPath() {
		log.info("Scanning classpath ...");
		Stopwatch timer = Stopwatch.createStarted();
		timer.start();

		// Concurrent lookup is MUCH FASTER
		ExecutorService service = Executors.newFixedThreadPool(threads);

		// Scan the class path
		String classPath = System.getProperty(CLASSPATH_PROPERTY);
		String separator = System.getProperty(PATH_SEPARATOR_PROPERTY);
		String[] paths = classPath.split(separator);
		final CountDown count = new CountDown();
		for (final String path : paths) {
			service.submit(new CountDownRunnable(count) {

				@Override
				public void runCount() {
					scanPath(path);
				}
			});
		}

		// Wait for completion
		count.waitFor();
		service.shutdown();
		timer.stop();

		NumberFormat format = DecimalFormat.getInstance();
		log.info("Scanned " + format.format(jarCount) + " jar files");
		log.info("Scanned " + format.format(directoryCount) + " directories");
		log.info("Found " + format.format(classCount) + " classes");
		log.info("Found " + format.format(nameToFullMap.size()) + " uniquely named classes");
		log.info("Found " + format.format(duplicateSet.size()) + " duplicated class names");
		log.info("Scanned class path in " + timer);
		return new HashMap<String, String>(nameToFullMap);
	}

	private void scanPath(String path) {
		if (path.toLowerCase().endsWith(".jar")) {
			jarCount++;
			scanJar(path);
			return;
		}

		File directory = new File(path);
		if (directory.isDirectory()) {
			directoryCount++;
			scanDirectory(directory, directory.getAbsolutePath());
			return;
		}
	}

	private void scanDirectory(File directory, String root) {
		if (!directory.exists()) {
			return;
		}
		List<File> files = new DirectoryLister().listFiles(directory);
		for (File file : files) {
			if (file.isDirectory()) {
				scanDirectory(file, root);
			} else {
				scanClass(file.getAbsolutePath(), root);
			}
		}
	}

	private void scanJar(String path) {
		try (JarInputStream input = new JarInputStream(new FileInputStream(path))) {
			while (true) {
				JarEntry entry = input.getNextJarEntry();
				if (entry == null) {
					return;
				}
				scanClass(entry.getName(), "");
			}
		} catch (Exception e) {
			log.warn("Error scanning jar: '" + path + "'", e);
		}
	}

	private void scanClass(String name, String prefix) {
		int index = name.toLowerCase().lastIndexOf(".class");
		if (index == -1) {
			return;
		}

		// Convert
		StringBuilder className = new StringBuilder();
		boolean first = true;
		for (int i = prefix.length(); i < index; i++) {
			char c = name.charAt(i);
			if (c == '/' || c == '\\') {
				c = '.';
				if (first) {
					first = false;
					continue;
				}
			}
			first = false;
			className.append(c);
		}

		// Done
		classCount++;
		String fullName = className.toString();
		index = fullName.lastIndexOf('.');
		String simpleName = fullName.substring(index + 1);
		if (lowerCase) {
			simpleName = simpleName.toLowerCase();
		}
		if (duplicateSet.contains(simpleName)) {
			return;
		}
		if (nameToFullMap.remove(simpleName) != null) {
			duplicateSet.add(simpleName);
			return;
		}
		nameToFullMap.put(simpleName, fullName);
	}

}

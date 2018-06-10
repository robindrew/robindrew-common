package com.robindrew.common.release.builder;

import static com.robindrew.common.util.Check.notNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.google.common.collect.ImmutableSet;
import com.robindrew.common.io.Files;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Java;

public class JarBuilder {

	private static final Logger log = LoggerFactory.getLogger(JarBuilder.class);

	private final File targetFile;
	private final Set<File> paths = new LinkedHashSet<>();

	public JarBuilder(File targetFile) {
		this.targetFile = notNull("targetFile", targetFile);
	}

	public File getTargetFile() {
		return targetFile;
	}

	public Set<File> getPaths() {
		return ImmutableSet.copyOf(paths);
	}

	public void addPath(File file) {
		paths.add(notNull("file", file));
	}

	public byte[] build() {

		// Sanity checks
		if (paths.isEmpty()) {
			throw new IllegalStateException("no paths specified");
		}

		List<File> files = new ArrayList<>();
		for (File path : paths) {
			files.addAll(Files.listContents(path));
		}

		log.info("[Jar] Writing {}", targetFile);
		Stopwatch timer = Stopwatch.createStarted();

		MessageDigest md5 = newMessgeDigest();
		int fileCount = writeFromFiles(targetFile, files, md5);

		byte[] digested = md5.digest();

		timer.stop();
		log.info("[Jar] Written {} ({} files) in {}", targetFile, fileCount, timer);

		return digested;
	}

	private MessageDigest newMessgeDigest() {
		try {
			return MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw Java.propagate(e);
		}
	}

	public int writeFromFiles(File jarFile, Collection<? extends File> files, MessageDigest md5) {
		Check.notEmpty("files", files);

		// Default manifest
		Manifest manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");

		// Build the jar
		try (JarOutputStream output = new JarOutputStream(new FileOutputStream(jarFile), manifest)) {

			int fileCount = 0;
			for (File file : files) {
				fileCount += writeJarEntry(output, "", file, md5, 0);
			}
			output.finish();

			return fileCount;
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	private int writeJarEntry(JarOutputStream jar, String path, File file, MessageDigest md5, int fileCount) throws Exception {

		// Directory
		if (file.isDirectory()) {

			// Directory path
			path = path + file.getName() + "/";

			// Build JAR from contents
			for (File child : Files.listContents(file)) {
				fileCount += writeJarEntry(jar, path, child, md5, 0);
			}

			return fileCount;
		}

		// Regular file
		else {

			// Filename
			path = path + file.getName();

			// Create entry
			JarEntry entry = new JarEntry(path);
			jar.putNextEntry(entry);

			// Stream file contents
			byte[] buffer = new byte[1000000];
			try (InputStream input = new FileInputStream(file)) {
				while (true) {
					int read = input.read(buffer);
					if (read == -1) {
						break;
					}
					md5.update(buffer, 0, read);
					jar.write(buffer, 0, read);
				}
			}
			jar.flush();
			jar.closeEntry();

			return fileCount + 1;
		}
	}

}

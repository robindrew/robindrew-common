package com.robindrew.common.io.file.jar;

import static com.robindrew.common.io.Files.listContents;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;

import com.google.common.io.ByteSource;
import com.google.common.io.ByteStreams;
import com.robindrew.common.io.Files;
import com.robindrew.common.util.Check;
import com.robindrew.common.util.Java;

public class JarFile {

	public static void main(String[] args) {
		JarFile file = new JarFile("C:/temp/robindrew-common2.jar");
		file.writeFromDirectory("C:\\development\\repository\\git\\robindrew-public\\robindrew-common\\bin");
		file.readToDirectory("C:/temp/robindrew-common/");
	}

	private final File file;
	private Manifest manifest;
	private boolean includeDirectoryEntries = false;

	public JarFile(File file) {
		if (!file.getName().endsWith(".jar")) {
			throw new IllegalArgumentException("file: '" + file + "'");
		}
		this.file = file;

		// Default manifest
		manifest = new Manifest();
		manifest.getMainAttributes().put(Attributes.Name.MANIFEST_VERSION, "1.0");
	}

	public JarFile(String filename) {
		this(new File(filename));
	}

	public void setIncludeDirectoryEntries(boolean include) {
		this.includeDirectoryEntries = include;
	}

	public void setManifest(Manifest manifest) {
		this.manifest = Check.notNull("manifest", manifest);
	}

	public void readToDirectory(String directory) {
		readToDirectory(new File(directory));
	}

	public void readToDirectory(File directory) {
		if (!directory.exists()) {
			throw new IllegalArgumentException("Directory does not exist: " + directory);
		}
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException("Not a directory: " + directory);
		}

		try (JarInputStream input = new JarInputStream(new FileInputStream(file))) {
			while (true) {

				JarEntry entry = input.getNextJarEntry();
				if (entry == null) {
					break;
				}

				readJarEntry(entry, input, directory);
			}

		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	private void readJarEntry(JarEntry entry, JarInputStream input, File directory) throws Exception {

		// Skip directories (they are optional anyhow)
		if (entry.isDirectory()) {
			return;
		}

		// Create the file
		File outputFile = new File(directory, entry.getName());

		// Create the parent directories if necessary
		outputFile.getParentFile().mkdirs();

		// Stream from the jar to the file
		try (FileOutputStream output = new FileOutputStream(outputFile)) {
			ByteStreams.copy(input, output);
			output.flush();
		}
	}

	public void writeFromDirectory(String directory) {
		writeFromDirectory(new File(directory));
	}

	public void writeFromDirectory(File directory) {
		if (!directory.exists()) {
			throw new IllegalArgumentException("Directory does not exist: " + directory);
		}
		if (!directory.isDirectory()) {
			throw new IllegalArgumentException("Not a directory: " + directory);
		}

		// Root level files to write
		List<File> files = listContents(directory);
		if (files.isEmpty()) {
			throw new IllegalArgumentException("No files found in directory: " + directory);
		}

		// Build the jar
		try (JarOutputStream output = new JarOutputStream(new FileOutputStream(file), manifest)) {

			for (File file : files) {
				writeJarEntry(output, "", file);
			}
			output.finish();

		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	private void writeJarEntry(JarOutputStream jar, String path, File file) throws Exception {

		// Directory
		if (file.isDirectory()) {

			// Directory path
			path = path + file.getName() + "/";

			// Include as an entry?
			if (includeDirectoryEntries) {
				JarEntry entry = new JarEntry(path);
				jar.putNextEntry(entry);
				jar.closeEntry();
			}

			// Build JAR from contents
			for (File child : Files.listContents(file)) {
				writeJarEntry(jar, path, child);
			}
		}

		// Regular file
		else {

			// Filename
			path = path + file.getName();

			// Create entry
			JarEntry entry = new JarEntry(path);
			jar.putNextEntry(entry);

			// Stream file contents
			ByteSource contents = Files.asByteSource(file);
			contents.copyTo(jar);
			jar.flush();
			jar.closeEntry();
		}
	}

}

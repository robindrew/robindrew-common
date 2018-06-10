package com.robindrew.common.release.builder;

import static com.robindrew.common.util.Check.notNull;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Stopwatch;
import com.robindrew.common.concurrent.ThreadPool;
import com.robindrew.common.io.Files;
import com.robindrew.common.release.ReleaseProject;
import com.robindrew.common.release.config.Release;
import com.robindrew.common.release.config.ReleaseVersion;
import com.robindrew.common.text.Strings;
import com.robindrew.common.util.Check;

public class ReleaseBuilder {

	private static final Logger log = LoggerFactory.getLogger(ReleaseBuilder.class);

	private final Set<ReleaseProject> projects = new LinkedHashSet<>();
	private File mavenDir;

	public ReleaseBuilder setConfig(File file) {
		log.info("[Config] File: {}", file);
		ReleaseBuilderConfig config = ReleaseBuilderConfig.fromFile(file);
		return setConfig(config);
	}

	public ReleaseBuilder setConfig(ReleaseBuilderConfig config) {
		setMavenDir(config.getMavenDir());
		for (File project : config.getReleaseProjects()) {
			addProject(project);
		}
		return this;
	}

	public ReleaseBuilder setMavenDir(File mavenDir) {
		this.mavenDir = Check.existsDirectory("mavenDir", mavenDir);
		return this;
	}

	public ReleaseBuilder addProject(File path) {
		return addProject(new ReleaseProject(path));
	}

	public ReleaseBuilder addProject(ReleaseProject project) {
		if (projects.add(notNull("project", project))) {
			log.info("[Project] {}", project.getName());
		}
		return this;
	}

	public void build() {
		if (mavenDir == null) {
			throw new IllegalStateException("Maven directory not set");
		}

		log.info("[Release] Building ...");
		Stopwatch timer = Stopwatch.createStarted();

		// Build releases in parallel
		ThreadPool pool = new ThreadPool();
		for (ReleaseProject project : projects) {
			ReleaseProjectBuilder builder = new ReleaseProjectBuilder(project);
			pool.register(project.getName(), builder);
		}
		pool.run();

		timer.stop();
		log.info("[Release] Built in {}", timer);
	}

	public File getJarFile(ReleaseProject project) {
		File targetDir = new File(project.getPath(), "target");
		targetDir.mkdir();
		return new File(targetDir, project.getName() + ".jar");
	}

	private void buildProject(ReleaseProject project) {
		File jarFile = getJarFile(project);

		// Build the jar
		byte[] md5 = buildJar(project, jarFile);

		// Check the release
		releaseJar(project, jarFile, md5);
	}

	private void releaseJar(ReleaseProject project, File tempJarFile, byte[] md5) {

		// MD5 the latest JAR
		String hash = Strings.hex(md5);
		log.info("[Hash] {} -> {}", tempJarFile.getName(), hash);

		Release release = project.getRelease();
		String latestMajorVersion = release.getVersion() + ".";
		int latestMinorVersion = 1;

		for (ReleaseVersion version : release.getReleaseVersions()) {

			// Does this version already exist?
			if (version.getHash().equals(hash)) {
				log.warn("Version already exists: {} for {} with hash {}", version.getVersion(), tempJarFile.getName(), hash);

				// Create a new release jar
				File newFile = getTargetJarFile(project, tempJarFile, release, version);
				if (newFile.exists()) {
					Files.deleteFile(tempJarFile);
				} else {
					tempJarFile.renameTo(newFile);
				}

				return;
			}

			// Ensure the minor version number is correct
			if (version.getVersion().startsWith(latestMajorVersion)) {
				int minorVersion = Integer.parseInt(version.getVersion().substring(latestMajorVersion.length()));
				if (latestMinorVersion <= minorVersion) {
					latestMinorVersion = minorVersion + 1;
				}
			}
		}

		// Create a new release jar
		ReleaseVersion version = new ReleaseVersion(hash, latestMajorVersion + latestMinorVersion);
		File newFile = getTargetJarFile(project, tempJarFile, release, version);
		tempJarFile.renameTo(newFile);

		// Update the .release file
		release.addReleaseVersion(version);
		release.toFile(project.getReleaseFile());
	}

	private String getGroupPath(ReleaseProject project, ReleaseVersion version) {
		Release release = project.getRelease();
		return release.getGroupId().replace('.', '/') + "/" + release.getArtifactId() + "/" + version.getVersion() + "/";
	}

	private File getTargetJarFile(ReleaseProject project, File jarFile, Release release, ReleaseVersion version) {
		log.info("[Version] {}", version.getVersion());

		String newFilename = release.getArtifactId() + "." + version.getVersion() + ".jar";
		File groupDir = new File(mavenDir, getGroupPath(project, version));
		groupDir.mkdirs();
		File file = new File(groupDir, newFilename);
		log.info("[Jar] {}", file);
		return file;
	}

	private byte[] buildJar(ReleaseProject project, File jarFile) {
		log.info("[Jar] Building {}", jarFile.getAbsolutePath());
		Stopwatch timer = Stopwatch.createStarted();

		JarBuilder jar = new JarBuilder(jarFile);
		jar.addPath(new File(project.getPath(), "bin"));
		byte[] md5 = jar.build();

		timer.stop();
		log.info("[Jar] Built {} in {}", jarFile.getAbsolutePath(), timer);

		return md5;
	}

	private class ReleaseProjectBuilder implements Runnable {

		private final ReleaseProject project;

		public ReleaseProjectBuilder(ReleaseProject project) {
			this.project = project;
		}

		@Override
		public void run() {
			buildProject(project);
		}

	}
}

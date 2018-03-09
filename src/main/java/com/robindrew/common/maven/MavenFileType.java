package com.robindrew.common.maven;

import com.robindrew.common.util.Check;

public enum MavenFileType implements IMavenFileType {

	/** JAR file. */
	JAR(".jar"),
	/** SOURCES JAR file. */
	SOURCES_JAR("-sources.jar"),
	/** JAVADOC JAR file. */
	JAVADOC_JAR("-javadoc.jar"),
	/** POM file. */
	POM(".pom");

	public static final String getFilename(IMavenArtifact artifact, String suffix) {
		StringBuilder name = new StringBuilder();

		// Filename
		name.append(artifact.getArtifactId());
		name.append('-');
		name.append(artifact.getVersion());
		name.append(suffix);
		return name.toString();
	}

	public static final String getPath(IMavenArtifact artifact, String suffix) {
		StringBuilder path = new StringBuilder();

		// Path
		path.append(artifact.getGroupId().replace('.', '/'));
		path.append('/');
		path.append(artifact.getArtifactId());
		path.append('/');
		path.append(artifact.getVersion());
		path.append('/');

		// Filename
		path.append(artifact.getArtifactId());
		path.append('-');
		path.append(artifact.getVersion());
		path.append(suffix);
		return path.toString();
	}

	private final String suffix;

	private MavenFileType(String suffix) {
		this.suffix = Check.notEmpty("suffix", suffix);
	}

	@Override
	public String getFilename(IMavenArtifact artifact) {
		return getFilename(artifact, suffix);
	}

	@Override
	public String getPath(IMavenArtifact artifact) {
		return getPath(artifact, suffix);
	}

}

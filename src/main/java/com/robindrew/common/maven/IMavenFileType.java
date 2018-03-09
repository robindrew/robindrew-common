package com.robindrew.common.maven;

public interface IMavenFileType {

	String getFilename(IMavenArtifact artifact);

	String getPath(IMavenArtifact artifact);

}

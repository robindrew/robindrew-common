package com.robindrew.common.maven;

import java.io.File;

import com.google.common.base.Optional;

public interface IMavenRepository {

	boolean canGet(IMavenArtifact artifact, IMavenFileType type);

	boolean canPut(IMavenArtifact artifact, IMavenFileType type);

	byte[] get(IMavenArtifact artifact, IMavenFileType type);

	void put(IMavenArtifact artifact, IMavenFileType type, byte[] bytes);

	Optional<File> getLocalPath(IMavenArtifact artifact, IMavenFileType type);
}

package com.robindrew.common.maven;

import com.robindrew.common.text.Strings;
import com.robindrew.common.util.Check;

public class MavenArtifact implements IMavenArtifact {

	private final String groupId;
	private final String artifactId;
	private final String version;

	public MavenArtifact(String groupId, String artifactId, String version) {
		this.groupId = Check.notEmpty("groupId", groupId);
		this.artifactId = Check.notEmpty("artifactId", artifactId);
		this.version = Check.notEmpty("version", version);
	}

	@Override
	public String getGroupId() {
		return groupId;
	}

	@Override
	public String getArtifactId() {
		return artifactId;
	}

	@Override
	public String getVersion() {
		return version;
	}

	@Override
	public String toString() {
		return Strings.toString(this);
	}

}

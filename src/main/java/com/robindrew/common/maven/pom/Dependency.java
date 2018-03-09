package com.robindrew.common.maven.pom;

import org.simpleframework.xml.Element;

import com.robindrew.common.maven.IMavenArtifact;
import com.robindrew.common.xml.simple.SimpleWriter;

public class Dependency implements IMavenArtifact {

	@Element
	private String groupId;
	@Element
	private String artifactId;
	@Element
	private String version;
	@Element(required = false)
	private String scope;

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

	public String getScope() {
		return scope;
	}

	@Override
	public String toString() {
		return new SimpleWriter().writeToString(this);
	}

}

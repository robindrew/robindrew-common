package com.robindrew.common.release.config;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.util.Check;
import com.robindrew.common.xml.simple.SimpleReader;
import com.robindrew.common.xml.simple.SimpleWriter;

public class Release {

	public static final String FILENAME = ".release";

	public static Release fromFile(File file) {
		return new SimpleReader().readFile(Release.class, file);
	}

	public static Release fromResource(String resource) {
		return new SimpleReader().readResource(Release.class, resource);
	}

	@Element
	private String groupId;
	@Element
	private String artifactId;
	@Element
	private String version;

	@ElementList(required = false, entry = "releaseVersion")
	private ArrayList<ReleaseVersion> releaseVersions = new ArrayList<>();

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public String getVersion() {
		return version;
	}

	public List<ReleaseVersion> getReleaseVersions() {
		return ImmutableList.copyOf(releaseVersions);
	}

	public void addReleaseVersion(ReleaseVersion version) {
		Check.notNull("version", version);
		releaseVersions.add(version);
	}

	public void toFile(File file) {
		new SimpleWriter().writeToFile(this, file);
	}

	@Override
	public String toString() {
		return new SimpleWriter().writeToString(this);
	}

}

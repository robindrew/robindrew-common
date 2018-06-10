package com.robindrew.common.release.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.xml.simple.SimpleReader;

public class ReleaseBuilderConfig {

	public static ReleaseBuilderConfig fromFile(File file) {
		return new SimpleReader().readFile(ReleaseBuilderConfig.class, file);
	}

	public static ReleaseBuilderConfig fromResource(String resource) {
		return new SimpleReader().readResource(ReleaseBuilderConfig.class, resource);
	}

	@Element
	private File mavenDir;
	@ElementList(inline = true, entry = "releaseProject")
	private List<File> releaseProjects = new ArrayList<>();

	public File getMavenDir() {
		return mavenDir;
	}

	public List<File> getReleaseProjects() {
		return ImmutableList.copyOf(releaseProjects);
	}

}

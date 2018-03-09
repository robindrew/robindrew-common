package com.robindrew.common.maven.pom;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.xml.simple.SimpleReader;
import com.robindrew.common.xml.simple.SimpleWriter;

@Root(name = "project")
public class Project {

	public static final String POM_FILENAME = "pom.xml";

	public static Project readFromDefaultPomFile() {
		return readFromFile(new File(POM_FILENAME));
	}

	public static final Project readFromFile(File file) {
		return new SimpleReader().readFile(Project.class, file);
	}

	@Attribute
	private String schemaLocation;

	@Element
	private String modelVersion;
	@Element
	private String groupId;
	@Element
	private String artifactId;
	@Element
	private String version;

	@ElementList(entry = "dependency", type = Dependency.class)
	private List<Dependency> dependencies = new ArrayList<>();

	public String getSchemaLocation() {
		return schemaLocation;
	}

	public String getModelVersion() {
		return modelVersion;
	}

	public String getGroupId() {
		return groupId;
	}

	public String getArtifactId() {
		return artifactId;
	}

	public String getVersion() {
		return version;
	}

	public List<Dependency> getDependencies() {
		return ImmutableList.copyOf(dependencies);
	}

	@Override
	public String toString() {
		return new SimpleWriter().writeToString(this);
	}

}

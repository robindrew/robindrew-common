package com.robindrew.common.eclipse.project;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.xml.simple.SimpleReader;
import com.robindrew.common.xml.simple.SimpleWriter;

public class Project {

	public static Project fromFile(File file) {
		return new SimpleReader().readFile(Project.class, file);
	}

	public static Project fromResource(String resource) {
		return new SimpleReader().readResource(Project.class, resource);
	}

	@Element
	private String name;
	@Element(required = false)
	private String comment;
	@ElementList(required = false, entry = "project")
	private ArrayList<String> projects = new ArrayList<>();
	@Element(required = false)
	private BuildSpec buildSpec;
	@ElementList(required = false, entry = "nature")
	private ArrayList<String> natures = new ArrayList<>();
	@ElementList(required = false, entry = "variable")
	private ArrayList<Variable> variableList = new ArrayList<>();

	public String getName() {
		return name;
	}

	public String getComment() {
		return comment;
	}

	public ArrayList<String> getProjects() {
		return projects;
	}

	public BuildSpec getBuildSpec() {
		return buildSpec;
	}

	public List<String> getNatures() {
		return ImmutableList.copyOf(natures);
	}

	public List<Variable> getVariableList() {
		return ImmutableList.copyOf(variableList);
	}

	@Override
	public String toString() {
		return new SimpleWriter().writeToString(this);
	}

}

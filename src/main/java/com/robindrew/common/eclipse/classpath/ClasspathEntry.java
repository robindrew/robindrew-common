package com.robindrew.common.eclipse.classpath;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Root;

import com.robindrew.common.xml.simple.SimpleWriter;

@Root(name = "classpathentry")
public class ClasspathEntry {

	public static final String KIND_SOURCE = "src";
	public static final String KIND_VARIABLE = "var";
	public static final String KIND_OUTPUT = "output";

	@Attribute
	private String kind;
	@Attribute
	private String path;
	@Attribute(required = false)
	private String sourcepath;
	@Attribute(required = false)
	private Boolean exported;
	@Attribute(required = false)
	private Boolean combineaccessrules;

	public String getKind() {
		return kind;
	}

	public String getPath() {
		return path;
	}

	public String getSourcepath() {
		return sourcepath;
	}

	public Boolean getExported() {
		return exported;
	}

	public Boolean getCombineAccessRules() {
		return combineaccessrules;
	}

	public void setSourcepath(String sourcepath) {
		this.sourcepath = sourcepath;
	}

	public void setExported(Boolean exported) {
		this.exported = exported;
	}

	public void setKind(String kind) {
		this.kind = kind;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return new SimpleWriter().writeToString(this);
	}

	public boolean isSource() {
		return getKind().equals(KIND_SOURCE);
	}

	public boolean isProjectSource() {
		return getPath().startsWith("/");
	}

	public boolean isTestSource() {
		return getPath().contains("/test/");
	}

}

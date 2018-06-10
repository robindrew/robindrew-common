package com.robindrew.common.eclipse.classpath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.util.Check;
import com.robindrew.common.xml.simple.SimpleReader;
import com.robindrew.common.xml.simple.SimpleWriter;

@Root(name = "classpath")
public class Classpath {

	public static Classpath fromFile(File file) {
		return new SimpleReader().readFile(Classpath.class, file);
	}

	public static Classpath fromResource(String resource) {
		return new SimpleReader().readResource(Classpath.class, resource);
	}

	public static final String FILENAME = ".classpath";

	@ElementList(entry = "classpathentry", type = ClasspathEntry.class, inline = true)
	private List<ClasspathEntry> entryList = new ArrayList<>();

	public List<ClasspathEntry> getEntryList() {
		return ImmutableList.copyOf(entryList);
	}

	public void addEntry(ClasspathEntry entry) {
		Check.notNull("entry", entry);
		entryList.add(entry);
	}

	@Override
	public String toString() {
		return new SimpleWriter().writeToString(this);
	}

}

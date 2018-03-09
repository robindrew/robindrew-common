package com.robindrew.common.eclipse.classpath;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import com.google.common.collect.ImmutableList;
import com.robindrew.common.util.Check;
import com.robindrew.common.xml.simple.SimpleWriter;

@Root(name = "classpath")
public class Classpath {

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

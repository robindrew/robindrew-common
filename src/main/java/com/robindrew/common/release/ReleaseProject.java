package com.robindrew.common.release;

import static com.robindrew.common.util.Check.existsDirectory;

import java.io.File;

import com.robindrew.common.eclipse.classpath.Classpath;
import com.robindrew.common.eclipse.project.Project;
import com.robindrew.common.release.config.Release;

public class ReleaseProject implements Comparable<ReleaseProject> {

	private final File path;
	private final Project project;
	private final Classpath classpath;
	private final Release release;

	public ReleaseProject(File path) {
		this.path = existsDirectory("path", path);
		this.project = Project.fromFile(new File(path, Project.FILENAME));
		this.classpath = Classpath.fromFile(new File(path, Classpath.FILENAME));
		this.release = Release.fromFile(getReleaseFile());
	}

	public File getReleaseFile() {
		return new File(path, Release.FILENAME);
	}

	public String getName() {
		return path.getName();
	}

	public File getPath() {
		return path;
	}

	public Project getProject() {
		return project;
	}

	public Release getRelease() {
		return release;
	}

	public Classpath getClasspath() {
		return classpath;
	}

	@Override
	public String toString() {
		return path.getAbsolutePath();
	}

	@Override
	public int hashCode() {
		return path.hashCode();
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object instanceof ReleaseProject) {
			ReleaseProject that = (ReleaseProject) object;
			return this.getPath().equals(that.getPath());
		}
		return false;
	}

	@Override
	public int compareTo(ReleaseProject that) {
		return this.getPath().compareTo(that.getPath());
	}

}

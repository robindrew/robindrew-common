package com.robindrew.common.maven.eclipse;

import static com.robindrew.common.maven.MavenFileType.JAR;
import static com.robindrew.common.maven.MavenFileType.SOURCES_JAR;

import java.io.File;

import com.google.common.base.Optional;
import com.robindrew.common.eclipse.classpath.ClasspathEntry;
import com.robindrew.common.maven.IMavenArtifact;
import com.robindrew.common.maven.IMavenRepository;
import com.robindrew.common.util.Check;

public class ClasspathGenerator {

	private final IMavenRepository repository;

	public ClasspathGenerator(IMavenRepository repository) {
		this.repository = Check.notNull("repository", repository);
	}

	public Optional<ClasspathEntry> generate(IMavenArtifact artifact) {
		Optional<File> path = repository.getLocalPath(artifact, JAR);
		if (!path.isPresent()) {
			return Optional.absent();
		}

		ClasspathEntry entry = new ClasspathEntry();
		entry.setKind("lib");

		// Path
		entry.setPath(path.get().getAbsolutePath());

		// Sourcepath
		Optional<File> sourcepath = repository.getLocalPath(artifact, SOURCES_JAR);
		if (sourcepath.isPresent()) {
			entry.setSourcepath(sourcepath.get().getAbsolutePath());
		}

		return Optional.of(entry);
	}

}

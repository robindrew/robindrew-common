package com.robindrew.common.maven.eclipse;

import static com.robindrew.common.maven.MavenFileType.JAR;
import static com.robindrew.common.maven.MavenFileType.SOURCES_JAR;
import static com.robindrew.common.maven.pom.Project.readFromDefaultPomFile;
import static com.robindrew.common.maven.repository.HttpRepository.DEFAULT_MAVEN_URLS;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.robindrew.common.eclipse.classpath.ClasspathEntry;
import com.robindrew.common.lang.Args;
import com.robindrew.common.maven.IMavenRepository;
import com.robindrew.common.maven.pom.Dependency;
import com.robindrew.common.maven.pom.Project;
import com.robindrew.common.maven.repository.DirectoryRepository;
import com.robindrew.common.maven.repository.HttpRepository;

public class ClasspathGeneratorTool {

	private static final Logger log = LoggerFactory.getLogger(ClasspathGeneratorTool.class);

	public static void main(String[] array) {
		Args args = new Args(array);

		String directory = args.get("-d");

		IMavenRepository local = new DirectoryRepository(new File(directory));
		IMavenRepository http = new HttpRepository(DEFAULT_MAVEN_URLS);

		// Download the dependencies
		Project project = readFromDefaultPomFile();
		for (Dependency dependency : project.getDependencies()) {
			if (local.canGet(dependency, JAR)) {
				local.get(dependency, JAR);
				continue;
			}
			if (!http.canGet(dependency, JAR)) {
				log.warn("Unable to download dependency: " + dependency);
				continue;
			}

			// Download and cache
			byte[] bytes = http.get(dependency, JAR);
			local.put(dependency, JAR, bytes);

			// We would definitely like the source too!
			if (http.canGet(dependency, SOURCES_JAR)) {
				bytes = http.get(dependency, SOURCES_JAR);
				local.put(dependency, SOURCES_JAR, bytes);
			}
		}

		// Generate the classpath
		ClasspathGenerator generator = new ClasspathGenerator(local);
		for (Dependency dependency : project.getDependencies()) {
			Optional<ClasspathEntry> classpath = generator.generate(dependency);
			if (classpath.isPresent()) {
				System.out.println(classpath.get());
			}
		}
	}

}

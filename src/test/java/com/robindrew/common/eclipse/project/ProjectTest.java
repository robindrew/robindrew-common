package com.robindrew.common.eclipse.project;

import java.io.File;

import org.junit.Test;

public class ProjectTest {

	@Test
	public void readProjectFile() {

		Project project = Project.fromResource("com/robindrew/common/eclipse/project/.project");
		System.out.println(project);
	}
}

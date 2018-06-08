package com.robindrew.common.eclipse.project;

import org.junit.Assert;
import org.junit.Test;

public class ProjectTest {

	@Test
	public void readProjectFile() {

		Project project = Project.fromResource("com/robindrew/common/eclipse/project/.project");

		Assert.assertEquals("robindrew-common", project.getName());
	}
}

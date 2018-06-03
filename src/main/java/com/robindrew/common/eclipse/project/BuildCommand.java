package com.robindrew.common.eclipse.project;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

public class BuildCommand {

	@Element
	private String name;
	@ElementList(required = false, entry = "argument")
	private ArrayList<String> arguments = null;

	
}

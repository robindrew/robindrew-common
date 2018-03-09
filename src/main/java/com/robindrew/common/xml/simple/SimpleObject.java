package com.robindrew.common.xml.simple;

import java.io.File;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class SimpleObject {

	public void readFromFile(File file) {
		ISimpleReader reader = new SimpleReader();
		reader.readFile(this, file);
	}

	public void readFromFile(String filename) {
		ISimpleReader reader = new SimpleReader();
		reader.readFile(this, filename);
	}

	public void readFromString(String xml) {
		ISimpleReader reader = new SimpleReader();
		reader.readString(this, xml);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object object) {
		return EqualsBuilder.reflectionEquals(this, object);
	}

	@Override
	public String toString() {
		ISimpleWriter writer = new SimpleWriter();
		return writer.writeToString(this);
	}

}

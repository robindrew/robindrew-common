package com.robindrew.common.template.velocity;

import org.apache.velocity.Template;
import org.apache.velocity.app.VelocityEngine;

import com.robindrew.common.template.ITemplate;
import com.robindrew.common.template.ITemplateLocator;

public class VelocityTemplateLocator implements ITemplateLocator {

	private final VelocityEngine engine;

	public VelocityTemplateLocator(VelocityEngine engine) {
		if (engine == null) {
			throw new NullPointerException("engine");
		}
		this.engine = engine;
	}

	public VelocityEngine getEngine() {
		return engine;
	}

	@Override
	public ITemplate getTemplate(String name) {
		if (name.isEmpty()) {
			throw new IllegalArgumentException("name is empty");
		}
		Template template = engine.getTemplate(name);
		return new VelocityTemplate(template, this);
	}
}

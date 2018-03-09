package com.robindrew.common.template.velocity;

import java.util.Map;
import java.util.Map.Entry;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;

import com.robindrew.common.io.writer.IWriter;
import com.robindrew.common.io.writer.StringBuilderWriter;
import com.robindrew.common.template.ITemplate;
import com.robindrew.common.template.ITemplateData;
import com.robindrew.common.template.ITemplateLocator;
import com.robindrew.common.template.TemplateData;

public class VelocityTemplate implements ITemplate {

	private final Template template;
	private final ITemplateLocator locator;

	public VelocityTemplate(Template template, ITemplateLocator locator) {
		if (template == null) {
			throw new NullPointerException("template");
		}
		if (locator == null) {
			throw new NullPointerException("locator");
		}
		this.template = template;
		this.locator = locator;
	}

	@Override
	public String getName() {
		return template.getName();
	}

	@Override
	public ITemplateLocator getLocator() {
		return locator;
	}

	@Override
	public String execute(Map<String, ?> map) {
		return execute(new TemplateData(map));
	}

	@Override
	public String execute(ITemplateData data) {
		IWriter writer = new StringBuilderWriter();
		execute(data, writer);
		return writer.toString();
	}

	@Override
	public void execute(ITemplateData data, IWriter writer) {
		VelocityContext context = new VelocityContext();
		for (Entry<String, Object> entry : data.toMap().entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}
		template.merge(context, writer.asWriter());
	}

}

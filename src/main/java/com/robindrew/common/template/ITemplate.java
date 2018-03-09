package com.robindrew.common.template;

import java.util.Map;

import com.robindrew.common.io.writer.IWriter;

public interface ITemplate {

	String getName();

	ITemplateLocator getLocator();

	String execute(Map<String, ?> map);

	String execute(ITemplateData data);

	void execute(ITemplateData data, IWriter writer);

}

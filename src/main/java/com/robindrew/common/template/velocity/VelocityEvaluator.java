package com.robindrew.common.template.velocity;

import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.context.Context;

import com.robindrew.common.template.evaluator.AbstractTemplateEvaluator;

public class VelocityEvaluator extends AbstractTemplateEvaluator {

	@Override
	public void evaluate(Reader reader, Writer writer, Map<String, Object> parameters) {
		Context context = new VelocityContext();
		for (Entry<String, Object> entry : parameters.entrySet()) {
			context.put(entry.getKey(), entry.getValue());
		}
		Velocity.evaluate(context, writer, "VelocityEvaluator", reader);
	}

	@Override
	public String evaluate(String template, Map<String, Object> parameters) {
		StringReader reader = new StringReader(template);
		return evaluate(reader, parameters);
	}

	@Override
	public String evaluate(Reader reader, Map<String, Object> parameters) {
		StringWriter writer = new StringWriter();
		evaluate(reader, writer, parameters);
		return writer.toString();
	}
}

package com.robindrew.common.template.velocity.map;

import java.nio.charset.Charset;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.log.LogChute;

import com.google.common.base.Charsets;
import com.google.common.base.Supplier;
import com.robindrew.common.template.ITemplateLocator;
import com.robindrew.common.template.velocity.Slf4jLogChute;
import com.robindrew.common.template.velocity.VelocityTemplateLocator;

public class VelocityMapTemplateLocatorSupplier implements Supplier<ITemplateLocator> {

	private Charset inputEncoding = Charsets.UTF_8;
	private Charset outputEncoding = Charsets.UTF_8;
	private IVelocityMap map = new VelocityMap();
	private LogChute logChute = new Slf4jLogChute();
	private boolean strict = false;

	public void setMap(IVelocityMap map) {
		if (map == null) {
			throw new NullPointerException("map");
		}
		this.map = map;
	}

	public IVelocityMap getMap() {
		return map;
	}

	public Charset getInputEncoding() {
		return inputEncoding;
	}

	public Charset getOutputEncoding() {
		return outputEncoding;
	}

	public LogChute getLogChute() {
		return logChute;
	}

	public VelocityMapTemplateLocatorSupplier setLogChute(LogChute logChute) {
		if (logChute == null) {
			throw new NullPointerException("logChute");
		}
		this.logChute = logChute;
		return this;
	}

	public VelocityMapTemplateLocatorSupplier setInputEncoding(Charset charset) {
		if (charset == null) {
			throw new NullPointerException("charset");
		}
		inputEncoding = charset;
		return this;
	}

	public VelocityMapTemplateLocatorSupplier setOutputEncoding(Charset charset) {
		if (charset == null) {
			throw new NullPointerException("charset");
		}
		outputEncoding = charset;
		return this;
	}

	@Override
	public ITemplateLocator get() {
		VelocityEngine engine = new VelocityEngine();
		engine.setProperty(RuntimeConstants.INPUT_ENCODING, inputEncoding.name());
		engine.setProperty(RuntimeConstants.OUTPUT_ENCODING, outputEncoding.name());
		engine.setProperty(RuntimeConstants.RUNTIME_LOG_LOGSYSTEM, getLogChute());
		engine.setProperty(RuntimeConstants.RESOURCE_LOADER, "map");
		engine.setProperty("map.resource.loader.class", VelocityMapResourceLoader.class.getName());
		engine.setProperty("map.resource.loader.map", map);
		if (strict) {
			engine.setProperty("runtime.references.strict", strict);
		}
		engine.init();
		return new VelocityTemplateLocator(engine);
	}
}

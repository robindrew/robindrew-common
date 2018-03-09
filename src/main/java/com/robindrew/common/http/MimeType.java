package com.robindrew.common.http;

import com.robindrew.common.util.Check;

public class MimeType {

	public static final MimeType TEXT_PLAIN = new MimeType("text/plain", true);
	public static final MimeType TEXT_HTML = new MimeType("text/html", true);
	public static final MimeType TEXT_CSS = new MimeType("text/css", true);
	public static final MimeType TEXT_XML = new MimeType("text/xml", true);
	public static final MimeType TEXT_CSV = new MimeType("text/csv", true);
	public static final MimeType TEXT_RTF = new MimeType("text/rtf", true);

	public static final MimeType IMAGE_ICON = new MimeType("image/x-icon", false);
	public static final MimeType IMAGE_JPEG = new MimeType("image/jpeg", false);
	public static final MimeType IMAGE_PNG = new MimeType("image/png", false);
	public static final MimeType IMAGE_GIF = new MimeType("image/gif", false);

	public static final MimeType VIDEO_MPEG = new MimeType("video/mpeg", false);
	public static final MimeType VIDEO_MP4 = new MimeType("video/mp4", false);

	public static final MimeType APPLICATION_JSON = new MimeType("application/json", true);
	public static final MimeType APPLICATION_JAVASCRIPT = new MimeType("application/javascript", true);

	public static MimeType withName(String name) {
		int dotIndex = name.lastIndexOf('.');
		if (dotIndex == -1) {
			dotIndex = 0;
		}
		String extension = name.substring(dotIndex + 1);
		return withExtension(extension);
	}

	public static MimeType withExtension(String extension) {
		extension = extension.toLowerCase();

		// Text types
		if (extension.equals("txt")) {
			return TEXT_PLAIN;
		}
		if (extension.equals("html") || extension.endsWith("htm")) {
			return TEXT_HTML;
		}
		if (extension.equals("xml")) {
			return TEXT_XML;
		}
		if (extension.equals("css")) {
			return TEXT_CSS;
		}
		if (extension.equals("csv")) {
			return TEXT_CSV;
		}
		if (extension.equals("rtf")) {
			return TEXT_RTF;
		}

		// Application types
		if (extension.equals("json")) {
			return APPLICATION_JSON;
		}
		if (extension.equals("js") || extension.equals("javascript")) {
			return APPLICATION_JAVASCRIPT;
		}

		// Image types
		if (extension.equals("ico")) {
			return IMAGE_ICON;
		}
		if (extension.equals("jpg")) {
			return IMAGE_JPEG;
		}
		if (extension.equals("png")) {
			return IMAGE_PNG;
		}
		if (extension.equals("gif")) {
			return IMAGE_GIF;
		}

		// Video types
		if (extension.equals("mpg")) {
			return VIDEO_MPEG;
		}
		if (extension.equals("mpeg")) {
			return VIDEO_MPEG;
		}
		if (extension.equals("mp4")) {
			return VIDEO_MP4;
		}

		// Unknown
		throw new IllegalArgumentException("Unknown extension: '" + extension + "'");
	}

	private final String type;
	private final boolean text;

	public MimeType(String type, boolean text) {
		this.type = Check.notEmpty("type", type);
		this.text = text;
	}

	public String getType() {
		return type;
	}

	public boolean isText() {
		return text;
	}

	public boolean isBinary() {
		return !text;
	}

	@Override
	public String toString() {
		return type;
	}

}

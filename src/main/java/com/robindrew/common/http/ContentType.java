package com.robindrew.common.http;

import java.nio.charset.Charset;

import com.google.common.base.Charsets;
import com.robindrew.common.util.Check;

public class ContentType {

	/** text/plain; charset=UTF-8 */
	public static final ContentType TEXT_PLAIN = new ContentType(MimeType.TEXT_PLAIN, Charsets.UTF_8);
	/** text/html; charset=UTF-8 */
	public static final ContentType TEXT_HTML = new ContentType(MimeType.TEXT_HTML, Charsets.UTF_8);
	/** application/json; charset=UTF-8 */
	public static final ContentType APPLICATION_JSON = new ContentType(MimeType.APPLICATION_JSON, Charsets.UTF_8);

	/** image/png */
	public static final ContentType IMAGE_PNG = new ContentType(MimeType.IMAGE_PNG);
	/** image/jpeg */
	public static final ContentType IMAGE_JPEG = new ContentType(MimeType.IMAGE_JPEG);

	private final MimeType type;
	private final String charset;
	private final String contentType;

	public ContentType(MimeType type) {
		this(type, "");
	}

	public ContentType(MimeType type, String charset) {
		this.type = Check.notNull("type", type);
		this.charset = Check.notNull("charset", charset);
		this.contentType = charset.isEmpty() ? type.toString() : (type + "; charset=" + charset);
	}

	public ContentType(MimeType type, Charset charset) {
		this(type, charset.name());
	}

	public MimeType getType() {
		return type;
	}

	public String getCharset() {
		return charset;
	}

	@Override
	public String toString() {
		return contentType;
	}

}

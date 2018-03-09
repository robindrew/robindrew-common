package com.robindrew.common.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageInputStream;

/**
 * An Image Format.
 * @see ImageIO
 */
public enum ImageFormat {

	/** The JPEG (Joint Photographic Experts Group) image format. */
	JPG,
	/** The GIF (Graphics Interchange Format) image format. */
	GIF,
	/** The PNG (Portable Network Graphics) image format. */
	PNG,
	/** The BMP (Bitmap) image format. */
	BMP,
	/** The WBMP (Wireless Bitmap) image format. */
	WBMP;

	public boolean isFormat(InputStream input) {
		try (ImageInputStream imageInput = ImageIO.createImageInputStream(input)) {
			Iterator<ImageReader> iter = ImageIO.getImageReaders(imageInput);
			if (!iter.hasNext()) {
				return false;
			}
			ImageReader reader = iter.next();
			return reader.getFormatName().equals(name());
		} catch (IOException ioe) {
			return false;
		}
	}

	/**
	 * Returns the image reader for this format.
	 * @return the image reader for this format.
	 */
	public ImageReader getReader() {
		return ImageIO.getImageReadersByFormatName(name()).next();
	}

	/**
	 * Returns the image writer for this format.
	 * @return the image writer for this format.
	 */
	public ImageWriter getWriter() {
		return ImageIO.getImageWritersByFormatName(name()).next();
	}

	/**
	 * Creates a new image format.
	 */
	private ImageFormat() {
	}
}

package com.robindrew.common.image;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;

import com.robindrew.common.util.Java;
import com.robindrew.common.util.Quietly;

public class ImageOutput implements IImageOutput {

	private static final float DEFAULT_QUALITY = 0.9f;

	private final BufferedImage image;
	private ImageFormat format = ImageFormat.JPG;
	private float quality = DEFAULT_QUALITY;

	public ImageOutput(BufferedImage image) {
		if (image == null) {
			throw new NullPointerException("image");
		}
		this.image = image;
	}

	public ImageOutput(BufferedImage image, ImageFormat format) {
		this(image);
		setFormat(format);
	}

	public ImageOutput(BufferedImage image, ImageFormat format, float quality) {
		this(image);
		setFormat(format);
		setQuality(quality);
	}

	@Override
	public void setFormat(ImageFormat format) {
		if (format == null) {
			throw new NullPointerException("format");
		}
		this.format = format;
	}

	@Override
	public float getQuality() {
		return quality;
	}

	@Override
	public void setQuality(float quality) {
		if (quality < 0.0 || quality > 1.0) {
			throw new IllegalArgumentException("quality=" + quality);
		}
		this.quality = quality;
	}

	@Override
	public BufferedImage getImage() {
		return image;
	}

	@Override
	public ImageFormat getFormat() {
		return format;
	}

	private void writeTo(ImageOutputStream output) throws IOException {
		ImageWriter writer = format.getWriter();
		writer.setOutput(output);

		IIOImage image = new IIOImage(getImage(), null, null);
		ImageWriteParam param = null;

		// Quality is currently only used for JPEG images
		if (ImageFormat.JPG.equals(format)) {
			param = writer.getDefaultWriteParam();
			param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
			param.setCompressionQuality(quality);
		}

		writer.write(null, image, param);
		writer.dispose();
		output.flush();
	}

	@Override
	public void writeToFile(File file) {
		try {
			FileImageOutputStream output = new FileImageOutputStream(file);
			try {
				writeTo(output);
			} finally {
				Quietly.close(output);
			}
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public byte[] writeToByteArray() {
		try {
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			ImageOutputStream output = ImageIO.createImageOutputStream(stream);
			writeTo(output);
			return stream.toByteArray();
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	@Override
	public void writeToStream(OutputStream stream) {
		try {
			ImageOutputStream output = ImageIO.createImageOutputStream(stream);
			writeTo(output);
			output.flush();
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

}

package com.robindrew.common.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.OutputStream;

public interface IImageOutput {

	float getQuality();

	void setQuality(float quality);

	void setFormat(ImageFormat format);

	BufferedImage getImage();

	ImageFormat getFormat();

	void writeToFile(File file);

	byte[] writeToByteArray();

	void writeToStream(OutputStream stream);

}

package com.robindrew.common.image;

import java.awt.Color;
import java.awt.image.BufferedImage;

public interface IImageResizer {

	BufferedImage resizeWithAlpa(BufferedImage image, int width, int height);

	BufferedImage resizeToFit(BufferedImage image, int width, int height);

	void setImageType(int imageType);

	void setBackground(Color background);

}

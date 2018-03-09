package com.robindrew.common.image;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

public class ImageResizer implements IImageResizer {

	private int imageType = BufferedImage.TYPE_INT_ARGB;
	private Color background = null;

	@Override
	public BufferedImage resizeWithAlpa(BufferedImage image, int width, int height) {

		int oldWidth = image.getWidth();
		int oldHeight = image.getHeight();
		int newWidth = oldWidth;
		int newHeight = oldHeight;

		// Resize the image
		Image scaled = image;
		if (newWidth > width || newHeight > height) {

			// Factors
			float widthFactor = (float) oldWidth / (float) width;
			float heightFactor = (float) oldHeight / (float) height;
			float factor = widthFactor > heightFactor ? widthFactor : heightFactor;
			newWidth = (int) (oldWidth / factor);
			newHeight = (int) (oldHeight / factor);

			// Scale
			scaled = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		}

		int x = 0;
		if (width > newWidth) {
			x = (width - newWidth) / 2;
		}

		int y = 0;
		if (height > newHeight) {
			y = (height - newHeight) / 2;
		}

		BufferedImage newImage = new BufferedImage(width, height, imageType);
		Graphics2D graphics = newImage.createGraphics();
		if (background != null) {
			graphics.setBackground(background);
		}
		graphics.drawImage(scaled, x, y, null);
		graphics.dispose();

		return newImage;
	}

	@Override
	public BufferedImage resizeToFit(BufferedImage image, int width, int height) {

		int oldWidth = image.getWidth();
		int oldHeight = image.getHeight();
		int newWidth = oldWidth;
		int newHeight = oldHeight;

		Image scaled = image;
		if (newWidth > width || newHeight > height) {

			// Factors
			float widthFactor = (float) oldWidth / (float) width;
			float heightFactor = (float) oldHeight / (float) height;
			float factor = widthFactor > heightFactor ? widthFactor : heightFactor;
			newWidth = (int) (oldWidth / factor);
			newHeight = (int) (oldHeight / factor);

			// Scale
			scaled = image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
		}

		BufferedImage newImage = new BufferedImage(scaled.getWidth(null), scaled.getHeight(null), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = newImage.createGraphics();
		if (background != null) {
			graphics.setBackground(background);
		}
		graphics.drawImage(scaled, 0, 0, null);
		graphics.dispose();

		return newImage;
	}

	@Override
	public void setImageType(int imageType) {
		this.imageType = imageType;
	}

	@Override
	public void setBackground(Color background) {
		this.background = background;
	}

}

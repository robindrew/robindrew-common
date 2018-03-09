package com.robindrew.common.image;

import static java.awt.RenderingHints.KEY_INTERPOLATION;
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.google.common.io.ByteSource;
import com.robindrew.common.io.Files;
import com.robindrew.common.util.Java;

public class Images {

	public static BufferedImage toBufferedImage(File file) {
		ByteSource source = Files.asByteSource(file);
		try (InputStream input = source.openBufferedStream()) {
			return toBufferedImage(input);
		} catch (Exception e) {
			throw Java.propagate(e);
		}
	}

	public static BufferedImage toBufferedImage(byte[] data) {
		return toBufferedImage(new ByteArrayInputStream(data));
	}

	public static BufferedImage toBufferedImage(InputStream input) {
		try {
			return ImageIO.read(input);
		} catch (IOException e) {
			throw Java.propagate(e);
		}
	}

	/**
	 * Efficient algorithm for scaling an image. Image is scaled to fit the given area, which is then filled to the
	 * desired size with a fill color.
	 * @param image the image to scale.
	 * @param width the width of the scaled image.
	 * @param height the height of the scaled image.
	 * @param fillColor the fill color surrounding the scaled image.
	 * @return the scaled image.
	 */
	public static BufferedImage scaleImageToFit(BufferedImage image, int width, int height, Color fillColor) {

		int oldWidth = image.getWidth();
		int oldHeight = image.getHeight();

		// Image already the correct size?
		if (oldWidth == width && oldHeight == height) {
			return image;
		}

		int newWidth = oldWidth;
		int newHeight = oldHeight;

		// Determine the new image width and height
		boolean resize = false;
		if (newWidth > width || newHeight > height) {
			resize = true;

			// Determine the new width and height
			float widthFactor = (float) oldWidth / (float) width;
			float heightFactor = (float) oldHeight / (float) height;
			if (widthFactor > heightFactor) {
				newWidth = width;
				newHeight = (int) (oldHeight / widthFactor);
			} else {
				newWidth = (int) (oldWidth / heightFactor);
				newHeight = height;
			}
		}

		// Position the image if necessary
		int x = (width > newWidth) ? (width - newWidth) / 2 : 0;
		int y = (height > newHeight) ? (height - newHeight) / 2 : 0;

		// Scale the image (if necessary)
		BufferedImage scaledImage = new BufferedImage(width, height, TYPE_INT_RGB);
		Graphics2D graphics = scaledImage.createGraphics();
		graphics.setBackground(fillColor);
		graphics.fillRect(0, 0, width, height);
		if (resize) {
			graphics.setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BILINEAR);
			graphics.drawImage(image, x, y, newWidth, newHeight, null);
		} else {
			graphics.drawImage(image, x, y, null);
		}
		graphics.dispose();

		return scaledImage;
	}

	/**
	 * Efficient algorithm for scaling an image to fit within the given bounds. Image maintains its aspect ratio.
	 * @param image the image to scale.
	 * @param width the maximum width of the scaled image.
	 * @param height the maximum height of the scaled image.
	 * @return the scaled image (or the original if not scaled).
	 */
	public static BufferedImage scaleImageToFit(BufferedImage image, int width, int height) {

		int oldWidth = image.getWidth();
		int oldHeight = image.getHeight();

		// Determine the new image width and height
		if (oldWidth <= width && oldHeight <= height) {
			return image;
		}

		int newWidth = oldWidth;
		int newHeight = oldHeight;

		// Determine the new width and height
		float widthFactor = (float) oldWidth / (float) width;
		float heightFactor = (float) oldHeight / (float) height;
		if (widthFactor > heightFactor) {
			newWidth = width;
			newHeight = (int) (oldHeight / widthFactor);
		} else {
			newWidth = (int) (oldWidth / heightFactor);
			newHeight = height;
		}

		// Scale the image (if necessary)
		BufferedImage newImage = new BufferedImage(newWidth, newHeight, TYPE_INT_RGB);
		Graphics2D graphics = newImage.createGraphics();
		graphics.setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BILINEAR);
		graphics.drawImage(image, 0, 0, newWidth, newHeight, null);
		graphics.dispose();

		return newImage;
	}

}

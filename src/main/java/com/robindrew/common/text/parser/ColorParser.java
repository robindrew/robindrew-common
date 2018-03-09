package com.robindrew.common.text.parser;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ColorParser implements IStringParser<Color> {

	public static final int TRANSPARENT_ALPHA = 0;
	public static final int OPAQUE_ALPHA = 255;

	public static final Color TRANSPARENT = new Color(0, 0, 0, TRANSPARENT_ALPHA);

	private final Map<String, Color> map = new HashMap<String, Color>();

	public ColorParser() {
		map.put("BLACK", Color.BLACK);
		map.put("BLUE", Color.BLUE);
		map.put("CYAN", Color.CYAN);
		map.put("GRAY", Color.GRAY);
		map.put("GREEN", Color.GREEN);
		map.put("DARK_GRAY", Color.DARK_GRAY);
		map.put("LIGHT_GRAY", Color.LIGHT_GRAY);
		map.put("MAGENTA", Color.MAGENTA);
		map.put("ORANGE", Color.ORANGE);
		map.put("PINK", Color.PINK);
		map.put("RED", Color.RED);
		map.put("WHITE", Color.WHITE);
		map.put("YELLOW", Color.YELLOW);
		map.put("TRANSPARENT", TRANSPARENT);
	}

	@Override
	public Color parse(String color) {
		for (Entry<String, Color> entry : map.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(color)) {
				return entry.getValue();
			}
		}
		if (color.startsWith("#")) {
			color = color.substring(1);
		}
		int red, green, blue, alpha;

		// 6 hex digit rgb (optional 7+8th are alpha)
		if (color.length() >= 6) {
			red = Integer.parseInt(color.substring(0, 2), 16);
			green = Integer.parseInt(color.substring(2, 4), 16);
			blue = Integer.parseInt(color.substring(4, 6), 16);
			alpha = OPAQUE_ALPHA;
			if (color.length() == 8) {
				alpha = Integer.parseInt(color.substring(6, 8), 16);
			}
		}

		// 3 hex digit rgb (optional 4th is alpha)
		else {
			red = Integer.parseInt(color.substring(0, 1) + color.substring(0, 1), 16);
			green = Integer.parseInt(color.substring(1, 2) + color.substring(1, 2), 16);
			blue = Integer.parseInt(color.substring(2, 3) + color.substring(2, 3), 16);
			alpha = OPAQUE_ALPHA;
			if (color.length() == 4) {
				alpha = Integer.parseInt(color.substring(3, 4) + color.substring(3, 4), 16);
			}
		}
		return new Color(red, green, blue, alpha);
	}

}

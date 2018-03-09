package com.robindrew.common.locale;

/**
 * A World Region.
 */
public enum WorldRegion {

	/** Africa. */
	AFRICA("Africa"),
	/** Antarctic Region. */
	ANTARCTIC("Antarctic"),
	/** Arctic Region. */
	ARCTIC("Arctic"),
	/** Asia. */
	ASIA("Asia"),
	/** Central America (and the Carribean). */
	CENTRAL_AMERICA("Central America"),
	/** Europe. */
	EUROPE("Europe"),
	/** Middle East. */
	MIDDLE_EAST("Middle East"),
	/** North America. */
	NORTH_AMERICA("North America"),
	/** Oceania. */
	OCEANIA("Oceania"),
	/** South America. */
	SOUTH_AMERICA("South America"),
	/** Southeast Asia. */
	SOUTHEAST_ASIA("Southeast Asia");

	/** The region name. */
	private final String name;

	/**
	 * Returns the region name.
	 * @return the region name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Creates a new region.
	 * @param name the english region name.
	 */
	WorldRegion(String name) {
		this.name = name;
	}

}

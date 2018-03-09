package com.robindrew.common.lang.bytes;

public enum ByteUnit {

	/** A Byte. */
	BYTES(0, "B"),
	/** A Kilobyte. */
	KILOBYTES(1, "KB"),
	/** A Megabyte. */
	MEGABYTES(2, "MB"),
	/** A Gigabyte. */
	GIGABYTES(3, "GB"),
	/** A Terabyte. */
	TERABYTES(4, "TB"),
	/** A Petabyte. */
	PETABYTES(5, "PB"),
	/** A Exabyte. */
	EXABYTES(6, "EB"),
	/** A Zettabyte. */
	ZETTABYTES(7, "ZB"),
	/** A Yottabyte . */
	YOTTABYTES(8, "YB"),
	/** A Brontobyte. */
	BRONTOBYTES(9, "BB");

	private final int multiplier;
	private final String acronym;

	private ByteUnit(int multiplier, String acronym) {
		this.multiplier = multiplier;
		this.acronym = acronym;
	}

	public int getMultiplier() {
		return multiplier;
	}

	public long get(int bytes) {
		long total = 1;
		for (int i = 0; i < multiplier; i++) {
			total *= bytes;
		}
		return total;
	}

	public long getDecimal() {
		return get(Bytes.DECIMAL_DIVISOR);
	}

	public long getBinary() {
		return get(Bytes.BINARY_DIVISOR);
	}

	public String getAcronym() {
		return acronym;
	}

	public boolean matches(String value) {
		return name().equals(value) || acronym.equals(value);
	}

	public static ByteUnit parseByteUnit(String value) {
		for (ByteUnit unit : values()) {
			if (unit.matches(value)) {
				return unit;
			}
		}
		throw new IllegalArgumentException("value: " + value);
	}

}

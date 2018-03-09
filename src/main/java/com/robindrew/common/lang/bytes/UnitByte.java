package com.robindrew.common.lang.bytes;

public class UnitByte {

	private final long amount;
	private final ByteUnit unit;

	public UnitByte(long amount, ByteUnit unit) {
		if (amount < 0) {
			throw new IllegalArgumentException("amount=" + amount);
		}
		if (unit == null) {
			throw new NullPointerException("unit");
		}
		this.amount = amount;
		this.unit = unit;
	}

	public long getAmount() {
		return amount;
	}

	public ByteUnit getUnit() {
		return unit;
	}

	public long getBinary() {
		return getAmount() * getUnit().getBinary();
	}

	public long getDecimal() {
		return getAmount() * getUnit().getDecimal();
	}

	@Override
	public String toString() {
		return amount + "x" + unit;
	}

}

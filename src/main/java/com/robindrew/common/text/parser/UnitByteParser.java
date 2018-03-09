package com.robindrew.common.text.parser;

import com.robindrew.common.lang.bytes.ByteUnit;
import com.robindrew.common.lang.bytes.UnitByte;

public class UnitByteParser extends ObjectParser<UnitByte> {

	@Override
	protected UnitByte parseObject(String value) {
		value = value.trim();

		int space = value.indexOf(' ');
		if (space == -1) {
			throw new IllegalArgumentException("Badly formatted UnitByte: '" + value + "'");
		}

		// Parse time
		String timePart = value.substring(0, space).trim();
		long time = Long.parseLong(timePart);

		// Parse unit
		String unitPart = value.substring(space + 1).trim();
		unitPart = unitPart.toUpperCase();
		if (unitPart.endsWith("S")) {
			unitPart = unitPart.substring(0, unitPart.length() - 1);
		}
		ByteUnit unit = ByteUnit.parseByteUnit(unitPart);

		return new UnitByte(time, unit);
	}
}

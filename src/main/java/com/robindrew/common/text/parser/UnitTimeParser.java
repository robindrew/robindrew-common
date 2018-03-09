package com.robindrew.common.text.parser;

import java.util.concurrent.TimeUnit;

import com.robindrew.common.date.UnitTime;
import com.robindrew.common.text.StringFormatException;

public class UnitTimeParser extends ObjectParser<UnitTime> {

	@Override
	protected UnitTime parseObject(String value) {
		int comma = value.indexOf(',');

		if (comma == -1) {
			return parseUnitTime(value);
		}

		UnitTime parsed = null;
		for (String part : value.split(",")) {
			if (parsed == null) {
				parsed = parseUnitTime(part);
			} else {
				UnitTime nextUnit = parseUnitTime(part);
				if (nextUnit.getUnit().ordinal() > parsed.getUnit().ordinal()) {
					throw new StringFormatException("badly formatted UnitTime: " + value);
				}

				parsed = UnitTime.add(parsed, nextUnit);
			}
		}
		return parsed;
	}

	private UnitTime parseUnitTime(String value) {
		value = value.trim();

		int space = value.indexOf(' ');
		if (space == -1) {
			throw new IllegalArgumentException("Badly formatted UnitTime: '" + value + "'");
		}

		// Parse time
		String timePart = value.substring(0, space).trim();
		long time = Long.parseLong(timePart);

		// Parse unit
		String unitPart = value.substring(space + 1).trim();
		unitPart = unitPart.toUpperCase();
		if (!unitPart.endsWith("S")) {
			unitPart = unitPart + "S";
		}
		TimeUnit unit = valueOf(unitPart);

		return new UnitTime(time, unit);
	}

	private TimeUnit valueOf(String unitPart) {
		if (unitPart.equals("NANOS")) {
			return TimeUnit.NANOSECONDS;
		}
		if (unitPart.equals("MICROS")) {
			return TimeUnit.MICROSECONDS;
		}
		if (unitPart.equals("MILLIS")) {
			return TimeUnit.MILLISECONDS;
		}
		return TimeUnit.valueOf(unitPart);
	}

}

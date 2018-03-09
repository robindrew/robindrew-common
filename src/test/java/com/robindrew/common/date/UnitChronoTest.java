package com.robindrew.common.date;

import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import org.junit.Assert;
import org.junit.Test;

public class UnitChronoTest {

	@Test
	public void testChronoUnits() {

		testChronoUnit(ChronoUnit.NANOS, TimeUnit.NANOSECONDS);
		testChronoUnit(ChronoUnit.MICROS, TimeUnit.MICROSECONDS);
		testChronoUnit(ChronoUnit.MILLIS, TimeUnit.MILLISECONDS);
		testChronoUnit(ChronoUnit.SECONDS, TimeUnit.SECONDS);
		testChronoUnit(ChronoUnit.MINUTES, TimeUnit.MINUTES);
		testChronoUnit(ChronoUnit.HOURS, TimeUnit.HOURS);
		testChronoUnit(ChronoUnit.DAYS, TimeUnit.DAYS);
	}

	private void testChronoUnit(ChronoUnit chronoUnit, TimeUnit timeUnit) {
		int number = 1;
		for (int i = 0; i < 10; i++) {

			UnitChrono unit1 = new UnitChrono(number, chronoUnit);
			UnitTime unit2 = new UnitTime(number, timeUnit);

			Assert.assertEquals(unit2.toNanos(), unit1.toNanos());
			Assert.assertEquals(unit2.toMillis(), unit1.toMillis());

			number *= 2;
		}
	}
}

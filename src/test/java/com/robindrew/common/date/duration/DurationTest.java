package com.robindrew.common.date.duration;

import org.junit.Assert;
import org.junit.Test;

public class DurationTest {

	@Test
	public void testDurations() {

		long nanos = 1;
		Assert.assertEquals("1 ns", new DurationNanos(nanos).toString());
		nanos *= 10;
		Assert.assertEquals("10 ns", new DurationNanos(nanos).toString());
		nanos *= 10;
		Assert.assertEquals("100 ns", new DurationNanos(nanos).toString());
		nanos *= 10;
		Assert.assertEquals("1.0 μs", new DurationNanos(nanos).toString());
		nanos *= 10;
		Assert.assertEquals("10.0 μs", new DurationNanos(nanos).toString());
		nanos *= 10;
		Assert.assertEquals("100.0 μs", new DurationNanos(nanos).toString());
		nanos *= 10;
		Assert.assertEquals("1.0 ms", new DurationNanos(nanos).toString());
		nanos *= 10;
		Assert.assertEquals("10.0 ms", new DurationNanos(nanos).toString());
		nanos *= 10;
		Assert.assertEquals("100.0 ms", new DurationNanos(nanos).toString());
		nanos *= 10;
		Assert.assertEquals("1.0 s", new DurationNanos(nanos).toString());
		nanos *= 10;
		Assert.assertEquals("10.0 s", new DurationNanos(nanos).toString());
		nanos *= 6;
		Assert.assertEquals("1.0 m", new DurationNanos(nanos).toString());
		nanos *= 10;
		Assert.assertEquals("10.0 m", new DurationNanos(nanos).toString());
		nanos *= 6;
		Assert.assertEquals("1.0 h", new DurationNanos(nanos).toString());
		nanos *= 2;
		Assert.assertEquals("2.0 h", new DurationNanos(nanos).toString());
		nanos *= 12;
		Assert.assertEquals("1.0 d", new DurationNanos(nanos).toString());

	}
}

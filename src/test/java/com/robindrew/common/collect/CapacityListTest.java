package com.robindrew.common.collect;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

import com.robindrew.common.collect.capacity.ArrayCapacityList;
import com.robindrew.common.collect.capacity.ICapacityList;

public class CapacityListTest {

	@Test
	public void testList() {

		ICapacityList<Integer> list = new ArrayCapacityList<>(5);

		list.add(1);
		Assert.assertEquals(1, list.size());

		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		Assert.assertEquals(5, list.size());
		Assert.assertEquals(Arrays.asList(1, 2, 3, 4, 5), list.toList());

		list.add(6);
		Assert.assertEquals(Arrays.asList(2, 3, 4, 5, 6), list.toList());
		list.add(7);
		Assert.assertEquals(Arrays.asList(3, 4, 5, 6, 7), list.toList());
		list.add(8);
		Assert.assertEquals(Arrays.asList(4, 5, 6, 7, 8), list.toList());
		list.add(9);
		Assert.assertEquals(Arrays.asList(5, 6, 7, 8, 9), list.toList());
		list.add(10);
		Assert.assertEquals(Arrays.asList(6, 7, 8, 9, 10), list.toList());
		list.add(11);
		Assert.assertEquals(Arrays.asList(7, 8, 9, 10, 11), list.toList());

		list.add(12);
		list.add(13);
		list.add(14);
		list.add(15);
		list.add(16);
		list.add(17);
		list.add(18);
		list.add(19);
		list.add(20);
		Assert.assertEquals(Arrays.asList(16, 17, 18, 19, 20), list.toList());
		Assert.assertEquals(5, list.size());

		list.clear();
		Assert.assertEquals(0, list.size());
		Assert.assertTrue(list.toList().isEmpty());
	}

}

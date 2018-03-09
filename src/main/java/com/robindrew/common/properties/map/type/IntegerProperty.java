package com.robindrew.common.properties.map.type;

import java.util.Arrays;
import java.util.Collection;

import com.robindrew.common.properties.map.PropertyException;

/**
 * An Integer Property.
 */
public class IntegerProperty extends AbstractProperty<Integer> {

	/** The valid values. */
	private int[] validValues = null;
	/** The invalid values. */
	private int[] invalidValues = null;
	/** The minimum value. */
	private int minimumValue = Integer.MIN_VALUE;
	/** The maximum value. */
	private int maximumValue = Integer.MAX_VALUE;

	public IntegerProperty(String... keys) {
		super(keys);
	}

	public IntegerProperty(Collection<String> keys) {
		super(keys);
	}

	public IntegerProperty min(int minimumValue) {
		this.minimumValue = minimumValue;
		return this;
	}

	public IntegerProperty max(int maximumValue) {
		this.maximumValue = maximumValue;
		return this;
	}

	public IntegerProperty range(int minimumValue, int maximumValue) {
		min(minimumValue);
		max(maximumValue);
		return this;
	}

	public IntegerProperty valid(int... values) {
		this.validValues = values;
		return this;
	}

	public IntegerProperty intvalid(int... values) {
		this.invalidValues = values;
		return this;
	}

	public int getMinimumValue() {
		return minimumValue;
	}

	public int getMaximumValue() {
		return maximumValue;
	}

	@Override
	public IntegerProperty notNull(boolean notNull) {
		super.notNull(notNull);
		return this;
	}

	@Override
	public IntegerProperty defaultValue(Integer value) {
		super.defaultValue(value);
		return this;
	}

	@Override
	protected Integer parseValue(String key, String value) {
		Integer intValue = Integer.parseInt(value);

		// Check Valid Values
		if (validValues != null) {
			for (int validValue : validValues) {
				if (intValue == validValue) {
					return intValue;
				}
			}
			throw new PropertyException(toString(key, value) + " is not a valid value: " + Arrays.toString(validValues));
		}

		// Check Invalid Values
		if (invalidValues != null) {
			for (int invalidValue : invalidValues) {
				if (intValue == invalidValue) {
					throw new PropertyException(toString(key, value) + " is an invalid value: " + Arrays.toString(invalidValues));
				}
			}
		}

		// Check Minimum Value
		if (intValue < minimumValue) {
			throw new PropertyException(toString(key, value) + " less than minimum value: " + minimumValue);
		}

		// Check Maximum Value
		if (intValue > maximumValue) {
			throw new PropertyException(toString(key, value) + " greater than maximum value: " + maximumValue);
		}
		return intValue;
	}

}

package com.robindrew.common.locale;

import static com.robindrew.common.locale.CurrencyCode.AUD;
import static com.robindrew.common.locale.CurrencyCode.CAD;
import static com.robindrew.common.locale.CurrencyCode.CHF;
import static com.robindrew.common.locale.CurrencyCode.EUR;
import static com.robindrew.common.locale.CurrencyCode.GBP;
import static com.robindrew.common.locale.CurrencyCode.JPY;
import static com.robindrew.common.locale.CurrencyCode.USD;

public class CurrencyPair {

	public static final CurrencyPair AUDUSD = getCurrencyPair(AUD, USD);
	public static final CurrencyPair EURUSD = getCurrencyPair(EUR, USD);
	public static final CurrencyPair EURGBP = getCurrencyPair(EUR, GBP);
	public static final CurrencyPair GBPUSD = getCurrencyPair(GBP, USD);
	public static final CurrencyPair USDCAD = getCurrencyPair(USD, CAD);
	public static final CurrencyPair USDCHF = getCurrencyPair(USD, CHF);
	public static final CurrencyPair USDJPY = getCurrencyPair(USD, JPY);
	public static final CurrencyPair EURJPY = getCurrencyPair(EUR, JPY);

	public static CurrencyPair getCurrencyPair(CurrencyCode code1, CurrencyCode code2) {
		return new CurrencyPair(code1, code2);
	}

	public static CurrencyPair valueOf(String pair) {
		int length = pair.length();
		if (length != 6 && length != 7) {
			throw new IllegalArgumentException("invalid currency pair: '" + pair + "'");
		}
		pair = pair.toUpperCase();
		int offset = length == 6 ? 3 : 4;
		CurrencyCode code1 = CurrencyCode.valueOf(pair.substring(0, 3));
		CurrencyCode code2 = CurrencyCode.valueOf(pair.substring(offset, 6));
		return getCurrencyPair(code1, code2);
	}

	private final CurrencyCode code1;
	private final CurrencyCode code2;

	private CurrencyPair(CurrencyCode code1, CurrencyCode code2) {
		if (code1 == null) {
			throw new NullPointerException("code1");
		}
		if (code2 == null) {
			throw new NullPointerException("code2");
		}
		if (code1.equals(code2)) {
			throw new IllegalArgumentException("codes are equal: " + code1);
		}
		this.code1 = code1;
		this.code2 = code2;
	}

	public CurrencyCode getCode1() {
		return code1;
	}

	public CurrencyCode getCode2() {
		return code2;
	}

	public CurrencyPair invert() {
		return new CurrencyPair(code2, code1);
	}

	@Override
	public String toString() {
		return toString("");
	}

	public String toString(String separator) {
		if (separator == null) {
			throw new NullPointerException("separator");
		}
		return code1.name() + separator + code2.name();
	}

	@Override
	public int hashCode() {
		return code1.hashCode() * code2.hashCode() * 1999;
	}

	@Override
	public boolean equals(Object object) {
		if (object == this) {
			return true;
		}
		if (object == null) {
			return false;
		}
		if (!object.getClass().equals(getClass())) {
			return false;
		}
		CurrencyPair compare = (CurrencyPair) object;
		return getCode1().equals(compare.getCode1()) && getCode2().equals(compare.getCode2());
	}

}

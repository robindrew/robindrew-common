package com.robindrew.common.locale;

import java.util.HashMap;
import java.util.Map;

/**
 * A Language.
 */
public enum Language {

	/** Albanian. */
	ALBANIAN("sqi", "sq", "Albanian"),
	/** Arabic. */
	ARABIC("ara", "ar", "Arabic"),
	/** Belarusian. */
	BELARUSIAN("bel", "be", "Belarusian"),
	/** Bulgarian. */
	BULGARIAN("bul", "bg", "Bulgarian"),
	/** Catalan. */
	CATALAN("cat", "ca", "Catalan"),
	/** Chinese. */
	CHINESE("zho", "zh", "Chinese"),
	/** Croatian. */
	CROATIAN("hrv", "hr", "Croatian"),
	/** Czech. */
	CZECH("ces", "cs", "Czech"),
	/** Danish. */
	DANISH("dan", "da", "Danish"),
	/** Dutch. */
	DUTCH("nld", "nl", "Dutch"),
	/** English. */
	ENGLISH("eng", "en", "English"),
	/** Estonian. */
	ESTONIAN("est", "et", "Estonian"),
	/** Finnish. */
	FINNISH("fin", "fi", "Finnish"),
	/** French. */
	FRENCH("fra", "fr", "French"),
	/** German. */
	GERMAN("deu", "de", "German"),
	/** Greek. */
	GREEK("ell", "el", "Greek"),
	/** Hebrew. */
	HEBREW("heb", "iw", "Hebrew"),
	/** Hindi. */
	HINDI("hin", "hi", "Hindi"),
	/** Hungarian. */
	HUNGARIAN("hun", "hu", "Hungarian"),
	/** Icelandic. */
	ICELANDIC("isl", "is", "Icelandic"),
	/** Indonesian. */
	INDONESIAN("ind", "in", "Indonesian"),
	/** Irish. */
	IRISH("gle", "ga", "Irish"),
	/** Italian. */
	ITALIAN("ita", "it", "Italian"),
	/** Japanese. */
	JAPANESE("jpn", "ja", "Japanese"),
	/** Korean. */
	KOREAN("kor", "ko", "Korean"),
	/** Latvian. */
	LATVIAN("lav", "lv", "Latvian"),
	/** Lithuanian. */
	LITHUANIAN("lit", "lt", "Lithuanian"),
	/** Macedonian. */
	MACEDONIAN("mkd", "mk", "Macedonian"),
	/** Malay. */
	MALAY("msa", "ms", "Malay"),
	/** Maltese. */
	MALTESE("mlt", "mt", "Maltese"),
	/** Norwegian. */
	NORWEGIAN("nor", "no", "Norwegian"),
	/** Polish. */
	POLISH("pol", "pl", "Polish"),
	/** Portuguese. */
	PORTUGUESE("por", "pt", "Portuguese"),
	/** Romanian. */
	ROMANIAN("ron", "ro", "Romanian"),
	/** Russian. */
	RUSSIAN("rus", "ru", "Russian"),
	/** Serbian. */
	SERBIAN("srp", "sr", "Serbian"),
	/** Slovak. */
	SLOVAK("slk", "sk", "Slovak"),
	/** Slovenian. */
	SLOVENIAN("slv", "sl", "Slovenian"),
	/** Spanish. */
	SPANISH("spa", "es", "Spanish"),
	/** Swedish. */
	SWEDISH("swe", "sv", "Swedish"),
	/** Thai. */
	THAI("tha", "th", "Thai"),
	/** Turkish. */
	TURKISH("tur", "tr", "Turkish"),
	/** Ukrainian. */
	UKRAINIAN("ukr", "uk", "Ukrainian"),
	/** Vietnamese. */
	VIETNAMESE("vie", "vi", "Vietnamese");

	/** Quick mapping from three-character ISO code to language. */
	private static final Map<String, Language> ISO_THREE_CHAR_MAP = new HashMap<String, Language>(64, 0.5f);
	/** Quick mapping from two-character ISO code to language. */
	private static final Map<String, Language> ISO_TWO_CHAR_MAP = new HashMap<String, Language>(64, 0.5f);
	/** Quick mapping from name to language. */
	private static final Map<String, Language> NAME_MAP = new HashMap<String, Language>(64, 0.5f);

	/**
	 * Initialise the quick maps.
	 */
	static {
		for (Language language : values()) {
			ISO_THREE_CHAR_MAP.put(language.getISOThreeChar(), language);
			ISO_TWO_CHAR_MAP.put(language.getISOTwoChar(), language);
			NAME_MAP.put(language.getName().toLowerCase().intern(), language);
		}
	}

	/**
	 * Returns the language for the given code.
	 * @param code the code.
	 * @return the language.
	 */
	public static Language getLanguage(String code) {
		final String lower = code.toLowerCase();
		Language language = null;
		if (code.length() == 3) {
			language = ISO_THREE_CHAR_MAP.get(lower);
		} else {
			if (code.length() == 2) {
				language = ISO_TWO_CHAR_MAP.get(lower);
			} else {
				language = NAME_MAP.get(lower);
			}
		}
		if (language == null) {
			throw new IllegalArgumentException("unknown language: '" + code + "'");
		}
		return language;
	}

	/** The language name. */
	private final String name;
	/** The ISO two-character code. */
	private final String isoTwoChar;
	/** The ISO three-character code. */
	private final String isoThreeChar;

	/**
	 * Returns the ISO three-character code.
	 * @return the ISO three-character code.
	 */
	public String getISOThreeChar() {
		return isoThreeChar;
	}

	/**
	 * Returns the ISO two-character code.
	 * @return the ISO two-character code.
	 */
	public String getISOTwoChar() {
		return isoTwoChar;
	}

	/**
	 * Returns the name.
	 * @return the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Creates a new language.
	 * @param isoTwoChar the ISO two-character code.
	 * @param isoThreeChar the ISO three-character code.
	 * @param name the english language name.
	 */
	Language(String isoThreeChar, String isoTwoChar, String name) {
		this.isoThreeChar = isoThreeChar;
		this.isoTwoChar = isoTwoChar;
		this.name = name;
	}
}

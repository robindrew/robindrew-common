package com.robindrew.common.locale;

import java.util.HashMap;
import java.util.Map;

/**
 * A Country.
 */
public enum Country {

	/** Aruba. */
	ARUBA(WorldRegion.CENTRAL_AMERICA, "AW", "ABW", "533", "Aruba"),
	/** Antigua and Barbuda. */
	ANTIGUA_AND_BARBUDA(WorldRegion.CENTRAL_AMERICA, "AG", "ATG", "028", "Antigua and Barbuda"),
	/** United Arab Emirates. */
	UNITED_ARAB_EMIRATES(WorldRegion.MIDDLE_EAST, "AE", "ARE", "784", "United Arab Emirates"),
	/** Afghanistan. */
	AFGHANISTAN(WorldRegion.ASIA, "AF", "AFG", "004", "Afghanistan"),
	/** Algeria. */
	ALGERIA(WorldRegion.AFRICA, "DZ", "DZA", "012", "Algeria"),
	/** Azerbaijan. */
	AZERBAIJAN(WorldRegion.ASIA, "AZ", "AZE", "031", "Azerbaijan"),
	/** Albania. */
	ALBANIA(WorldRegion.EUROPE, "AL", "ALB", "008", "Albania"),
	/** Armenia. */
	ARMENIA(WorldRegion.ASIA, "AM", "ARM", "051", "Armenia"),
	/** Andorra. */
	ANDORRA(WorldRegion.EUROPE, "AD", "AND", "020", "Andorra"),
	/** Angola. */
	ANGOLA(WorldRegion.AFRICA, "AO", "AGO", "024", "Angola"),
	/** Argentina. */
	ARGENTINA(WorldRegion.SOUTH_AMERICA, "AR", "ARG", "032", "Argentina"),
	/** Australia. */
	AUSTRALIA(WorldRegion.OCEANIA, "AU", "AUS", "036", "Australia"),
	/** Austria. */
	AUSTRIA(WorldRegion.EUROPE, "AT", "AUT", "040", "Austria"),
	/** Anguilla. */
	ANGUILLA(WorldRegion.CENTRAL_AMERICA, "AI", "AIA", "660", "Anguilla"),
	/** Antarctica. */
	ANTARCTICA(WorldRegion.ANTARCTIC, "AQ", "ATA", "010", "Antarctica"),
	/** Bahrain. */
	BAHRAIN(WorldRegion.MIDDLE_EAST, "BH", "BHR", "048", "Bahrain"),
	/** Barbados. */
	BARBADOS(WorldRegion.CENTRAL_AMERICA, "BB", "BRB", "052", "Barbados"),
	/** Botswana. */
	BOTSWANA(WorldRegion.AFRICA, "BW", "BWA", "072", "Botswana"),
	/** Bermuda. */
	BERMUDA(WorldRegion.NORTH_AMERICA, "BM", "BMU", "060", "Bermuda"),
	/** Belgium. */
	BELGIUM(WorldRegion.EUROPE, "BE", "BEL", "056", "Belgium"),
	/** The Bahamas. */
	THE_BAHAMAS(WorldRegion.CENTRAL_AMERICA, "BS", "BHS", "044", "The Bahamas"),
	/** Bangladesh. */
	BANGLADESH(WorldRegion.ASIA, "BD", "BGD", "050", "Bangladesh"),
	/** Belize. */
	BELIZE(WorldRegion.CENTRAL_AMERICA, "BZ", "BLZ", "084", "Belize"),
	/** Bosnia and Herzegovina. */
	BOSNIA_AND_HERZEGOVINA(WorldRegion.EUROPE, "BA", "BIH", "070", "Bosnia and Herzegovina"),
	/** Bolivia. */
	BOLIVIA(WorldRegion.SOUTH_AMERICA, "BO", "BOL", "068", "Bolivia"),
	/** Myanmar (Burma). */
	MYANMAR(WorldRegion.SOUTHEAST_ASIA, "MM", "MMR", "104", "Myanmar (Burma)"),
	/** Benin. */
	BENIN(WorldRegion.AFRICA, "BJ", "BEN", "204", "Benin"),
	/** Belarus. */
	BELARUS(WorldRegion.EUROPE, "BY", "BLR", "112", "Belarus"),
	/** Solomon Islands. */
	SOLOMON_ISLANDS(WorldRegion.OCEANIA, "SB", "SLB", "090", "Solomon Islands"),
	/** Brazil. */
	BRAZIL(WorldRegion.SOUTH_AMERICA, "BR", "BRA", "076", "Brazil"),
	/** Bhutan. */
	BHUTAN(WorldRegion.ASIA, "BT", "BTN", "064", "Bhutan"),
	/** Bulgaria. */
	BULGARIA(WorldRegion.EUROPE, "BG", "BGR", "100", "Bulgaria"),
	/** Bouvet Island. */
	BOUVET_ISLAND(WorldRegion.ANTARCTIC, "BV", "BVT", "074", "Bouvet Island"),
	/** Brunei. */
	BRUNEI(WorldRegion.SOUTHEAST_ASIA, "BN", "BRN", "096", "Brunei"),
	/** Burundi. */
	BURUNDI(WorldRegion.AFRICA, "BI", "BDI", "108", "Burundi"),
	/** Canada. */
	CANADA(WorldRegion.NORTH_AMERICA, "CA", "CAN", "124", "Canada"),
	/** Cambodia. */
	CAMBODIA(WorldRegion.SOUTHEAST_ASIA, "KH", "KHM", "116", "Cambodia"),
	/** Chad. */
	CHAD(WorldRegion.AFRICA, "TD", "TCD", "148", "Chad"),
	/** Sri Lanka. */
	SRI_LANKA(WorldRegion.ASIA, "LK", "LKA", "144", "Sri Lanka"),
	/** Congo (Brazzaville). */
	CONGO_BRAZZAVILLE(WorldRegion.AFRICA, "CG", "COG", "178", "Congo (Brazzaville)"),
	/** Congo (Kinshasa). */
	CONGO_KINSHASA(WorldRegion.AFRICA, "CD", "COD", "180", "Congo (Kinshasa)"),
	/** China. */
	CHINA(WorldRegion.ASIA, "CN", "CHN", "156", "China"),
	/** Chile. */
	CHILE(WorldRegion.SOUTH_AMERICA, "CL", "CHL", "152", "Chile"),
	/** Cayman Islands. */
	CAYMAN_ISLANDS(WorldRegion.CENTRAL_AMERICA, "KY", "CYM", "136", "Cayman Islands"),
	/** Cocos (Keeling) Islands. */
	COCOS_ISLANDS(WorldRegion.SOUTHEAST_ASIA, "CC", "CCK", "166", "Cocos (Keeling) Islands"),
	/** Cameroon. */
	CAMEROON(WorldRegion.AFRICA, "CM", "CMR", "120", "Cameroon"),
	/** Comoros. */
	COMOROS(WorldRegion.AFRICA, "KM", "COM", "174", "Comoros"),
	/** Colombia. */
	COLOMBIA(WorldRegion.SOUTH_AMERICA, "CO", "COL", "170", "Colombia"),
	/** Costa Rica. */
	COSTA_RICA(WorldRegion.CENTRAL_AMERICA, "CR", "CRI", "188", "Costa Rica"),
	/** Central African Republic. */
	CENTRAL_AFRICAN_REPUBLIC(WorldRegion.AFRICA, "CF", "CAF", "140", "Central African Republic"),
	/** Cuba. */
	CUBA(WorldRegion.CENTRAL_AMERICA, "CU", "CUB", "192", "Cuba"),
	/** Cape Verde. */
	CAPE_VERDE(WorldRegion.AFRICA, "CV", "CPV", "132", "Cape Verde"),
	/** Cook Islands. */
	COOK_ISLANDS(WorldRegion.OCEANIA, "CK", "COK", "184", "Cook Islands"),
	/** Cyprus. */
	CYPRUS(WorldRegion.MIDDLE_EAST, "CY", "CYP", "196", "Cyprus"),
	/** Denmark. */
	DENMARK(WorldRegion.EUROPE, "DK", "DNK", "208", "Denmark"),
	/** Djibouti. */
	DJIBOUTI(WorldRegion.AFRICA, "DJ", "DJI", "262", "Djibouti"),
	/** Dominica. */
	DOMINICA(WorldRegion.CENTRAL_AMERICA, "DM", "DMA", "212", "Dominica"),
	/** Dominican Republic. */
	DOMINICAN_REPUBLIC(WorldRegion.CENTRAL_AMERICA, "DO", "DOM", "214", "Dominican Republic"),
	/** Ecuador. */
	ECUADOR(WorldRegion.SOUTH_AMERICA, "EC", "ECU", "218", "Ecuador"),
	/** Egypt. */
	EGYPT(WorldRegion.AFRICA, "EG", "EGY", "818", "Egypt"),
	/** Ireland. */
	IRELAND(WorldRegion.EUROPE, "IE", "IRL", "372", "Ireland"),
	/** Equatorial Guinea. */
	EQUATORIAL_GUINEA(WorldRegion.AFRICA, "GQ", "GNQ", "226", "Equatorial Guinea"),
	/** Estonia. */
	ESTONIA(WorldRegion.EUROPE, "EE", "EST", "233", "Estonia"),
	/** Eritrea. */
	ERITREA(WorldRegion.AFRICA, "ER", "ERI", "232", "Eritrea"),
	/** El Salvador. */
	EL_SALVADOR(WorldRegion.CENTRAL_AMERICA, "SV", "SLV", "222", "El Salvador"),
	/** Ethiopia. */
	ETHIOPIA(WorldRegion.AFRICA, "ET", "ETH", "231", "Ethiopia"),
	/** Czech Republic. */
	CZECH_REPUBLIC(WorldRegion.EUROPE, "CZ", "CZE", "203", "Czech Republic"),
	/** French Guiana. */
	FRENCH_GUIANA(WorldRegion.SOUTH_AMERICA, "GF", "GUF", "254", "French Guiana"),
	/** Finland. */
	FINLAND(WorldRegion.EUROPE, "FI", "FIN", "246", "Finland"),
	/** Fiji. */
	FIJI(WorldRegion.OCEANIA, "FJ", "FJI", "242", "Fiji"),
	/** Falkland Islands (Islas Malvinas). */
	FALKLAND_ISLANDS(WorldRegion.SOUTH_AMERICA, "FK", "FLK", "238", "Falkland Islands (Islas Malvinas)"),
	/** Faroe Islands. */
	FAROE_ISLANDS(WorldRegion.EUROPE, "FO", "FRO", "234", "Faroe Islands"),
	/** French Polynesia. */
	FRENCH_POLYNESIA(WorldRegion.OCEANIA, "PF", "PYF", "258", "French Polynesia"),
	/** France. */
	FRANCE(WorldRegion.EUROPE, "FR", "FRA", "250", "France"),
	/** French Southern and Antarctic Lands. */
	FRENCH_SOUTHERN_AND_ANTARCTIC_LANDS(WorldRegion.ANTARCTIC, "TF", "ATF", "260", "French Southern and Antarctic Lands"),
	/** The Gambia. */
	THE_GAMBIA(WorldRegion.AFRICA, "GM", "GMB", "270", "The Gambia"),
	/** Gabon. */
	GABON(WorldRegion.AFRICA, "GA", "GAB", "266", "Gabon"),
	/** Georgia. */
	GEORGIA(WorldRegion.ASIA, "GE", "GEO", "268", "Georgia"),
	/** Ghana. */
	GHANA(WorldRegion.AFRICA, "GH", "GHA", "288", "Ghana"),
	/** Gibraltar. */
	GIBRALTAR(WorldRegion.EUROPE, "GI", "GIB", "292", "Gibraltar"),
	/** Grenada. */
	GRENADA(WorldRegion.CENTRAL_AMERICA, "GD", "GRD", "308", "Grenada"),
	/** Greenland. */
	GREENLAND(WorldRegion.ARCTIC, "GL", "GRL", "304", "Greenland"),
	/** Germany. */
	GERMANY(WorldRegion.EUROPE, "DE", "DEU", "276", "Germany"),
	/** Guadeloupe. */
	GUADELOUPE(WorldRegion.CENTRAL_AMERICA, "GP", "GLP", "312", "Guadeloupe"),
	/** Greece. */
	GREECE(WorldRegion.EUROPE, "GR", "GRC", "300", "Greece"),
	/** Guatemala. */
	GUATEMALA(WorldRegion.CENTRAL_AMERICA, "GT", "GTM", "320", "Guatemala"),
	/** Guinea. */
	GUINEA(WorldRegion.AFRICA, "GN", "GIN", "324", "Guinea"),
	/** Guyana. */
	GUYANA(WorldRegion.SOUTH_AMERICA, "GY", "GUY", "328", "Guyana"),
	/** Haiti. */
	HAITI(WorldRegion.CENTRAL_AMERICA, "HT", "HTI", "332", "Haiti"),
	/** Hong Kong. */
	HONG_KONG(WorldRegion.SOUTHEAST_ASIA, "HK", "HKG", "344", "Hong Kong"),
	/** Heard Island and McDonald Islands. */
	HEARD_ISLAND_AND_MCDONALD_ISLANDS(WorldRegion.ANTARCTIC, "HM", "HMD", "334", "Heard Island and McDonald Islands"),
	/** Honduras. */
	HONDURAS(WorldRegion.CENTRAL_AMERICA, "HN", "HND", "340", "Honduras"),
	/** Croatia. */
	CROATIA(WorldRegion.EUROPE, "HR", "HRV", "191", "Croatia"),
	/** Hungary. */
	HUNGARY(WorldRegion.EUROPE, "HU", "HUN", "348", "Hungary"),
	/** Iceland. */
	ICELAND(WorldRegion.ARCTIC, "IS", "ISL", "352", "Iceland"),
	/** Indonesia. */
	INDONESIA(WorldRegion.SOUTHEAST_ASIA, "ID", "IDN", "360", "Indonesia"),
	/** India. */
	INDIA(WorldRegion.ASIA, "IN", "IND", "356", "India"),
	/** British Indian Ocean Territory. */
	BRITISH_INDIAN_OCEAN_TERRITORY(WorldRegion.SOUTHEAST_ASIA, "IO", "IOT", "086", "British Indian Ocean Territory"),
	/** Iran. */
	IRAN(WorldRegion.MIDDLE_EAST, "IR", "IRN", "364", "Iran"),
	/** Israel. */
	ISRAEL(WorldRegion.MIDDLE_EAST, "IL", "ISR", "376", "Israel"),
	/** Italy. */
	ITALY(WorldRegion.EUROPE, "IT", "ITA", "380", "Italy"),
	/** Cote D'Ivoire. */
	COTE_DIVOIRE(WorldRegion.AFRICA, "CI", "CIV", "384", "Cote D'Ivoire"),
	/** Iraq. */
	IRAQ(WorldRegion.MIDDLE_EAST, "IQ", "IRQ", "368", "Iraq"),
	/** Japan. */
	JAPAN(WorldRegion.ASIA, "JP", "JPN", "392", "Japan"),
	/** Jamaica. */
	JAMAICA(WorldRegion.CENTRAL_AMERICA, "JM", "JAM", "388", "Jamaica"),
	/** Jan Mayen. */
	JAN_MAYEN(WorldRegion.ARCTIC, "SJ", "SJM", "744", "Jan Mayen"),
	/** Jordan. */
	JORDAN(WorldRegion.MIDDLE_EAST, "JO", "JOR", "400", "Jordan"),
	/** Kenya. */
	KENYA(WorldRegion.AFRICA, "KE", "KEN", "404", "Kenya"),
	/** Kyrgyzstan. */
	KYRGYZSTAN(WorldRegion.ASIA, "KG", "KGZ", "417", "Kyrgyzstan"),
	/** North Korea. */
	NORTH_KOREA(WorldRegion.ASIA, "KP", "PRK", "408", "North Korea"),
	/** Kiribati. */
	KIRIBATI(WorldRegion.OCEANIA, "KI", "KIR", "296", "Kiribati"),
	/** South Korea. */
	SOUTH_KOREA(WorldRegion.ASIA, "KR", "KOR", "410", "South Korea"),
	/** Christmas Island. */
	CHRISTMAS_ISLAND(WorldRegion.SOUTHEAST_ASIA, "CX", "CXR", "162", "Christmas Island"),
	/** Kuwait. */
	KUWAIT(WorldRegion.MIDDLE_EAST, "KW", "KWT", "414", "Kuwait"),
	/** Kazakhstan. */
	KAZAKHSTAN(WorldRegion.ASIA, "KZ", "KAZ", "398", "Kazakhstan"),
	/** Laos. */
	LAOS(WorldRegion.SOUTHEAST_ASIA, "LA", "LAO", "418", "Laos"),
	/** Lebanon. */
	LEBANON(WorldRegion.MIDDLE_EAST, "LB", "LBN", "422", "Lebanon"),
	/** Latvia. */
	LATVIA(WorldRegion.EUROPE, "LV", "LVA", "428", "Latvia"),
	/** Lithuania. */
	LITHUANIA(WorldRegion.EUROPE, "LT", "LTU", "440", "Lithuania"),
	/** Liberia. */
	LIBERIA(WorldRegion.AFRICA, "LR", "LBR", "430", "Liberia"),
	/** Slovakia. */
	SLOVAKIA(WorldRegion.EUROPE, "SK", "SVK", "703", "Slovakia"),
	/** Liechtenstein. */
	LIECHTENSTEIN(WorldRegion.EUROPE, "LI", "LIE", "438", "Liechtenstein"),
	/** Lesotho. */
	LESOTHO(WorldRegion.AFRICA, "LS", "LSO", "426", "Lesotho"),
	/** Luxembourg. */
	LUXEMBOURG(WorldRegion.EUROPE, "LU", "LUX", "442", "Luxembourg"),
	/** Libya. */
	LIBYA(WorldRegion.AFRICA, "LY", "LBY", "434", "Libya"),
	/** Madagascar. */
	MADAGASCAR(WorldRegion.AFRICA, "MG", "MDG", "450", "Madagascar"),
	/** Martinique. */
	MARTINIQUE(WorldRegion.CENTRAL_AMERICA, "MQ", "MTQ", "474", "Martinique"),
	/** Macau. */
	MACAU(WorldRegion.SOUTHEAST_ASIA, "MO", "MAC", "446", "Macau"),
	/** Moldova. */
	MOLDOVA(WorldRegion.EUROPE, "MD", "MDA", "498", "Moldova"),
	/** Mayotte. */
	MAYOTTE(WorldRegion.AFRICA, "YT", "MYT", "175", "Mayotte"),
	/** Mongolia. */
	MONGOLIA(WorldRegion.ASIA, "MN", "MNG", "496", "Mongolia"),
	/** Montserrat. */
	MONTSERRAT(WorldRegion.CENTRAL_AMERICA, "MS", "MSR", "500", "Montserrat"),
	/** Malawi. */
	MALAWI(WorldRegion.AFRICA, "MW", "MWI", "454", "Malawi"),
	/** Macedonia. */
	MACEDONIA(WorldRegion.EUROPE, "MK", "MKD", "807", "Macedonia"),
	/** Mali. */
	MALI(WorldRegion.AFRICA, "ML", "MLI", "466", "Mali"),
	/** Monaco. */
	MONACO(WorldRegion.EUROPE, "MC", "MCO", "492", "Monaco"),
	/** Morocco. */
	MOROCCO(WorldRegion.AFRICA, "MA", "MAR", "504", "Morocco"),
	/** Mauritius. */
	MAURITIUS(WorldRegion.AFRICA, "MU", "MUS", "480", "Mauritius"),
	/** Mauritania. */
	MAURITANIA(WorldRegion.AFRICA, "MR", "MRT", "478", "Mauritania"),
	/** Malta. */
	MALTA(WorldRegion.EUROPE, "MT", "MLT", "470", "Malta"),
	/** Oman. */
	OMAN(WorldRegion.MIDDLE_EAST, "OM", "OMN", "512", "Oman"),
	/** Maldives. */
	MALDIVES(WorldRegion.ASIA, "MV", "MDV", "462", "Maldives"),
	/** Mexico. */
	MEXICO(WorldRegion.NORTH_AMERICA, "MX", "MEX", "484", "Mexico"),
	/** Malaysia. */
	MALAYSIA(WorldRegion.SOUTHEAST_ASIA, "MY", "MYS", "458", "Malaysia"),
	/** Mozambique. */
	MOZAMBIQUE(WorldRegion.AFRICA, "MZ", "MOZ", "508", "Mozambique"),
	/** New Caledonia. */
	NEW_CALEDONIA(WorldRegion.OCEANIA, "NC", "NCL", "540", "New Caledonia"),
	/** Niue. */
	NIUE(WorldRegion.OCEANIA, "NU", "NIU", "570", "Niue"),
	/** Norfolk Island. */
	NORFOLK_ISLAND(WorldRegion.OCEANIA, "NF", "NFK", "574", "Norfolk Island"),
	/** Niger. */
	NIGER(WorldRegion.AFRICA, "NE", "NER", "562", "Niger"),
	/** Vanuatu. */
	VANUATU(WorldRegion.OCEANIA, "VU", "VUT", "548", "Vanuatu"),
	/** Nigeria. */
	NIGERIA(WorldRegion.AFRICA, "NG", "NGA", "566", "Nigeria"),
	/** Netherlands. */
	NETHERLANDS(WorldRegion.EUROPE, "NL", "NLD", "528", "Netherlands"),
	/** Norway. */
	NORWAY(WorldRegion.EUROPE, "NO", "NOR", "578", "Norway"),
	/** Nepal. */
	NEPAL(WorldRegion.ASIA, "NP", "NPL", "524", "Nepal"),
	/** Nauru. */
	NAURU(WorldRegion.OCEANIA, "NR", "NRU", "520", "Nauru"),
	/** Suriname. */
	SURINAME(WorldRegion.SOUTH_AMERICA, "SR", "SUR", "740", "Suriname"),
	/** Netherlands Antilles. */
	NETHERLANDS_ANTILLES(WorldRegion.CENTRAL_AMERICA, "AN", "ANT", "530", "Netherlands Antilles"),
	/** Nicaragua. */
	NICARAGUA(WorldRegion.CENTRAL_AMERICA, "NI", "NIC", "558", "Nicaragua"),
	/** New Zealand. */
	NEW_ZEALAND(WorldRegion.OCEANIA, "NZ", "NZL", "554", "New Zealand"),
	/** Paraguay. */
	PARAGUAY(WorldRegion.SOUTH_AMERICA, "PY", "PRY", "600", "Paraguay"),
	/** Pitcairn Islands. */
	PITCAIRN_ISLANDS(WorldRegion.OCEANIA, "PN", "PCN", "612", "Pitcairn Islands"),
	/** Peru. */
	PERU(WorldRegion.SOUTH_AMERICA, "PE", "PER", "604", "Peru"),
	/** Pakistan. */
	PAKISTAN(WorldRegion.ASIA, "PK", "PAK", "586", "Pakistan"),
	/** Poland. */
	POLAND(WorldRegion.EUROPE, "PL", "POL", "616", "Poland"),
	/** Panama. */
	PANAMA(WorldRegion.CENTRAL_AMERICA, "PA", "PAN", "591", "Panama"),
	/** Portugal. */
	PORTUGAL(WorldRegion.EUROPE, "PT", "PRT", "620", "Portugal"),
	/** Papua New Guinea. */
	PAPUA_NEW_GUINEA(WorldRegion.OCEANIA, "PG", "PNG", "598", "Papua New Guinea"),
	/** Guinea-Bissau. */
	GUINEA_BISSAU(WorldRegion.AFRICA, "GW", "GNB", "624", "Guinea-Bissau"),
	/** Qatar. */
	QATAR(WorldRegion.MIDDLE_EAST, "QA", "QAT", "634", "Qatar"),
	/** Reunion. */
	REUNION(WorldRegion.AFRICA, "RE", "REU", "638", "Reunion"),
	/** Romania. */
	ROMANIA(WorldRegion.EUROPE, "RO", "ROU", "642", "Romania"),
	/** Philippines. */
	PHILIPPINES(WorldRegion.SOUTHEAST_ASIA, "PH", "PHL", "608", "Philippines"),
	/** Russia. */
	RUSSIA(WorldRegion.ASIA, "RU", "RUS", "643", "Russia"),
	/** Rwanda. */
	RWANDA(WorldRegion.AFRICA, "RW", "RWA", "646", "Rwanda"),
	/** Saudi Arabia. */
	SAUDI_ARABIA(WorldRegion.MIDDLE_EAST, "SA", "SAU", "682", "Saudi Arabia"),
	/** Saint Pierre and Miquelon. */
	SAINT_PIERRE_AND_MIQUELON(WorldRegion.NORTH_AMERICA, "PM", "SPM", "666", "Saint Pierre and Miquelon"),
	/** Saint Kitts and Nevis. */
	SAINT_KITTS_AND_NEVIS(WorldRegion.CENTRAL_AMERICA, "KN", "KNA", "659", "Saint Kitts and Nevis"),
	/** Seychelles. */
	SEYCHELLES(WorldRegion.AFRICA, "SC", "SYC", "690", "Seychelles"),
	/** South Africa. */
	SOUTH_AFRICA(WorldRegion.AFRICA, "ZA", "ZAF", "710", "South Africa"),
	/** Senegal. */
	SENEGAL(WorldRegion.AFRICA, "SN", "SEN", "686", "Senegal"),
	/** Saint Helena. */
	SAINT_HELENA(WorldRegion.AFRICA, "SH", "SHN", "654", "Saint Helena"),
	/** Slovenia. */
	SLOVENIA(WorldRegion.EUROPE, "SI", "SVN", "705", "Slovenia"),
	/** Sierra Leone. */
	SIERRA_LEONE(WorldRegion.AFRICA, "SL", "SLE", "694", "Sierra Leone"),
	/** San Marino. */
	SAN_MARINO(WorldRegion.EUROPE, "SM", "SMR", "674", "San Marino"),
	/** Singapore. */
	SINGAPORE(WorldRegion.SOUTHEAST_ASIA, "SG", "SGP", "702", "Singapore"),
	/** Somalia. */
	SOMALIA(WorldRegion.AFRICA, "SO", "SOM", "706", "Somalia"),
	/** Spain. */
	SPAIN(WorldRegion.EUROPE, "ES", "ESP", "724", "Spain"),
	/** Saint Lucia. */
	SAINT_LUCIA(WorldRegion.CENTRAL_AMERICA, "LC", "LCA", "662", "Saint Lucia"),
	/** Sudan. */
	SUDAN(WorldRegion.AFRICA, "SD", "SDN", "736", "Sudan"),
	/** Svalbard. */
	SVALBARD(WorldRegion.ARCTIC, "SJ", "SJM", "744", "Svalbard"),
	/** Sweden. */
	SWEDEN(WorldRegion.EUROPE, "SE", "SWE", "752", "Sweden"),
	/** South Georgia and the South Sandwich Islands. */
	SOUTH_GEORGIA_AND_THE_SOUTH_SANDWICH_ISLANDS(WorldRegion.ANTARCTIC, "GS", "SGS", "239", "South Georgia and the South Sandwich Islands"),
	/** Syria. */
	SYRIA(WorldRegion.MIDDLE_EAST, "SY", "SYR", "760", "Syria"),
	/** Switzerland. */
	SWITZERLAND(WorldRegion.EUROPE, "CH", "CHE", "756", "Switzerland"),
	/** Trinidad and Tobago. */
	TRINIDAD_AND_TOBAGO(WorldRegion.CENTRAL_AMERICA, "TT", "TTO", "780", "Trinidad and Tobago"),
	/** Thailand. */
	THAILAND(WorldRegion.SOUTHEAST_ASIA, "TH", "THA", "764", "Thailand"),
	/** Tajikistan. */
	TAJIKISTAN(WorldRegion.ASIA, "TJ", "TJK", "762", "Tajikistan"),
	/** Turks and Caicos Islands. */
	TURKS_AND_CAICOS_ISLANDS(WorldRegion.CENTRAL_AMERICA, "TC", "TCA", "796", "Turks and Caicos Islands"),
	/** Tokelau. */
	TOKELAU(WorldRegion.OCEANIA, "TK", "TKL", "772", "Tokelau"),
	/** Tonga. */
	TONGA(WorldRegion.OCEANIA, "TO", "TON", "776", "Tonga"),
	/** Togo. */
	TOGO(WorldRegion.AFRICA, "TG", "TGO", "768", "Togo"),
	/** Sao Tome and Principe. */
	SAO_TOME_AND_PRINCIPE(WorldRegion.AFRICA, "ST", "STP", "678", "Sao Tome and Principe"),
	/** Tunisia. */
	TUNISIA(WorldRegion.AFRICA, "TN", "TUN", "788", "Tunisia"),
	/** East Timor. */
	EAST_TIMOR(WorldRegion.SOUTHEAST_ASIA, "TL", "TLS", "626", "East Timor"),
	/** Turkey. */
	TURKEY(WorldRegion.MIDDLE_EAST, "TR", "TUR", "792", "Turkey"),
	/** Tuvalu. */
	TUVALU(WorldRegion.OCEANIA, "TV", "TUV", "798", "Tuvalu"),
	/** Taiwan. */
	TAIWAN(WorldRegion.SOUTHEAST_ASIA, "TW", "TWN", "158", "Taiwan"),
	/** Turkmenistan. */
	TURKMENISTAN(WorldRegion.ASIA, "TM", "TKM", "795", "Turkmenistan"),
	/** Tanzania. */
	TANZANIA(WorldRegion.AFRICA, "TZ", "TZA", "834", "Tanzania"),
	/** Uganda. */
	UGANDA(WorldRegion.AFRICA, "UG", "UGA", "800", "Uganda"),
	/** United Kingdom. */
	UNITED_KINGDOM(WorldRegion.EUROPE, "GB", "GBR", "826", "United Kingdom"),
	/** Ukraine. */
	UKRAINE(WorldRegion.EUROPE, "UA", "UKR", "804", "Ukraine"),
	/** United States. */
	UNITED_STATES(WorldRegion.NORTH_AMERICA, "US", "USA", "840", "United States"),
	/** Burkina Faso. */
	BURKINA_FASO(WorldRegion.AFRICA, "BF", "BFA", "854", "Burkina Faso"),
	/** Uruguay. */
	URUGUAY(WorldRegion.SOUTH_AMERICA, "UY", "URY", "858", "Uruguay"),
	/** Uzbekistan. */
	UZBEKISTAN(WorldRegion.ASIA, "UZ", "UZB", "860", "Uzbekistan"),
	/** Saint Vincent and the Grenadines. */
	SAINT_VINCENT_AND_THE_GRENADINES(WorldRegion.CENTRAL_AMERICA, "VC", "VCT", "670", "Saint Vincent and the Grenadines"),
	/** Venezuela. */
	VENEZUELA(WorldRegion.SOUTH_AMERICA, "VE", "VEN", "862", "Venezuela"),
	/** British Virgin Islands. */
	BRITISH_VIRGIN_ISLANDS(WorldRegion.CENTRAL_AMERICA, "VG", "VGB", "092", "British Virgin Islands"),
	/** Vietnam. */
	VIETNAM(WorldRegion.SOUTHEAST_ASIA, "VN", "VNM", "704", "Vietnam"),
	/** Holy See (Vatican City). */
	VATICAN_CITY(WorldRegion.EUROPE, "VA", "VAT", "336", "Holy See (Vatican City)"),
	/** Namibia. */
	NAMIBIA(WorldRegion.AFRICA, "NA", "NAM", "516", "Namibia"),
	/** Wallis and Futuna. */
	WALLIS_AND_FUTUNA(WorldRegion.OCEANIA, "WF", "WLF", "876", "Wallis and Futuna"),
	/** Western Sahara. */
	WESTERN_SAHARA(WorldRegion.AFRICA, "EH", "ESH", "732", "Western Sahara"),
	/** Samoa. */
	SAMOA(WorldRegion.OCEANIA, "WS", "WSM", "882", "Samoa"),
	/** Swaziland. */
	SWAZILAND(WorldRegion.AFRICA, "SZ", "SWZ", "748", "Swaziland"),
	/** Serbia and Montenegro. */
	SERBIA_AND_MONTENEGRO(WorldRegion.EUROPE, "CS", "SCG", "891", "Serbia and Montenegro"),
	/** Yemen. */
	YEMEN(WorldRegion.MIDDLE_EAST, "YE", "YEM", "887", "Yemen"),
	/** Zambia. */
	ZAMBIA(WorldRegion.AFRICA, "ZM", "ZMB", "894", "Zambia"),
	/** Zimbabwe. */
	ZIMBABWE(WorldRegion.AFRICA, "ZW", "ZWE", "716", "Zimbabwe");

	/** Quick mapping from three-character ISO code to country. */
	private static final Map<Integer, Country> ISO_NUMERIC_MAP = new HashMap<Integer, Country>(128, 0.5f);
	/** Quick mapping from three-character ISO code to country. */
	private static final Map<String, Country> ISO_THREE_DIGIT_MAP = new HashMap<String, Country>(128, 0.5f);
	/** Quick mapping from three-character ISO code to country. */
	private static final Map<String, Country> ISO_THREE_CHAR_MAP = new HashMap<String, Country>(128, 0.5f);
	/** Quick mapping from two-character ISO code to country. */
	private static final Map<String, Country> ISO_TWO_CHAR_MAP = new HashMap<String, Country>(128, 0.5f);
	/** Quick mapping from name to country. */
	private static final Map<String, Country> NAME_MAP = new HashMap<String, Country>();

	/**
	 * Initialise the quick maps.
	 */
	static {
		for (Country country : values()) {
			ISO_NUMERIC_MAP.put(country.getISONumeric(), country);
			ISO_THREE_DIGIT_MAP.put(country.getISOThreeDigit(), country);
			ISO_THREE_CHAR_MAP.put(country.getISOThreeChar(), country);
			ISO_TWO_CHAR_MAP.put(country.getISOTwoChar(), country);
			NAME_MAP.put(country.getName().toLowerCase().intern(), country);
		}
	}

	/**
	 * Returns the country for the given code.
	 * @param code the code.
	 * @return the country.
	 */
	public static Country getCountry(int code) {
		Country country = ISO_NUMERIC_MAP.get(code);
		if (country == null) {
			throw new IllegalArgumentException("unknown country: " + code);
		}
		return country;
	}

	/**
	 * Returns the country for the given code.
	 * @param code the code.
	 * @return the country.
	 */
	public static Country getCountry(String code) {
		final String lower = code.toLowerCase();
		Country country = null;
		if (code.length() == 3) {
			country = ISO_THREE_CHAR_MAP.get(lower);
			if (country == null) {
				country = ISO_THREE_DIGIT_MAP.get(lower);
			}
		} else {
			if (code.length() == 2) {
				country = ISO_TWO_CHAR_MAP.get(lower);
			} else {
				country = NAME_MAP.get(lower);
			}
		}
		if (country == null) {
			throw new IllegalArgumentException("unknown country: '" + code + "'");
		}
		return country;
	}

	/** The country name. */
	private final String name;
	/** The world region. */
	private final WorldRegion region;
	/** The ISO two-character code. */
	private final String isoTwoChar;
	/** The ISO three-character code. */
	private final String isoThreeChar;
	/** The ISO three-digit code. */
	private final String isoThreeDigit;
	/** The ISO three-digit code as an integer. */
	private final int isoNumeric;

	/**
	 * Returns the ISO three-digit code.
	 * @return the ISO three-digit code.
	 */
	public String getISOThreeDigit() {
		return isoThreeDigit;
	}

	/**
	 * Returns the ISO three-digit code as an integer.
	 * @return the ISO three-digit code as an integer.
	 */
	public int getISONumeric() {
		return isoNumeric;
	}

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
	 * Returns the region.
	 * @return the region.
	 */
	public WorldRegion getRegion() {
		return region;
	}

	/**
	 * Creates a new country.
	 * @param region the region.
	 * @param isoTwoChar the ISO two-character code.
	 * @param isoThreeChar the ISO three-character code.
	 * @param isoThreeDigit the ISO three-digit code.
	 * @param name the english country name.
	 */
	Country(WorldRegion region, String isoTwoChar, String isoThreeChar, String isoThreeDigit, String name) {
		this.region = region;
		this.isoTwoChar = isoTwoChar.toLowerCase().intern();
		this.isoThreeChar = isoThreeChar.toLowerCase().intern();
		this.isoThreeDigit = isoThreeDigit.toLowerCase().intern();
		this.isoNumeric = Integer.parseInt(isoThreeDigit);
		this.name = name;
	}

}

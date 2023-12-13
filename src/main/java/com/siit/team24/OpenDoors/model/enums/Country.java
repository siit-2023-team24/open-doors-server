package com.siit.team24.OpenDoors.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum Country {
    AFGHANISTAN("Afghanistan"),
    ALBANIA("Albania"),
    ALGERIA("Algeria"),
    ANDORRA("Andorra"),
    ANGOLA("Angola"),
    ARGENTINA("Argentina"),
    ARMENIA("Armenia"),
    AUSTRALIA("Australia"),
    AUSTRIA("Austria"),
    AZERBAIJAN("Azerbaijan"),
    BAHAMAS("Bahamas"),
    BAHRAIN("Bahrain"),
    BANGLADESH("Bangladesh"),
    BARBADOS("Barbados"),
    BELARUS("Belarus"),
    BELGIUM("Belgium"),
    BOSNIA_AND_HERZEGOVINA("Bosnia and Herzegovina"),
    BRAZIL("Brazil"),
    BULGARIA("Bulgaria"),
    CANADA("Canada"),
    CHINA("China"),
    COLOMBIA("Colombia"),
    CROATIA("Croatia"),
    CYPRUS("Cyprus"),
    CZECH_REPUBLIC("Czech Republic"),
    DENMARK("Denmark"),
    EGYPT("Egypt"),
    ESTONIA("Estonia"),
    FINLAND("Finland"),
    FRANCE("France"),
    GEORGIA("Georgia"),
    GERMANY("Germany"),
    GREECE("Greece"),
    HUNGARY("Hungary"),
    ICELAND("Iceland"),
    INDIA("India"),
    IRELAND("Ireland"),
    ITALY("Italy"),
    JAPAN("Japan"),
    KAZAKHSTAN("Kazakhstan"),
    LATVIA("Latvia"),
    LIECHTENSTEIN("Liechtenstein"),
    LITHUANIA("Lithuania"),
    LUXEMBOURG("Luxembourg"),
    MALTA("Malta"),
    MEXICO("Mexico"),
    MOLDOVA("Moldova"),
    MONACO("Monaco"),
    MONTENEGRO("Montenegro"),
    NETHERLANDS("Netherlands"),
    NEW_ZEALAND("New Zealand"),
    NIGERIA("Nigeria"),
    NORTH_MACEDONIA("North Macedonia"),
    NORWAY("Norway"),
    PAKISTAN("Pakistan"),
    PERU("Peru"),
    POLAND("Poland"),
    PORTUGAL("Portugal"),
    ROMANIA("Romania"),
    RUSSIA("Russia"),
    SAN_MARINO("San Marino"),
    SERBIA("Serbia"),
    SLOVAKIA("Slovakia"),
    SLOVENIA("Slovenia"),
    SOUTH_AFRICA("South Africa"),
    SPAIN("Spain"),
    SWEDEN("Sweden"),
    SWITZERLAND("Switzerland"),
    TURKEY("Turkey"),
    UKRAINE("Ukraine"),
    UNITED_KINGDOM("United Kingdom"),
    UNITED_STATES("United States"),
    VATICAN_CITY("Vatican City");

    private final String countryName;
    private static final Map<String, Country> countryMap = new HashMap<>();

    static {
        for (Country country : Country.values()) {
            countryMap.put(country.getCountryName(), country);
        }
    }

    public static Country fromString(String countryName) {
        return countryMap.get(countryName);
    }

    Country(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }
}


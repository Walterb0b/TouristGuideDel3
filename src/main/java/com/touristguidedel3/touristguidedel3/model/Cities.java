package com.touristguidedel3.touristguidedel3.model;

public enum Cities {

    COPENHAGEN("København"),
    AARHUS("Aarhus"),
    ODENSE("Odense"),
    AALBORG("Aalborg"),
    ESBJERG("Esbjerg"),
    RANDERS("Randers"),
    KOLDING("Kolding"),
    VEJLE("Vejle"),
    HORSENS("Horsens"),
    ROSKILDE("Roskilde"),
    HERNING("Herning"),
    SILKEBORG("Silkeborg"),
    NAESTVED("Næstved"),
    SLAGELSE("Slagelse"),
    HOLBAEK("Holbæk"),
    HILLEROED("Hillerød"),
    SOENDERBORG("Sønderborg"),
    SVENDBORG("Svendborg"),
    FREDERICIA("Fredericia"),
    HELSINGOER("Helsingør"),
    BILLUND("Billund");

    private final String displayName;

    Cities(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

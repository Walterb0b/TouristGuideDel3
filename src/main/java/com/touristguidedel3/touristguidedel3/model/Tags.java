package com.touristguidedel3.touristguidedel3.model;

public enum Tags {
    KID_FRIENDLY("Børnevenlig"),
    FREE("Gratis"),
    RESTAURANT("Restaurant"),
    MUSEUM("Museum"),
    ENTERTAINMENT("Underholdning"),
    HISTORY("Historie");

    private final String displayName;

    Tags(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

package net.onelitefeather.labyrinth.utils;

import java.util.regex.Pattern;

public final class Constants {

    public static final String CONFIG_ZONE_PATH = "zones.%s";
    public static final String CONFIG_ZONE_CENTER_PATH = CONFIG_ZONE_PATH + ".centerLocation";
    public static final String CONFIG_ZONE_RADIUS_PATH = CONFIG_ZONE_PATH + ".radius";
    public static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9äöüÄÖÜß]+$");

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}

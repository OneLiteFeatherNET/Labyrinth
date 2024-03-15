package net.onelitefeather.labyrinth.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.regex.Pattern;

public final class Constants {

    public static final Component PREFIX = MiniMessage.miniMessage().deserialize("<color:#aaaaaa>[<gradient:#ff8800:#947e72:#ebc8b5>Labyrinth</gradient>] ");
    public static final String SET_RADIUS_MESSAGE = "<prefix>The radius for zone <zone> was successfully set!";
    public static final String CONFIG_ZONE_PATH = "zones.%s";
    public static final String CONFIG_ZONE_CENTER_PATH = CONFIG_ZONE_PATH + ".centerLocation";
    public static final String CONFIG_ZONE_RADIUS_PATH = CONFIG_ZONE_PATH + ".radius";
    public static final String CONFIG_ZONE_MOBSPAWNING_PATH = CONFIG_ZONE_PATH + ".mobspawning";
    public static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}

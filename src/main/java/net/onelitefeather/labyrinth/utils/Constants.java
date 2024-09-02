package net.onelitefeather.labyrinth.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;

import java.util.regex.Pattern;

public final class Constants {

    /*
    All plugin to player Chat Messages
     */
    public static final Component PREFIX = MiniMessage.miniMessage().deserialize("<color:#aaaaaa>[<gradient:#ff8800:#947e72:#ebc8b5>Labyrinth</gradient>]</color> ");
    public static final String SET_RADIUS_MESSAGE = "<prefix><color:#aaaaaa>The radius for zone <zone> was <green>successfully</green> set!";
    public static final String CREATE_ZONE_MESSAGE_SUCCESS = "<prefix><color:#aaaaaa>The zone <zone> was <green>successfully</green> created!";
    public static final String CENTER_COMMAND_MESSAGE_SUCCESS = "<prefix><color:#aaaaaa>The center was <green>successfully</green> set for zone <zone>!";
    public static final String TOGGLE_MOB_SPAWN_COMMAND_MESSAGE_SUCCESS = "<prefix><color:#aaaaaa>Mobspawning was <green>successfully</green> set for zone <zone> to <green><value></green>!";
    public static final String ZONE_INVALID_MESSAGE = "<prefix><color:#aaaaaa>The zone name needs to contain only <red>alphanumeric characters</red> or might <red>not be created yet</red>!";
    public static final String PLAYER_TITLE_ENTER_MESSAGE = "Welcome to the <prefix>!";
    /*
    All config & validation related
     */
    public static final String CONFIG_ZONE_PATH = "zones.%s";
    public static final String CONFIG_ZONE_CENTER_PATH = CONFIG_ZONE_PATH + ".centerLocation";
    public static final String CONFIG_ZONE_RADIUS_PATH = CONFIG_ZONE_PATH + ".radius";
    public static final String CONFIG_ZONE_MOBSPAWNING_PATH = CONFIG_ZONE_PATH + ".mobspawning";
    public static final Pattern PATTERN = Pattern.compile("^[a-zA-Z0-9]+$");

    private Constants() {
        throw new IllegalStateException("Utility class");
    }
}

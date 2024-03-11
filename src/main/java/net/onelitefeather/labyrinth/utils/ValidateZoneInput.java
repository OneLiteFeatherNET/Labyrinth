package net.onelitefeather.labyrinth.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.onelitefeather.labyrinth.Labyrinth;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

public class ValidateZoneInput {

    public static void validateZoneInput(@NotNull Player player, @Argument(value = "zone") String zone, Labyrinth labyrinth) {
        if (labyrinth.getConfig().isSet(zone)) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(
                    "Zone <zone> could not be found!", Placeholder.unparsed("zone", zone)));
            return;
        }
        Matcher matcher = Constants.PATTERN.matcher(zone);
        boolean notMatches = !(matcher.matches());
        if (notMatches) {
            player.sendMessage(Component.text("Only characters without symbols are allowed"));
            return;
        }
    }
}

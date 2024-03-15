package net.onelitefeather.labyrinth.commands;

import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.utils.Constants;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;

import java.util.regex.Matcher;

@Command("labyrinth")
public record CreateZoneCommand(Labyrinth labyrinth) implements ZoneSuggestions {

    @Command("create <zone>")
    public void createZone(Player player, @Argument(value = "zone", suggestions = "zone") String zone) {
        Matcher matcher = Constants.PATTERN.matcher(zone);
        if (matcher.matches()) {
            labyrinth.getConfig().createSection(Constants.CONFIG_ZONE_PATH.formatted(zone));
            labyrinth.saveConfig();
        }
    }
}

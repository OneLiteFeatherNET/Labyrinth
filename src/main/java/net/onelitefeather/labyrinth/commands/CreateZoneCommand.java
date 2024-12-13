package net.onelitefeather.labyrinth.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.utils.Constants;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.Permission;

import java.util.regex.Matcher;


@Command("labyrinth")
public record CreateZoneCommand(Labyrinth labyrinth) {

    @Command("create <zone>")
    @Permission("labyrinth.setup.createzone")
    public void createZone(Player player, @Argument(value = "zone", suggestions = "zones") String zone) {
        Matcher matcher = Constants.PATTERN.matcher(zone);
        if (matcher.matches()) {
            labyrinth.getConfig().createSection(Constants.CONFIG_ZONE_PATH.formatted(zone));
            labyrinth.saveConfig();
            var message = MiniMessage.miniMessage().deserialize(Constants.CREATE_ZONE_MESSAGE_SUCCESS,
                    Placeholder.unparsed("zone", zone),
                    Placeholder.component("prefix", Constants.PREFIX));
            player.sendMessage(message);
        } else {
            var message = MiniMessage.miniMessage().deserialize(Constants.ZONE_INVALID_MESSAGE,
                    Placeholder.component("prefix", Constants.PREFIX));
            player.sendMessage(message);
        }
    }
}

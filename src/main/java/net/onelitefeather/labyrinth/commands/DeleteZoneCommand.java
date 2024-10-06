package net.onelitefeather.labyrinth.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.utils.Constants;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.Permission;


@Command("labyrinth")
public record DeleteZoneCommand(Labyrinth labyrinth) {

    @Command("delete <zone>")
    @Permission("labyrinth.setup.deletezone")
    public void createZone(Player player, @Argument(value = "zone", suggestions = "zones") String zone) {
        var zoneString = Constants.CONFIG_ZONE_PATH.formatted(zone);
        if(labyrinth.getConfig().contains(zoneString)) {
            labyrinth.getConfig().set(zoneString, null);
            labyrinth.saveConfig();
            var message = MiniMessage.miniMessage().deserialize(Constants.DELETE_ZONE_MESSAGE_SUCCESS,
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

package net.onelitefeather.labyrinth.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.utils.Constants;
import net.onelitefeather.labyrinth.utils.ValidateZoneInput;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;

@Command("labyrinth")
public record CenterCommand(Labyrinth labyrinth) implements ZoneSuggestions {

    @Command("center <zone>")
    public void centerCommand(Player player, @Argument(value = "zone", suggestions = "zone") String zone) {
        if (ValidateZoneInput.validateZoneInput(player, zone, labyrinth)) {
            this.labyrinth.getConfig().set(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zone), player.getLocation());
            labyrinth.saveConfig();
            var message = MiniMessage.miniMessage().deserialize(Constants.CENTER_COMMAND_MESSAGE_SUCCESS,
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

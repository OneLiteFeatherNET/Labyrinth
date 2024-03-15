package net.onelitefeather.labyrinth.commands;

import net.kyori.adventure.chat.ChatType;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.utils.Constants;
import net.onelitefeather.labyrinth.utils.ValidateZoneInput;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.jetbrains.annotations.NotNull;


@Command("labyrinth")
public record SetRadiusCommand(Labyrinth labyrinth) implements ZoneSuggestions {

    @Command("setradius <zone>")
    public void setRadius(@NotNull Player player, @Argument(value = "zone", suggestions = "zone") String zone) {
        if (ValidateZoneInput.validateZoneInput(player, zone, labyrinth)) {
            Location playerLabyrinthCenterLocation = player.getLocation();

            Location location = labyrinth.getConfig().getLocation(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zone));
            if (location == null) return;


            labyrinth.getConfig().set(Constants.CONFIG_ZONE_RADIUS_PATH.formatted(zone), playerLabyrinthCenterLocation.distance(location));
            labyrinth.saveConfig();
            var message = MiniMessage.miniMessage().deserialize(Constants.SET_RADIUS_MESSAGE, Placeholder.unparsed("zone", zone), Placeholder.component("prefix", Constants.PREFIX));
            player.sendMessage(message);
        }

    }
}

package net.onelitefeather.labyrinth.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.service.api.ValidationService;
import net.onelitefeather.labyrinth.utils.Constants;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.Permission;

@Command("labyrinth")
public record CenterCommand(Labyrinth labyrinth, ValidationService validationService) {

    @Command("center <zone>")
    @Permission("labyrinth.setup.center")
    public void centerCommand(Player player, @Argument(value = "zone", suggestions = "zones") String zone) {
        Location location = player.getLocation();

        /**
         * This location Y is important to be set to 0 for a cylindric region, see {@link SetRadiusCommand}
        * */
        location.setY(0);
        if (validationService.validateZoneInput(player,zone)) {
            this.labyrinth.getConfig().set(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zone), location);
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

package net.onelitefeather.labyrinth.commands;

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
public class SetRadiusCommand {

    private final Labyrinth labyrinth;

    public SetRadiusCommand(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    @Command("setradius <zone>")
    public void setRadius(@NotNull Player player, @Argument(value = "zone" /*,suggestions = "zones"*/) String zone) {

        ValidateZoneInput.validateZoneInput(player, zone, labyrinth);
        Location playerLabyrinthCenterLocation = player.getLocation();

        Location location = labyrinth.getConfig().getLocation(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zone));
        if (location == null) return;


        labyrinth.getConfig().set(Constants.CONFIG_ZONE_RADIUS_PATH.formatted(zone), playerLabyrinthCenterLocation.distance(location));
        labyrinth.saveConfig();
        player.sendMessage(MiniMessage.miniMessage().deserialize(
                "The radius for zone <zone> was successfully set!", Placeholder.unparsed("zone", zone)));
    }
}

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
public class CenterCommand {

    private final Labyrinth labyrinth;

    public CenterCommand(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    @Command("center <zone>")
    public void centerCommand(Player player, @Argument(value = "zone") String zone) {

        ValidateZoneInput.validateZoneInput(player, zone, labyrinth);

        this.labyrinth.getConfig().set(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zone), player.getLocation());
        labyrinth.saveConfig();
        player.sendMessage(MiniMessage.miniMessage().deserialize(
                "Zone <zone> was successfully set!", Placeholder.unparsed("zone", zone)));
    }


}

package net.onelitefeather.labyrinth.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.utils.Constants;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;

import java.util.regex.Matcher;

@Command("labyrinth")
public final class CenterCommand {

    private final Labyrinth labyrinth;

    public CenterCommand(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    @Command("center <zone>")
    public void centerCommand(Player player, @Argument(value = "zone", suggestions = "zones") String zone) {

        var configPath = Constants.CONFIG_ZONE_PATH.formatted(zone);

        if (labyrinth.getConfig().isSet(configPath)) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(
                    "Zone <zone> was already set!", Placeholder.unparsed("zone", zone)));
            return;
        }

        Matcher matcher = Constants.PATTERN.matcher(zone);
        boolean notMatches = !(matcher.matches());
        if (notMatches) {
            player.sendMessage(MiniMessage.miniMessage().deserialize("Only characters without symbols are allowed"));
            return;
        }

        this.labyrinth.getConfig().set(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zone), player.getLocation());
        labyrinth.saveConfig();
        player.sendMessage(MiniMessage.miniMessage().deserialize(
                "Zone <zone> was successfully set!", Placeholder.unparsed("zone", zone)));
    }


}

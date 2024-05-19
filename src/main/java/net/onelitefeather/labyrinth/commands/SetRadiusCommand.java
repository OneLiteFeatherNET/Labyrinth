package net.onelitefeather.labyrinth.commands;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.utils.Constants;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.suggestion.Suggestions;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;


@Command("labyrinth")
public final class SetRadiusCommand {

    private final Labyrinth labyrinth;

    public SetRadiusCommand(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    @Command("setradius <zone>")
    public void setRadius(@NotNull Player player, @Argument(value = "zone", suggestions = "zones") String zone) {

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
        Location playerLabyrinthCenterLocation = player.getLocation();

        Location location = labyrinth.getConfig().getLocation(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zone));
        if (location == null) return;

        labyrinth.getConfig().set(Constants.CONFIG_ZONE_RADIUS_PATH.formatted(zone), playerLabyrinthCenterLocation.distance(location));
        labyrinth.saveConfig();
        player.sendMessage(MiniMessage.miniMessage().deserialize(
                "The radius for zone <zone> was successfully set!", Placeholder.unparsed("zone", zone)));
    }


    @Suggestions("zones")
    public @NonNull List<@NonNull String> suggestZones(
            final @NonNull CommandContext<CommandSender> context,
            @NonNull CommandInput commandInput) {
        Set<String> zones = new HashSet<>();
        if(labyrinth.getConfig().getConfigurationSection("zones") != null) {
            zones = labyrinth.getConfig().getConfigurationSection("zones").getKeys(false);
        }
        return StringUtil.copyPartialMatches(commandInput.peekString(), zones, new ArrayList<>(zones));
    }
}

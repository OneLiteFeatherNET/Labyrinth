package net.onelitefeather.labyrinth.commands;

import net.onelitefeather.labyrinth.Labyrinth;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.annotations.suggestion.Suggestions;
import org.incendo.cloud.context.CommandContext;
import org.incendo.cloud.context.CommandInput;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ZoneSuggestions {

    private final Labyrinth labyrinth;

    public ZoneSuggestions(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
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

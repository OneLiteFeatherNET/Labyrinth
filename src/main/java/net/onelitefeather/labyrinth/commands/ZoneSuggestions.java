package net.onelitefeather.labyrinth.commands;

import net.onelitefeather.labyrinth.Labyrinth;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.util.StringUtil;
import org.incendo.cloud.annotations.suggestion.Suggestions;
import org.incendo.cloud.context.CommandContext;

import java.util.ArrayList;
import java.util.List;

public final class ZoneSuggestions {
    private final Labyrinth labyrinth;

    public ZoneSuggestions(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    @Suggestions("zones")
    public List<String> suggestions(CommandContext<CommandSender> context, String input) {
        ConfigurationSection zones = labyrinth.getConfig().getConfigurationSection("zones");
        if (zones == null) {
            return List.of();
        }
        return StringUtil.copyPartialMatches(input, zones.getKeys(false).stream().toList(), new ArrayList<>()) ;
    }
}

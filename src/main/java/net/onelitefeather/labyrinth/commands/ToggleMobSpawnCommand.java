package net.onelitefeather.labyrinth.commands;

import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.utils.Constants;
import net.onelitefeather.labyrinth.utils.ValidateZoneInput;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.jetbrains.annotations.NotNull;

@Command("labyrinth")
public record ToggleMobSpawnCommand(Labyrinth labyrinth) implements ZoneSuggestions {

    @Command("toggle <zone>")
    public void toggleMobSpawn(@NotNull Player player, @Argument(value = "zone", suggestions = "zone") String zone) {
        if (ValidateZoneInput.validateZoneInput(player, zone, labyrinth)) {
            boolean mobSpawning = !labyrinth.getConfig().getBoolean(Constants.CONFIG_ZONE_MOBSPAWNING_PATH.formatted(zone), false);
            labyrinth.getConfig().set(Constants.CONFIG_ZONE_MOBSPAWNING_PATH.formatted(zone), mobSpawning);
            labyrinth.saveConfig();
        }
    }
}

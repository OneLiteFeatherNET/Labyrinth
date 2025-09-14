package net.onelitefeather.labyrinth.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.service.api.ValidationService;
import net.onelitefeather.labyrinth.utils.Constants;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.incendo.cloud.annotations.Permission;
import org.jetbrains.annotations.NotNull;

@Command("labyrinth")
public record ToggleMobSpawnCommand(Labyrinth labyrinth, ValidationService validationService) {

    @Command("toggle <zone>")
    @Permission("labyrinth.toggle.mobspawn")
    public void toggleMobSpawn(@NotNull Player player, @Argument(value = "zone", suggestions = "zones") String zone) {
        if (validationService.validateZoneInput(player, zone)) {
            boolean mobSpawning = !labyrinth.getConfig().getBoolean(Constants.CONFIG_ZONE_MOBSPAWNING_PATH.formatted(zone), false);
            labyrinth.getConfig().set(Constants.CONFIG_ZONE_MOBSPAWNING_PATH.formatted(zone), mobSpawning);
            labyrinth.saveConfig();
            var message = MiniMessage.miniMessage().deserialize(Constants.TOGGLE_MOB_SPAWN_COMMAND_MESSAGE_SUCCESS,
                    Placeholder.unparsed("zone", zone),
                    Placeholder.component("prefix", Constants.PREFIX),
                    Placeholder.unparsed("value", String.valueOf(mobSpawning)));
            player.sendMessage(message);
        } else {
            var message = MiniMessage.miniMessage().deserialize(Constants.ZONE_INVALID_MESSAGE,
                    Placeholder.component("prefix", Constants.PREFIX));
            player.sendMessage(message);
        }
    }
}

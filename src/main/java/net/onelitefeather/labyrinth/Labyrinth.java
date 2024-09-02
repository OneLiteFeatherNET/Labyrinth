package net.onelitefeather.labyrinth;

import net.onelitefeather.labyrinth.commands.CenterCommand;
import net.onelitefeather.labyrinth.commands.CreateZoneCommand;
import net.onelitefeather.labyrinth.commands.ToggleMobSpawnCommand;
import net.onelitefeather.labyrinth.commands.SetRadiusCommand;
import net.onelitefeather.labyrinth.listener.MobspawnListener;
import net.onelitefeather.labyrinth.commands.ZoneSuggestions;
import net.onelitefeather.labyrinth.listener.PlayerEnterListener;
import net.onelitefeather.labyrinth.utils.Constants;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.meta.CommandMeta;
import org.incendo.cloud.paper.LegacyPaperCommandManager;
import java.util.Set;

public class Labyrinth extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerCommands();
        registerListeners();
    }

    public void registerCommands() {

        var commandManager = LegacyPaperCommandManager.createNative(this, ExecutionCoordinator.simpleCoordinator());

        if (commandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            commandManager.registerAsynchronousCompletions();
        }

        var annotationParser = new AnnotationParser<>(commandManager, CommandSender.class, parameters -> CommandMeta.empty());

        annotationParser.parse(new ZoneSuggestions(this));
        annotationParser.parse(new CenterCommand(this));
        annotationParser.parse(new SetRadiusCommand(this));
        annotationParser.parse(new ToggleMobSpawnCommand(this));
        annotationParser.parse(new CreateZoneCommand(this));
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new MobspawnListener(this), this);
        getServer().getPluginManager().registerEvents(new PlayerEnterListener(this), this);
    }

    public Boolean isInZone(Location location) {
        FileConfiguration config = getConfig();
        location.setY(0);
        var zoneSection = config.getConfigurationSection("zones");
        if (zoneSection == null) {
            return false;
        }
        Set<String> zones = zoneSection.getKeys(false);
        for (var zone : zones) {
            var radius = config.getDouble(Constants.CONFIG_ZONE_RADIUS_PATH.formatted(zone));
            var centerLocation = config.getLocation(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zone));
            if(centerLocation == null) return false;
            var isInZone = location.distance(centerLocation) < radius;
            var enabled = config.getBoolean(Constants.CONFIG_ZONE_MOBSPAWNING_PATH.formatted(zone));
            if (isInZone && enabled) {
                return true;
            }
        }
        return false;
    }
}
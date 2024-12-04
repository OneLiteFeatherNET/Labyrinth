package net.onelitefeather.labyrinth;

import net.onelitefeather.labyrinth.commands.CenterCommand;
import net.onelitefeather.labyrinth.commands.CreateZoneCommand;
import net.onelitefeather.labyrinth.commands.DeleteZoneCommand;
import net.onelitefeather.labyrinth.commands.ToggleMobSpawnCommand;
import net.onelitefeather.labyrinth.commands.SetRadiusCommand;
import net.onelitefeather.labyrinth.listener.MobspawnListener;
import net.onelitefeather.labyrinth.commands.ZoneSuggestions;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.meta.CommandMeta;
import org.incendo.cloud.paper.LegacyPaperCommandManager;
import java.util.logging.Level;

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
            getLogger().log(Level.INFO, "Asynchronous tab completion enabled"); // Might be useful to know if it works
        }
        var annotationParser = new AnnotationParser<>(commandManager, CommandSender.class, parameters -> CommandMeta.empty());

        annotationParser.parse(new ZoneSuggestions(this));
        annotationParser.parse(new CenterCommand(this));
        annotationParser.parse(new SetRadiusCommand(this));
        annotationParser.parse(new ToggleMobSpawnCommand(this));
        annotationParser.parse(new CreateZoneCommand(this));
        annotationParser.parse(new DeleteZoneCommand(this));
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new MobspawnListener(this), this);
    }

}
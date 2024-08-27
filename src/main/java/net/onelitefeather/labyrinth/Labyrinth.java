package net.onelitefeather.labyrinth;

import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.onelitefeather.labyrinth.commands.CenterCommand;
import net.onelitefeather.labyrinth.commands.CreateZoneCommand;
import net.onelitefeather.labyrinth.commands.ToggleMobSpawnCommand;
import net.onelitefeather.labyrinth.commands.SetRadiusCommand;
import net.onelitefeather.labyrinth.listener.MobspawnListener;
import net.onelitefeather.labyrinth.commands.ZoneSuggestions;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.units.qual.C;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.meta.CommandMeta;
import org.incendo.cloud.paper.LegacyPaperCommandManager;
import org.incendo.cloud.paper.PaperCommandManager;

public class Labyrinth extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerCommands();
        registerListeners();
    }

    public void registerCommands() {
        PaperCommandManager<CommandSourceStack> commandManager = PaperCommandManager.builder()
                .executionCoordinator(ExecutionCoordinator.simpleCoordinator())
                .buildOnEnable(this);

//        if (commandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
//           commandManager.registerAsynchronousCompletions();
//        }

        AnnotationParser<CommandSourceStack> annotationParser = new AnnotationParser<>(
                commandManager,
                CommandSourceStack.class,
                parameters -> CommandMeta.empty()
        );

        annotationParser.parse(new ZoneSuggestions(this));
        annotationParser.parse(new CenterCommand(this));
        annotationParser.parse(new SetRadiusCommand(this));
        annotationParser.parse(new ToggleMobSpawnCommand(this));
        annotationParser.parse(new CreateZoneCommand(this));
    }

    public void registerListeners() {
        getServer().getPluginManager().registerEvents(new MobspawnListener(this), this);
    }

}
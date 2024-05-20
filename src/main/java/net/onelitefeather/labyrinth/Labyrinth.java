package net.onelitefeather.labyrinth;

import net.onelitefeather.labyrinth.commands.CenterCommand;
import net.onelitefeather.labyrinth.commands.SetRadiusCommand;
import net.onelitefeather.labyrinth.commands.ZoneSuggestions;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.meta.CommandMeta;
import org.incendo.cloud.paper.PaperCommandManager;

import java.lang.annotation.Annotation;

public class Labyrinth extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerCommands();
    }

    public void registerCommands() {

        var commandManager = PaperCommandManager.createNative(this, ExecutionCoordinator.simpleCoordinator());

        if (commandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            commandManager.registerAsynchronousCompletions();
        }

        var annotationParser = new AnnotationParser<>(commandManager, CommandSender.class, parameters -> CommandMeta.empty());

        annotationParser.parse(new ZoneSuggestions(this));
        annotationParser.parse(new CenterCommand(this));
        annotationParser.parse(new SetRadiusCommand(this));
    }

}
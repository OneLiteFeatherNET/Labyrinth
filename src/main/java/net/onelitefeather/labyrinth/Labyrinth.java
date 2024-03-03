package net.onelitefeather.labyrinth;

import net.onelitefeather.labyrinth.commands.CenterCommand;
import net.onelitefeather.labyrinth.commands.SetRadiusCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.incendo.cloud.annotations.AnnotationParser;
import org.incendo.cloud.bukkit.CloudBukkitCapabilities;
import org.incendo.cloud.execution.ExecutionCoordinator;
import org.incendo.cloud.meta.CommandMeta;
import org.incendo.cloud.paper.PaperCommandManager;
import java.util.regex.Pattern;

public class Labyrinth extends JavaPlugin {

    public static final String CONFIG_CENTER_LOCATION = "centerLocation";
    public static final String CONFIG_RADIUS = "radius";
    public static final Pattern pattern = Pattern.compile("[a-zA-Z0-9]+/g");

    @Override
    public void onEnable() {
        saveDefaultConfig();
        registerCommands();
    }

    public void registerCommands() {
        PaperCommandManager<CommandSender> commandManager = PaperCommandManager.createNative(
                Labyrinth.this,
                ExecutionCoordinator.simpleCoordinator()
        );

        if (commandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            commandManager.registerAsynchronousCompletions();
        }
        AnnotationParser<CommandMeta> annotationParser = new AnnotationParser<>(
                commandManager,
                CommandMeta.class,
                parameters -> CommandMeta.empty()
        );
        annotationParser.parse(new CenterCommand(this));
        annotationParser.parse(new SetRadiusCommand(this));
    }
}
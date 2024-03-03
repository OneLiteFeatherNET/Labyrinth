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

public class Labyrinth extends JavaPlugin {

    public static final String CONFIG_CENTER_LOCATION = "centerLocation";
    public static final String CONFIG_RADIUS = "radius";

    @Override
    public void onEnable() {
        saveDefaultConfig();

        PaperCommandManager<CommandSender> commandManager = PaperCommandManager.createNative(
                Labyrinth.this, /* 1 */
                ExecutionCoordinator.simpleCoordinator() /* 2 */
        );

        if (commandManager.hasCapability(CloudBukkitCapabilities.ASYNCHRONOUS_COMPLETION)) {
            commandManager.registerAsynchronousCompletions();
        }
        AnnotationParser annotationParser = new AnnotationParser<>(
                commandManager,
                CommandMeta.class,
                parameters -> CommandMeta.empty()
        );

        annotationParser.parse(new CenterCommand(this));
        
        var setRadiusCommand = this.getCommand("setradius");
        if (setRadiusCommand != null) {
            setRadiusCommand.setExecutor(new SetRadiusCommand(this));
        }


    }
}
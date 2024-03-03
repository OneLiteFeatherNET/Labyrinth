package net.onelitefeather.labyrinth.commands;

import net.kyori.adventure.text.Component;
import net.onelitefeather.labyrinth.Labyrinth;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Command;

import java.util.regex.Matcher;
import static net.onelitefeather.labyrinth.Labyrinth.pattern;
import static org.bukkit.plugin.java.JavaPlugin.getPlugin;

@Command("labyrinth")
public class CenterCommand {

    private static final String CONFIG_PATH = "%s.%s";

    private final Labyrinth labyrinth;



    public CenterCommand(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }
    @Command("center <zone>")
    public void centerCommand(Player player, String zone) {
        Matcher matcher = pattern.matcher(zone);
        boolean notMatches = !(matcher.matches());
        if (notMatches) {
            player.sendMessage(Component.text("Only characters without symbols are allowed"));
            return;
        }
            this.labyrinth.getConfig().set(CONFIG_PATH.formatted(zone,
                    Labyrinth.CONFIG_CENTER_LOCATION), player.getLocation());
            labyrinth.saveConfig();
    }


}

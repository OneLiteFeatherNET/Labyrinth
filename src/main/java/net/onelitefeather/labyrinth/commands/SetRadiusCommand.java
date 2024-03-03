package net.onelitefeather.labyrinth.commands;

import net.kyori.adventure.text.Component;
import net.onelitefeather.labyrinth.Labyrinth;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Command;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Matcher;

import static net.onelitefeather.labyrinth.Labyrinth.pattern;

@Command("labyrinth")
public class SetRadiusCommand {

    private final Labyrinth labyrinth;

    public SetRadiusCommand(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    @Command("setradius <zone>")
    public void setRadius(@NotNull Player player, String zone) {
        Matcher matcher = pattern.matcher(zone);
        boolean notMatches = !(matcher.matches());
        if (notMatches) {
            player.sendMessage(Component.text("Only characters without symbols are allowed"));
            return;
        }
        Location playerLabyrinthCenterLocation = player.getLocation();
            if (labyrinth.getConfig().contains(Labyrinth.CONFIG_CENTER_LOCATION)) {
                Location location = labyrinth.getConfig().getLocation(Labyrinth.CONFIG_CENTER_LOCATION);
                if (location == null) return;
                labyrinth.getConfig().set(Labyrinth.CONFIG_RADIUS, playerLabyrinthCenterLocation.distance(location));
                labyrinth.saveConfig();
            }
    }
}

package net.onelitefeather.labyrinth.commands;

import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.utils.ValidateZoneInput;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Argument;
import org.incendo.cloud.annotations.Command;
import org.jetbrains.annotations.NotNull;

@Command("labyrinth")
public class MobspawnCommand {

    private final Labyrinth labyrinth;
    private boolean toggled;

    public MobspawnCommand(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    @Command("mobspawn <zone>")
    public void toggleMobSpawn(@NotNull Player player, @Argument(value = "zone") String zone) {

        ValidateZoneInput.validateZoneInput(player, zone, labyrinth);


    }

}

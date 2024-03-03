package net.onelitefeather.labyrinth.commands;

import net.onelitefeather.labyrinth.Labyrinth;
import org.bukkit.entity.Player;
import org.incendo.cloud.annotations.Command;

@Command("labyrinth")
public class CenterCommand  {

    private static final String CONFIG_PATH = "%s.%s";

    private final Labyrinth labyrinth;

    public CenterCommand(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }
    @Command("center <zone>")
    public void centerCommand(Player player, String zone) {
        //TODO: Zone niemals vetrauen bei der Nutzer eingabe. Wir wollen ja keine ÖÄÜ oder andere komische Zeichen in der Konfig haben
        this.labyrinth.getConfig().set(CONFIG_PATH.formatted(zone,
                Labyrinth.CONFIG_CENTER_LOCATION), player.getLocation());
    }


}

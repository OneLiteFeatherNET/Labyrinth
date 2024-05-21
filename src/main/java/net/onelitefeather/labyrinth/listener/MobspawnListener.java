package net.onelitefeather.labyrinth.listener;

import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.utils.Constants;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Set;

public class MobspawnListener implements Listener {
    private final Labyrinth labyrinth;

    public MobspawnListener(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    @EventHandler
    public void onMobSpawn(EntitySpawnEvent event) {
        if (!(event.getEntity() instanceof Mob && !(event.getEntity() instanceof Bat))) {
            return;
        }

        FileConfiguration config = labyrinth.getConfig();
        var zoneSection = config.getConfigurationSection("zones");
        if (zoneSection == null) {
            return;
        }
        Set<String> zones = zoneSection.getKeys(false);
        for (String zone : zones) {
            var centerLocation = config.getLocation(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zone));
            var enabled = config.getBoolean(Constants.CONFIG_ZONE_MOBSPAWNING_PATH.formatted(zone));
            if (enabled && centerLocation != null) {
                event.setCancelled(true);
            }
        }
    }
}

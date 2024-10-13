package net.onelitefeather.labyrinth.listener;

import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.utils.Constants;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Bat;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntitySpawnEvent;

import java.util.Set;

public class MobspawnListener implements Listener {

    /**
     * An instance of the Labyrinth from the Labyrinth class
     */
    private final Labyrinth labyrinth;

    public MobspawnListener(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    /**
     * This event is used for canceling mob spawning in an existing labyrinth region because on our labyrinth build
     * On our server there is no lighting below / above, and we don't want mobs to spawn there, also Bats are friendly but
     * Only spawn in caves or when Minecraft thinks we are in a cave, so we don't want to let them spawn too.
     *
     * @param event is EntitySpawnEvent which is called when an Entity spawns on the world
     *              <p>
     *              This Mobspawn Feature can be toggled and is saved for each zone in the configuration separately
     */
    @EventHandler
    public void onMobSpawn(EntitySpawnEvent event) {

        if (event.getEntity() instanceof Player) return;

        FileConfiguration config = labyrinth.getConfig();
        Location location = event.getEntity().getLocation();
        location.setY(0);
        var zoneSection = config.getConfigurationSection("zones");
        if (zoneSection == null) {
            return;
        }
        Set<String> zones = zoneSection.getKeys(false);
        for (var zone : zones) {
            var radius = config.getDouble(Constants.CONFIG_ZONE_RADIUS_PATH.formatted(zone));
            var centerLocation = config.getLocation(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zone));
            if (centerLocation == null) return;
            var isInZone = location.distance(centerLocation) < radius;
            if (!isInZone) continue;
            var disabled = !(config.getBoolean(Constants.CONFIG_ZONE_MOBSPAWNING_PATH.formatted(zone)));
            if (disabled) {
                // We don't want Bats spawning everywhere below and above the actual labyrinth build, would be too many because of the lighting
                if (!(event.getEntity() instanceof Monster || event.getEntity() instanceof Bat)) {
                    continue;
                }
                event.setCancelled(true);
            }
        }

    }
}

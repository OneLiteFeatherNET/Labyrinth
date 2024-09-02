package net.onelitefeather.labyrinth.listener;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.title.Title;
import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.event.ZoneEnterEvent;
import net.onelitefeather.labyrinth.utils.Constants;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class PlayerEnterZoneListener implements Listener {

    private final NamespacedKey inRegion;

    /**
     * An instance of the Labyrinth from the Labyrinth class
     */
    private final Labyrinth labyrinth;

    public PlayerEnterZoneListener(Labyrinth labyrinth) {
        this.inRegion = NamespacedKey.fromString("region", labyrinth);
        this.labyrinth = labyrinth;
    }

    @EventHandler
    public void onPlayerEnter(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        PersistentDataContainer persistentDataContainer = player.getPersistentDataContainer();
        boolean inRegion = labyrinth.isInZone(player.getLocation());
        if (persistentDataContainer.has(this.inRegion) && !inRegion) {
            persistentDataContainer.remove(this.inRegion);
        }
        if (!persistentDataContainer.has(this.inRegion) && inRegion) {
            persistentDataContainer.set(this.inRegion, PersistentDataType.BOOLEAN, true);
            ZoneEnterEvent.create(player).callEvent();
        }
    }
    @EventHandler
    public void onPlayerEnter(ZoneEnterEvent zoneEnterEvent) {
        var player = zoneEnterEvent.getPlayer();
        var message = MiniMessage.miniMessage().deserialize(Constants.PLAYER_TITLE_ENTER_MESSAGE,
                Placeholder.component("prefix",Constants.PREFIX));
        player.showTitle(Title.title(message,Component.empty()));
    }
}

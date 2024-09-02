package net.onelitefeather.labyrinth.event;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class RegionEnterEvent extends Event {

    private static final HandlerList HANDLER_LIST = new HandlerList();

    private final Player player;

    public RegionEnterEvent(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLER_LIST;
    }

    public static RegionEnterEvent create(Player player) {
        return new RegionEnterEvent(player);
    }


    public static HandlerList getHandlerList() {
        return HANDLER_LIST;
    }
}

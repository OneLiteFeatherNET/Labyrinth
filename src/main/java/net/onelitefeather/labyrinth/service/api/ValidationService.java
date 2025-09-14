package net.onelitefeather.labyrinth.service.api;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;


public interface ValidationService {

    boolean validateZoneInput(@NotNull Player player, @NotNull String zone);
}

package net.onelitefeather.labyrinth.service.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.service.api.ValidationService;
import net.onelitefeather.labyrinth.utils.Constants;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import java.util.regex.Matcher;

public final class ValidationServiceImpl implements ValidationService {

    private final Labyrinth labyrinth;

    public ValidationServiceImpl(Labyrinth labyrinth) {
        this.labyrinth = labyrinth;
    }

    @Override
    public boolean validateZoneInput(@NotNull Player player, @NotNull String zone) {
        if (!labyrinth.getConfig().isSet(Constants.CONFIG_ZONE_PATH.formatted(zone))) {
            player.sendMessage(MiniMessage.miniMessage().deserialize(
                    "Zone <zone> could not be found!", Placeholder.unparsed("zone", zone)));
            return false;
        }
        Matcher matcher = Constants.PATTERN.matcher(zone);
        boolean notMatches = !(matcher.matches());
        if (notMatches) {
            player.sendMessage(Component.text("Only characters without symbols are allowed"));
            return false;
        }
        return true;
    }

}

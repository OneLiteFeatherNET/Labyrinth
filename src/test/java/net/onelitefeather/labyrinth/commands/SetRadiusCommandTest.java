package net.onelitefeather.labyrinth.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.onelitefeather.labyrinth.service.api.ValidationService;
import net.onelitefeather.labyrinth.utils.Constants;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.world.WorldMock;

import static org.junit.jupiter.api.Assertions.*;

class SetRadiusCommandTest extends CommandPluginTestBase{

    private SetRadiusCommand command;
    private SetRadiusCommandTest.MockValidationService validationService;

    public static class MockValidationService implements ValidationService
    {
        private boolean isValid;

        @Override
        public boolean validateZoneInput(@NotNull Player player, @NotNull String zone) {
            return isValid;
        }

        public void setValid(boolean valid) {
            isValid = valid;
        }

        public boolean isValid() {
            return isValid;
        }
    }

    @Override
    @BeforeEach
    void setUp()
    {
        super.setUp();
        validationService = new SetRadiusCommandTest.MockValidationService();
        command = new SetRadiusCommand(plugin, validationService);
    }

    @Test
    void testValidationWrong()
    {
        var player = server.addPlayer();
        var zoneName = "Test";
        var expectedMessage = MiniMessage.miniMessage().deserialize(Constants.ZONE_INVALID_MESSAGE,
                Placeholder.component("prefix", Constants.PREFIX));
        validationService.setValid(false);
        command.setRadius(player, zoneName);
        assertEquals(expectedMessage, player.nextComponentMessage());
    }

    @Test
    void testNoLocationSet()
    {
        var player = server.addPlayer();
        var zoneName = "Test";
        validationService.setValid(true);
        command.setRadius(player, zoneName);
        assertNull(player.nextComponentMessage());
    }

    @Test
    void testRadiusSet()
    {
        var player = server.addPlayer();
        var zoneName = "Test";
        var expectedMessage = MiniMessage.miniMessage().deserialize(Constants.SET_RADIUS_MESSAGE,
                Placeholder.unparsed("zone", zoneName),
                Placeholder.component("prefix", Constants.PREFIX));
        var world = new WorldMock(Material.GRASS_BLOCK, 64);
        var centerLocation = new Location(world, 120, 0, 120);
        var playerLocation = new Location(world, 240, 64, 240);

        validationService.setValid(true);
        plugin.getConfig().set(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zoneName), centerLocation);
        player.setLocation(playerLocation);

        command.setRadius(player,zoneName);

        playerLocation.setY(0);
        var expectedDistance = centerLocation.distance(playerLocation);
        assertEquals(expectedDistance,  plugin.getConfig().getDouble(Constants.CONFIG_ZONE_RADIUS_PATH.formatted(zoneName)));
        assertEquals(expectedMessage, player.nextComponentMessage());
    }

}
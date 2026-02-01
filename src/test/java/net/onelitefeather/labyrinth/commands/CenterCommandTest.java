package net.onelitefeather.labyrinth.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.onelitefeather.labyrinth.service.api.ValidationService;
import net.onelitefeather.labyrinth.utils.Constants;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.*;
import org.mockbukkit.mockbukkit.world.WorldMock;

class CenterCommandTest extends CommandPluginTestBase {

    private CenterCommand command;
    private MockValidationService validationService;

    public static class MockValidationService implements ValidationService {
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
    void setUp() {
        super.setUp();
        validationService = new MockValidationService();
        command = new CenterCommand(plugin, validationService);
    }


    @DisplayName("Test if the player location is not zero")
    @Test
    void testIsYLocationFromPlayerNotZero() {
        var player = server.addPlayer();
        var location = new Location(new WorldMock(Material.GRASS_BLOCK, 64), 120, 64, 120);
        player.setLocation(location);

        command.centerCommand(player, "Test");
        Assertions.assertNotEquals(0, location.getY());
    }

    @Test
    void testValidationWrong() {
        var player = server.addPlayer();
        var location = new Location(new WorldMock(Material.GRASS_BLOCK, 64), 120, 64, 120);
        player.setLocation(location);

        validationService.setValid(false);
        command.centerCommand(player, "Test");
        var playerMessage = player.nextComponentMessage();
        Assertions.assertNotNull(playerMessage);
        var expectedMessage = MiniMessage.miniMessage().deserialize(Constants.ZONE_INVALID_MESSAGE,
                Placeholder.component("prefix", Constants.PREFIX));
        Assertions.assertEquals(expectedMessage, playerMessage);
        Assertions.assertFalse(plugin.getConfig().contains(Constants.CONFIG_ZONE_CENTER_PATH.formatted("Test")));
    }

    @Test
    void testValidationTrue() {
        var player = server.addPlayer();
        var location = new Location(new WorldMock(Material.GRASS_BLOCK, 64), 120, 64, 120);
        var zoneName = "Test";
        var expectedMessage = MiniMessage.miniMessage().deserialize(Constants.CENTER_COMMAND_MESSAGE_SUCCESS,
                Placeholder.unparsed("zone", zoneName),
                Placeholder.component("prefix", Constants.PREFIX));

        player.setLocation(location);
        validationService.setValid(true);
        command.centerCommand(player, zoneName);
        location.setY(0);
        var playerMessage = player.nextComponentMessage();

        Assertions.assertNotNull(playerMessage);
        Assertions.assertEquals(expectedMessage, playerMessage);
        Assertions.assertTrue(plugin.getConfig().contains(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zoneName)));
        Assertions.assertEquals(location, plugin.getConfig().getLocation(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zoneName)));
    }

}
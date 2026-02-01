package net.onelitefeather.labyrinth.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.onelitefeather.labyrinth.service.api.ValidationService;
import net.onelitefeather.labyrinth.utils.Constants;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToggleMobSpawnCommandTest extends CommandPluginTestBase {

    private ToggleMobSpawnCommand command;
    private ToggleMobSpawnCommandTest.MockValidationService validationService;

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
        validationService = new ToggleMobSpawnCommandTest.MockValidationService();
        command = new ToggleMobSpawnCommand(plugin, validationService);
    }

    @Test
    void testZoneNotValid() {
        var player = server.addPlayer();
        var expectedMessage = MiniMessage.miniMessage().deserialize(Constants.ZONE_INVALID_MESSAGE,
                Placeholder.component("prefix", Constants.PREFIX));

        validationService.setValid(false);

        command.toggleMobSpawn(player, "Test");
        assertEquals(expectedMessage, player.nextComponentMessage());
    }

    @Test
    void testActivateMobSpawningWithNotSetInConfig() {
        var player = server.addPlayer();
        var zoneName = "Test";
        var expectedMessage = MiniMessage.miniMessage().deserialize(Constants.TOGGLE_MOB_SPAWN_COMMAND_MESSAGE_SUCCESS,
                Placeholder.unparsed("zone", zoneName),
                Placeholder.component("prefix", Constants.PREFIX),
                Placeholder.unparsed("value", String.valueOf(true)));
        validationService.setValid(true);

        command.toggleMobSpawn(player, zoneName);
        assertEquals(expectedMessage, player.nextComponentMessage());
        assertTrue(plugin.getConfig().getBoolean(Constants.CONFIG_ZONE_MOBSPAWNING_PATH.formatted(zoneName)));
    }

    @Test
    void testMobSpawningChangedToTrue() {
        var player = server.addPlayer();
        var zoneName = "Test";
        var expectedMessage = MiniMessage.miniMessage().deserialize(Constants.TOGGLE_MOB_SPAWN_COMMAND_MESSAGE_SUCCESS,
                Placeholder.unparsed("zone", zoneName),
                Placeholder.component("prefix", Constants.PREFIX),
                Placeholder.unparsed("value", String.valueOf(true)));
        plugin.getConfig().set(Constants.CONFIG_ZONE_MOBSPAWNING_PATH.formatted(zoneName), false);
        validationService.setValid(true);

        command.toggleMobSpawn(player, zoneName);
        assertEquals(expectedMessage, player.nextComponentMessage());
        assertTrue(plugin.getConfig().getBoolean(Constants.CONFIG_ZONE_MOBSPAWNING_PATH.formatted(zoneName)));
    }

    @Test
    void testMobSpawningChangedToFalse() {
        var player = server.addPlayer();
        var zoneName = "Test";
        var expectedMessage = MiniMessage.miniMessage().deserialize(Constants.TOGGLE_MOB_SPAWN_COMMAND_MESSAGE_SUCCESS,
                Placeholder.unparsed("zone", zoneName),
                Placeholder.component("prefix", Constants.PREFIX),
                Placeholder.unparsed("value", String.valueOf(false)));
        plugin.getConfig().set(Constants.CONFIG_ZONE_MOBSPAWNING_PATH.formatted(zoneName), true);
        validationService.setValid(true);

        command.toggleMobSpawn(player, zoneName);
        assertEquals(expectedMessage, player.nextComponentMessage());
        assertFalse(plugin.getConfig().getBoolean(Constants.CONFIG_ZONE_MOBSPAWNING_PATH.formatted(zoneName)));
    }

}
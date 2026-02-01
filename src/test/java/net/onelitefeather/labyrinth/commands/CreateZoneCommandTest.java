package net.onelitefeather.labyrinth.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.onelitefeather.labyrinth.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateZoneCommandTest extends CommandPluginTestBase {

    private CreateZoneCommand command;

    @Override
    @BeforeEach
    void setUp() {
        super.setUp();
        command = new CreateZoneCommand(plugin);
    }

    @Test
    void testZoneNameNotMatchesPattern() {
        var player = server.addPlayer();
        var zoneName = "Test%";
        var expectedMessage = MiniMessage.miniMessage().deserialize(Constants.ZONE_INVALID_MESSAGE,
                Placeholder.component("prefix", Constants.PREFIX));
        command.createZone(player, zoneName);

        assertFalse(plugin.getConfig().isSet(Constants.CONFIG_ZONE_PATH.formatted(zoneName)));
        assertEquals(expectedMessage, player.nextComponentMessage());
    }

    @Test
    void testZoneCreated() {
        var player = server.addPlayer();
        var zoneName = "Test";
        var expectedMessage = MiniMessage.miniMessage().deserialize(Constants.CREATE_ZONE_MESSAGE_SUCCESS,
                Placeholder.unparsed("zone", zoneName),
                Placeholder.component("prefix", Constants.PREFIX));
        command.createZone(player, zoneName);

        assertTrue(plugin.getConfig().isSet(Constants.CONFIG_ZONE_PATH.formatted(zoneName)));
        assertEquals(expectedMessage, player.nextComponentMessage());
    }
}
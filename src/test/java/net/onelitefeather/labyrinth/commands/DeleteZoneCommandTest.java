package net.onelitefeather.labyrinth.commands;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.onelitefeather.labyrinth.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeleteZoneCommandTest extends CommandPluginTestBase{

    private DeleteZoneCommand command;

    @Override
    @BeforeEach
    void setUp()
    {
        super.setUp();
        command = new DeleteZoneCommand(plugin);
    }

    @Test
    void testZoneDeleted()
    {
        var player = server.addPlayer();
        var zoneName = "Test";
        var expectedMessage = MiniMessage.miniMessage().deserialize(Constants.DELETE_ZONE_MESSAGE_SUCCESS,
            Placeholder.unparsed("zone", zoneName),
            Placeholder.component("prefix", Constants.PREFIX));
        var configSectionName = Constants.CONFIG_ZONE_PATH.formatted(zoneName);
        plugin.getConfig().createSection(configSectionName);
        command.deleteZone(player, zoneName);
        assertFalse(plugin.getConfig().isSet(configSectionName));
        assertEquals(expectedMessage, player.nextComponentMessage());
    }

    @Test
    void testZoneNotDeleted()
    {
        var player = server.addPlayer();
        var zoneName = "Test";
        var expectedMessage = MiniMessage.miniMessage().deserialize(Constants.ZONE_INVALID_MESSAGE,
                Placeholder.component("prefix", Constants.PREFIX));
        command.deleteZone(player, zoneName);
        assertEquals(expectedMessage, player.nextComponentMessage());

    }

}
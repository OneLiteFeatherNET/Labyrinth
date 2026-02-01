package net.onelitefeather.labyrinth.service.impl;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.utils.Constants;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

import static org.junit.jupiter.api.Assertions.*;

class ValidationServiceImplTest {

    private @NotNull ServerMock server;
    private Labyrinth plugin;
    private ValidationServiceImpl validationService;

    public static class MockLabyrinthPlugin extends Labyrinth {
        @Override
        public void onEnable() {

        }

        @Override
        public void onDisable() {

        }
    }

    @BeforeEach
    void setUp() {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(ValidationServiceImplTest.MockLabyrinthPlugin.class);
        validationService = new ValidationServiceImpl(plugin);
    }

    @AfterEach
    void tearDown() {
        MockBukkit.unmock();
    }

    @Test
    void testZoneNotFound() {
        var player = server.addPlayer();
        var zoneName = "Test";
        var expectedMessage = MiniMessage.miniMessage().deserialize(
                "Zone <zone> could not be found!", Placeholder.unparsed("zone", zoneName));
        var validationSuccessful = validationService.validateZoneInput(player, zoneName);
        assertFalse(validationSuccessful);
        assertEquals(expectedMessage, player.nextComponentMessage());
    }

    @Test
    void testZoneValid() {
        var player = server.addPlayer();
        var zoneName = "Test";
        plugin.getConfig().createSection(Constants.CONFIG_ZONE_PATH.formatted(zoneName));
        var validationSuccessful = validationService.validateZoneInput(player, zoneName);
        assertTrue(validationSuccessful);
    }

    @Test
    void testZoneNameNotMatchesPattern() {
        var player = server.addPlayer();
        var zoneName = "Test%";
        var expectedMessage = Component.text("Only characters without symbols are allowed");
        plugin.getConfig().createSection(Constants.CONFIG_ZONE_PATH.formatted(zoneName));
        var validationSuccessful = validationService.validateZoneInput(player, zoneName);
        assertFalse(validationSuccessful);
        assertEquals(expectedMessage, player.nextComponentMessage());
    }

}
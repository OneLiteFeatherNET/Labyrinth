package net.onelitefeather.labyrinth.listener;

import net.onelitefeather.labyrinth.Labyrinth;
import net.onelitefeather.labyrinth.utils.Constants;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;
import org.mockbukkit.mockbukkit.entity.SheepMock;
import org.mockbukkit.mockbukkit.entity.ZombieMock;
import org.mockbukkit.mockbukkit.world.WorldMock;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class MobspawnListenerTest {

    private @NotNull ServerMock server;
    private Labyrinth plugin;
    private MobspawnListener listener;

    public static class MockLabyrinthPlugin extends Labyrinth {
        @Override
        public void onEnable() {

        }

        @Override
        public void onDisable() {

        }
    }

    @BeforeEach
    void setUp()
    {
        server = MockBukkit.mock();
        plugin = MockBukkit.load(MobspawnListenerTest.MockLabyrinthPlugin.class);
        listener = new MobspawnListener(this.plugin);
    }

    @AfterEach
    void tearDown()
    {
        MockBukkit.unmock();
    }

    @Test
    void testNoZone()
    {
        this.server.getPluginManager().registerEvents(listener, this.plugin);
        var zombie = new ZombieMock(this.server, UUID.randomUUID());
        zombie.setLocation(new Location(new WorldMock(Material.GRASS_BLOCK, 64), 120, 64, 120));
        var event = new EntitySpawnEvent(zombie);
        this.server.getPluginManager().callEvent(event);
        assertFalse(event.isCancelled());
    }

    @Test
    void testNoCenterLocation()
    {
        this.server.getPluginManager().registerEvents(listener, this.plugin);
        var zombie = new ZombieMock(this.server, UUID.randomUUID());
        zombie.setLocation(new Location(new WorldMock(Material.GRASS_BLOCK, 64), 120, 64, 120));
        var zoneName = "Test";
        var configSectionName = Constants.CONFIG_ZONE_PATH.formatted(zoneName);
        plugin.getConfig().createSection(configSectionName);
        var event = new EntitySpawnEvent(zombie);
        this.server.getPluginManager().callEvent(event);
        assertFalse(event.isCancelled());
    }

    @Test
    void testNotInRadius()
    {
        this.server.getPluginManager().registerEvents(listener, this.plugin);

        var world = new WorldMock(Material.GRASS_BLOCK, 64);
        var zombie = new ZombieMock(this.server, UUID.randomUUID());
        zombie.setLocation(new Location(world, 10, 64, 10));
        var zoneName = "Test";
        var configSectionName = Constants.CONFIG_ZONE_PATH.formatted(zoneName);
        plugin.getConfig().createSection(configSectionName);

        var centerLocation = new Location(world, 120, 0, 120);
        plugin.getConfig().set(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zoneName), centerLocation);
        plugin.getConfig().set(Constants.CONFIG_ZONE_RADIUS_PATH.formatted(zoneName), 5d);

        var event = new EntitySpawnEvent(zombie);
        this.server.getPluginManager().callEvent(event);
        assertFalse(event.isCancelled());
    }

    @Test
    void testNotDisabled()
    {
        this.server.getPluginManager().registerEvents(listener, this.plugin);

        var world = new WorldMock(Material.GRASS_BLOCK, 64);
        var zombie = new ZombieMock(this.server, UUID.randomUUID());
        zombie.setLocation(new Location(world, 119, 64, 120));
        var zoneName = "Test";
        var configSectionName = Constants.CONFIG_ZONE_PATH.formatted(zoneName);
        plugin.getConfig().createSection(configSectionName);

        var centerLocation = new Location(world, 120, 0, 120);
        plugin.getConfig().set(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zoneName), centerLocation);
        plugin.getConfig().set(Constants.CONFIG_ZONE_RADIUS_PATH.formatted(zoneName), 5d);
        plugin.getConfig().set(Constants.CONFIG_ZONE_MOBSPAWNING_PATH.formatted(zoneName), true);

        var event = new EntitySpawnEvent(zombie);
        this.server.getPluginManager().callEvent(event);
        assertFalse(event.isCancelled());
    }

    @Test
    void testNoSpawning()
    {
        this.server.getPluginManager().registerEvents(listener, this.plugin);

        var world = new WorldMock(Material.GRASS_BLOCK, 64);
        var zombie = new ZombieMock(this.server, UUID.randomUUID());
        zombie.setLocation(new Location(world, 119, 64, 120));
        var zoneName = "Test";
        var configSectionName = Constants.CONFIG_ZONE_PATH.formatted(zoneName);
        plugin.getConfig().createSection(configSectionName);

        var centerLocation = new Location(world, 120, 0, 120);
        plugin.getConfig().set(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zoneName), centerLocation);
        plugin.getConfig().set(Constants.CONFIG_ZONE_RADIUS_PATH.formatted(zoneName), 5d);
        plugin.getConfig().set(Constants.CONFIG_ZONE_MOBSPAWNING_PATH.formatted(zoneName), false);

        var event = new EntitySpawnEvent(zombie);
        this.server.getPluginManager().callEvent(event);
        assertTrue(event.isCancelled());
    }

    @Test
    void testNoMonster()
    {
        this.server.getPluginManager().registerEvents(listener, this.plugin);

        var world = new WorldMock(Material.GRASS_BLOCK, 64);
        var sheep = new SheepMock(this.server, UUID.randomUUID());
        sheep.setLocation(new Location(world, 119, 64, 120));
        var zoneName = "Test";
        var configSectionName = Constants.CONFIG_ZONE_PATH.formatted(zoneName);
        plugin.getConfig().createSection(configSectionName);

        var centerLocation = new Location(world, 120, 0, 120);
        plugin.getConfig().set(Constants.CONFIG_ZONE_CENTER_PATH.formatted(zoneName), centerLocation);
        plugin.getConfig().set(Constants.CONFIG_ZONE_RADIUS_PATH.formatted(zoneName), 5d);
        plugin.getConfig().set(Constants.CONFIG_ZONE_MOBSPAWNING_PATH.formatted(zoneName), false);

        var event = new EntitySpawnEvent(sheep);
        this.server.getPluginManager().callEvent(event);
        assertFalse(event.isCancelled());
    }

}
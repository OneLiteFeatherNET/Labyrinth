package net.onelitefeather.labyrinth.commands;

import net.onelitefeather.labyrinth.Labyrinth;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.mockbukkit.mockbukkit.MockBukkit;
import org.mockbukkit.mockbukkit.ServerMock;

public abstract class CommandPluginTestBase {
    protected @NotNull ServerMock server;
    protected Labyrinth plugin;

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
        plugin = MockBukkit.load(CommandPluginTestBase.MockLabyrinthPlugin.class);
    }

    @AfterEach
    void tearDown()
    {
        MockBukkit.unmock();
    }
}

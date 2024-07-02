package dev.acronical.recordingindicator;

import org.bukkit.plugin.java.JavaPlugin;

public final class RecordingIndicator extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PluginEvents(), this);
        getCommand("recording").setExecutor(new PluginCommands());
        getCommand("recording").setTabCompleter(new PluginCommands());
        getCommand("live").setExecutor(new PluginCommands());
        getCommand("live").setTabCompleter(new PluginCommands());
        getServer().getConsoleSender().sendMessage("[Recording Indicator] Plugin enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage("[Recording Indicator] Plugin disabled!");
    }
}

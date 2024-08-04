package dev.acronical.recordingindicator;

import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.util.Scanner;

public final class RecordingIndicator extends JavaPlugin {

    @Override
    public void onEnable() {
        versionCheck();
        getServer().getMessenger().registerIncomingPluginChannel(this, "acronicalRecordingIndicator", (channel, player, data) -> {
            if (!channel.equals("acronicalRecordingIndicator")) return;
            PluginEvents.onModConnectPlugin(player, data);
        });
        getServer().getPluginManager().registerEvents(new PluginEvents(), this);
        getCommand("recording").setExecutor(new PluginCommands());
        getCommand("recording").setTabCompleter(new PluginCommands());
        getCommand("live").setExecutor(new PluginCommands());
        getCommand("live").setTabCompleter(new PluginCommands());
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Recording Indicator] Plugin enabled!");
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.RED + "[Recording Indicator] Plugin disabled!");
    }

    private void versionCheck() {
        try {
            String latestVersion = getLatestVersion();
            if (!getDescription().getVersion().equals(latestVersion)) {
                getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[Recording Indicator] A new version is available!");
                getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[Recording Indicator] Current version: " + getDescription().getVersion());
                getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[Recording Indicator] Latest version: " + latestVersion);
                getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[Recording Indicator] Download it from https://acronial.is-a.dev/projects");
            } else {
                getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Recording Indicator] Plugin is up to date!");
                getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Recording Indicator] Current version: " + getDescription().getVersion());
                getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[Recording Indicator] Latest version: " + latestVersion);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getLatestVersion() throws Exception {
        URL url = new URI("https://api.github.com/repos/AcronicalYT/RecordingIndicator/releases/latest").toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "RecordingIndicator");
        try {
            connection.connect();
            if (connection.getResponseCode() == 200) {
                Scanner scanner = new Scanner(connection.getInputStream());
                StringBuilder response = new StringBuilder();
                while (scanner.hasNext()) {
                    response.append(scanner.nextLine());
                }
                scanner.close();
                return response.toString().split("\"tag_name\":\"")[1].split("\"")[0];
            } else {
                throw new RuntimeException("Failed to check for updates. Response code: " + connection.getResponseCode() + " " + connection.getResponseMessage());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "An error occurred while checking for updates.";
        }
    }
}

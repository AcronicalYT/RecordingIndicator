package dev.acronical.recordingindicator;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.UUID;

public class PluginEvents implements Listener {

    File recordingFile = new File(RecordingIndicator.getPlugin(RecordingIndicator.class).getDataFolder(), "recording.yml");
    FileConfiguration recordingData = YamlConfiguration.loadConfiguration(recordingFile);

    File streamingFile = new File(RecordingIndicator.getPlugin(RecordingIndicator.class).getDataFolder(), "streaming.yml");
    FileConfiguration streamingData = YamlConfiguration.loadConfiguration(streamingFile);

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (player.getPlayerListName().startsWith("§c●§r ")) {
            recordingData.set(player.getUniqueId().toString(), true);
        } else if (player.getPlayerListName().startsWith("§d●§r ")) {
            streamingData.set(player.getUniqueId().toString(), true);
        } else {
            recordingData.set(player.getUniqueId().toString(), false);
            streamingData.set(player.getUniqueId().toString(), false);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (recordingData.contains(uuid.toString())) {
            if (recordingData.getBoolean(uuid.toString())) {
                player.setPlayerListName("§c●§r " + player.getName());
            }
        } else if (streamingData.contains(uuid.toString())) {
            if (streamingData.getBoolean(uuid.toString())) {
                player.setPlayerListName("§d●§r " + player.getName());
            }
        } else {
            player.setPlayerListName(player.getName());
        }
    }

    public static void onModConnectPlugin(Player player, byte[] data) {
        String dataString = new String(data);
        String[] dataSplit = dataString.split(";;");
        String indicator = dataSplit[0];
        boolean enabled = Boolean.parseBoolean(dataSplit[1]);

        switch (indicator) {
            case "live":
                if (enabled) player.performCommand("live on");
                if (!enabled) player.performCommand("live off");
                break;
            case "recording":
                if (enabled) player.performCommand("recording on");
                if (!enabled) player.performCommand("recording off");
                break;
            default:
                player.sendMessage(ChatColor.RED + "An error occurred when automatically setting status, aborting...");
                throw new Error("Data received with no indicator string, please contact Acronical about this.");
        }
    }
}
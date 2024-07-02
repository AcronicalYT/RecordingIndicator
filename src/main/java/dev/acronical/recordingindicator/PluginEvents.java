package dev.acronical.recordingindicator;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.util.Objects;
import java.util.UUID;

public class PluginEvents implements Listener {

    File recordingFile = new File(RecordingIndicator.getPlugin(RecordingIndicator.class).getDataFolder(), "recording.yml");
    FileConfiguration data = YamlConfiguration.loadConfiguration(recordingFile);

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (player.getPlayerListName().startsWith("§4●§r ")) {
            data.set(player.getUniqueId().toString(), true);
        } else {
            data.set(player.getUniqueId().toString(), false);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (data.contains(uuid.toString())) {
            if (data.getBoolean(uuid.toString())) {
                player.setPlayerListName("§4●§r " + player.getName());
            } else {
                player.setPlayerListName(player.getName());
            }
        }
    }

}
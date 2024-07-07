package dev.acronical.recordingindicator;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PluginCommands implements CommandExecutor, TabCompleter {

    private static final String[] COMMANDS = { "on", "off" };

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player player)) {
            commandSender.sendMessage("[!] Only players can use that command.");
            return true;
        }

        if (command.getName().equalsIgnoreCase("recording")) {
            if (strings.length == 0) {
                player.sendMessage("Usage: /recording <on|off>");
                return true;
            }

            if (strings[0].equalsIgnoreCase("on")) {
                player.setPlayerListName("§c●§r " + player.getName());
                player.sendMessage("Toggled the recording indicator on.");
                return true;
            }

            if (strings[0].equalsIgnoreCase("off")) {
                player.setPlayerListName(player.getName());
                player.sendMessage("Toggled the recording indicator off.");
                return true;
            }

            player.sendMessage("Usage: /recording <on|off>");
            return true;
        }

        if (command.getName().equalsIgnoreCase("live")) {
            if (strings.length == 0) {
                player.sendMessage("Usage: /live <on|off>");
                return true;
            }

            if (strings[0].equalsIgnoreCase("on")) {
                player.setPlayerListName("§d●§r " + player.getName());
                player.sendMessage("Toggled the streaming indicator on.");
                return true;
            }

            if (strings[0].equalsIgnoreCase("off")) {
                player.setPlayerListName(player.getName());
                player.sendMessage("Toggled the streaming indicator off.");
                return true;
            }

            player.sendMessage("Usage: /live <on|off>");
            return true;
        }

        return false;
    }

    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, String[] args) {        //create new array
        final List<String> completions = new ArrayList<>();
        StringUtil.copyPartialMatches(args[0], List.of(COMMANDS), completions);
        return completions;
    }

}

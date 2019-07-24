package com.yerti.onlyzduels.commands;

import com.yerti.core.commands.CustomCommand;
import com.yerti.onlyzduels.arena.Arena;
import com.yerti.onlyzduels.kit.DuelKit;
import com.yerti.onlyzduels.utils.Variables;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class CommandCreateArena extends CustomCommand {


    public CommandCreateArena(String command, String permission) {
        super(command, permission);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            return false;
        }

        Player player = (Player) commandSender;

        if (args.length <= 1) {
            Variables.printPrefixMessage("Invalid arguments! Usage: /duelarena create/playerone/playertwo", player);
            return false;
        }

        if (args.length == 2) {
            if (args[0].toLowerCase().equals("create")) {

                for (Arena arena : Variables.arenas) {
                    if (arena.getName().equals(args[1])) {
                        Variables.printPrefixMessage("Arena " + ChatColor.WHITE + args[1] + ChatColor.YELLOW + " already exists!", player);
                        return true;
                    }
                }

                Variables.arenas.add(new Arena(args[1], player.getWorld(), 0, 0, 0, 0, 0, 0));
                Variables.printPrefixMessage("Created Arena " + ChatColor.WHITE + args[1] + ChatColor.YELLOW + "!", player);
                Variables.printPrefixMessage("Configure the arena using these settings:", player);
                Variables.printPrefixMessage("/duelarena <arena> setworld", player);
                Variables.printPrefixMessage("/duelarena <arena> playerone", player);
                Variables.printPrefixMessage("/duelarena <arena> playertwo", player);
                return true;
            }

            for (Arena arena : Variables.arenas) {
                if (arena.getName().equals(args[0])) {
                    //Continue
                    Location location = player.getLocation();


                    switch (args[1].toLowerCase()) {
                        case "setworld":
                            arena.setWorld(player.getWorld());
                            Variables.printPrefixMessage("Successfully set the world of arena " + ChatColor.WHITE + arena.getName() + ChatColor.YELLOW + ".", player);
                            arena.saveArena();
                            return true;
                        case "playerone":
                            arena.setFirstX(location.getX());
                            arena.setFirstY(location.getY());
                            arena.setFirstZ(location.getZ());
                            arena.saveArena();
                            Variables.printPrefixMessage("Successfully set the location of player one in arena " + ChatColor.WHITE + arena.getName() + ChatColor.YELLOW + ".", player);
                            return true;
                        case "playertwo":
                            arena.setSecondX(location.getX());
                            arena.setSecondY(location.getY());
                            arena.setSecondZ(location.getZ());
                            Variables.printPrefixMessage("Successfully set the location of player two in arena " + ChatColor.WHITE + arena.getName() + ChatColor.YELLOW + ".", player);
                            arena.saveArena();
                            return true;
                    }



                }

                Variables.printPrefixMessage("The specified arena does not exist.", player);

            }



            return false;
        }

        if (args.length > 2) {
            Variables.printPrefixMessage("Invalid arguments! Usage: /duelarena create/playerone/playertwo", player);
        }

        return false;
    }
}

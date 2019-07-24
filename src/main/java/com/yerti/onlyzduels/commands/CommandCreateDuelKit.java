package com.yerti.onlyzduels.commands;

import com.yerti.core.commands.CustomCommand;
import com.yerti.onlyzduels.kit.DuelKit;
import com.yerti.onlyzduels.utils.Variables;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandCreateDuelKit extends CustomCommand {
    public CommandCreateDuelKit(String command, String permission) {
        super(command, permission);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            return false;
        }

        Player player = (Player) commandSender;

        if (args.length == 0 || args.length > 1) {
            Variables.printPrefixMessage("Invalid arguments! Usage: /createduelkit <name>", player);
            return false;
        }

        for (DuelKit kit : Variables.duelKits) {
            if (kit.getName().equals(args[0])) {
                Variables.printPrefixMessage("DuelKit " + ChatColor.WHITE + kit.getName() + ChatColor.YELLOW + " already exists!", player);
                return false;
            }
        }
        new DuelKit(player, args[0]);
        return false;
    }
}

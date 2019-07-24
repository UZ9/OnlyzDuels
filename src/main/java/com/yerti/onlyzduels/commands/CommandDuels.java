package com.yerti.onlyzduels.commands;

import com.yerti.core.commands.CustomCommand;
import com.yerti.onlyzduels.arena.Arena;
import com.yerti.onlyzduels.arena.Duel;
import com.yerti.onlyzduels.arena.EnumArenaStatus;
import com.yerti.onlyzduels.arena.EnumDuelStatus;
import com.yerti.onlyzduels.gui.GUIDuels;
import com.yerti.onlyzduels.gui.GUISelectKit;
import com.yerti.onlyzduels.utils.Variables;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionEffect;

public class CommandDuels extends CustomCommand {
    public CommandDuels(String command) {
        super(command, null);
    }

    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            return false;
        }

        Player player = (Player) commandSender;

        if (args.length == 0) {
            Variables.printPrefixMessage("Too few arguments! Usage: /duel <player>", player);
            //GUIDuels duelGUI = new GUIDuels();

            //player.openInventory(duelGUI.getInventory());
            return true;
        }

        if (args.length == 2) {
            if (args[0].toLowerCase().equals("accept")) {
                if (Bukkit.getPlayer(args[1]) == null) return false;

                Player player1 = Bukkit.getPlayer(args[1]);



                for (Duel duel : Variables.duels) {
                    if (player.equals(duel.getPlayerTwo()) && duel.getPlayerOne().equals(player1)) {
                        if (duel.getStatus() == EnumDuelStatus.REQUEST) {
                            Variables.printPrefixMessage("You have accepted the duel. Please wait while we find you an arena.", duel.getPlayerTwo());
                            Variables.printPrefixMessage(player.getName() + " has accepted the duel. Please wait while we find you an arena.", duel.getPlayerOne());

                            for (Arena arena : Variables.arenas) {

                                if (duel.getKit().getName().contains("Sumo")) {
                                    if (arena.getName().contains("Sumo")) {
                                        duel.setupDuel(arena, duel);
                                    }
                                } else {
                                    duel.setupDuel(arena, duel);
                                }

                            }

                        } else {
                            Variables.printPrefixMessage("The duel is already in progress!", player);
                            return true;
                        }
                    }
                }
            } else {
                Variables.printPrefixMessage("Invalid subcommand! Usage: /duel <player>/accept", player);
            }
        }

        if (args.length == 1) {

            if (Bukkit.getPlayer(args[0]) != null) {
                if (Bukkit.getPlayer(args[0]).isOnline()) {



                    Player targetPlayer = Bukkit.getPlayer(args[0]);
                    if (player.equals(targetPlayer)) {
                        Variables.printPrefixMessage("Silly you! You can't fight yourself!", player);
                        return true;
                    }

                    for (Duel duel : Variables.duels) {
                        if (duel.getPlayerOne().equals(targetPlayer) || duel.getPlayerTwo().equals(targetPlayer)) {
                            Variables.printPrefixMessage("This player is currently in a duel!", player);
                            return true;
                        }
                    }

                    //Open up GUI showing selected arena and selected type
                    //TODO: Work on implementing this better
                    GUIDuels arenaInventory = new GUIDuels(player, targetPlayer);

                    player.openInventory(arenaInventory.getInventory());


                } else {
                    Variables.printPrefixMessage("This player is offline!", player);
                }
            } else {
                Variables.printPrefixMessage("This player is offline!", player);
            }
        }

        if (args.length > 2) {
            Variables.printPrefixMessage("Too many arguments! Usage: /duel OR /duel <player>", player);
        }

        return false;
    }





}

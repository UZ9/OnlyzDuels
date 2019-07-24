package com.yerti.onlyzduels.utils;

import com.yerti.onlyzduels.arena.Arena;
import com.yerti.onlyzduels.arena.Duel;
import com.yerti.onlyzduels.kit.DuelKit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Variables {

    public static final String PREFIX = "" + ChatColor.GOLD + ChatColor.BOLD + "OnlyzMc";
    public static List<DuelKit> duelKits = new ArrayList<DuelKit>();
    public static List<Duel> duels = new ArrayList<Duel>();
    public static HashMap<Player, Player> duelRequest = new HashMap<Player, Player>();
    public static List<Arena> arenas = new ArrayList<Arena>();


    public static void printPrefixMessage(String message, Player player) {
        player.sendMessage(Variables.PREFIX + ChatColor.GRAY + " » " + ChatColor.YELLOW + message);
    }

}

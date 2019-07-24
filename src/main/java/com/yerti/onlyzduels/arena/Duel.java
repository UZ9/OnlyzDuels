package com.yerti.onlyzduels.arena;

import com.yerti.onlyzduels.kit.DuelKit;
import com.yerti.onlyzduels.utils.CountDown;
import com.yerti.onlyzduels.utils.Variables;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.scheduler.BukkitScheduler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Duel {

    private static Plugin pl;

    private EnumDuelStatus status = EnumDuelStatus.REQUEST;
    private Player playerOne;
    private Player playerTwo;
    private DuelKit kit;

    public Duel(Plugin pl) {
        this.pl = pl;
    }

    public Duel(DuelKit kit, Player playerOne, Player playerTwo) {
        this.playerOne = playerOne;
        this.playerTwo = playerTwo;
        this.kit = kit;
    }




    public Player getPlayerOne() {
        return playerOne;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public DuelKit getKit() {
        return kit;
    }

    public void setStatus(EnumDuelStatus status) {
        this.status = status;
    }

    public EnumDuelStatus getStatus() {
        return status;
    }


    public void setupDuel(Arena arena, Duel duel) {
        if (arena.getArenaStatus() == EnumArenaStatus.EMPTY) {
            arena.setArenaStatus(EnumArenaStatus.PROGRESS);
            arena.setDuel(duel);
            Variables.printPrefixMessage("Starting match...", duel.getPlayerOne());
            Variables.printPrefixMessage("Starting match...", duel.getPlayerTwo());

            duel.getPlayerOne().teleport(new Location(arena.getWorld(), arena.getFirstX(), arena.getFirstY(), arena.getFirstZ()));
            duel.getPlayerTwo().teleport(new Location(arena.getWorld(), arena.getSecondX(), arena.getSecondY(), arena.getSecondZ()));

            //Set items
            duel.getPlayerOne().getInventory().setContents(duel.getKit().getItems());
            duel.getPlayerTwo().getInventory().setContents(duel.getKit().getItems());

            //Set armor
            duel.getPlayerOne().getEquipment().setArmorContents(duel.getKit().getArmor());
            duel.getPlayerTwo().getEquipment().setArmorContents(duel.getKit().getArmor());

            duel.getPlayerOne().setHealth(20);
            duel.getPlayerTwo().setHealth(20);

            duel.getPlayerOne().setGameMode(GameMode.SURVIVAL);
            duel.getPlayerTwo().setGameMode(GameMode.SURVIVAL);

            clearPotionEffects(duel.getPlayerOne());
            clearPotionEffects(duel.getPlayerTwo());

            duel.getPlayerOne().playSound(duel.getPlayerOne().getLocation(), Sound.ORB_PICKUP, 1, 1);
            duel.getPlayerOne().playSound(duel.getPlayerTwo().getLocation(), Sound.ORB_PICKUP, 1, 1);

            duel.setStatus(EnumDuelStatus.COUNTDOWN);
            //Start timer
            new CountDown(duel).runTaskTimer(pl, 0L, 20L);


        }
    }


    private void clearPotionEffects(Player player) {
        for (PotionEffect effect : player.getActivePotionEffects()) {
            player.removePotionEffect(effect.getType());
        }
    }
}

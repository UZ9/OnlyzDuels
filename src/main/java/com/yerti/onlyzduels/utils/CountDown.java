package com.yerti.onlyzduels.utils;

import com.yerti.onlyzduels.arena.Duel;
import com.yerti.onlyzduels.arena.EnumDuelStatus;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class CountDown extends BukkitRunnable {

    int delay;
    Duel duel;

    public CountDown(Duel duel) {
        this.delay = 5;
        this.duel = duel;
    }

    @Override
    public void run() {
        duel.setStatus(EnumDuelStatus.COUNTDOWN);
        if (duel == null) {
            this.cancel();
        }
        if (this.delay > 0) {
            Variables.printPrefixMessage(String.valueOf(this.delay), duel.getPlayerTwo());
            Variables.printPrefixMessage(String.valueOf(this.delay), duel.getPlayerOne());
            duel.getPlayerOne().playSound(duel.getPlayerOne().getLocation(), Sound.ORB_PICKUP, 1, 1);
            duel.getPlayerOne().playSound(duel.getPlayerTwo().getLocation(), Sound.ORB_PICKUP, 1, 1);

            this.delay--;
        } else {
            this.cancel();
            duel.setStatus(EnumDuelStatus.PROGRESS);
        }
    }
}
package com.yerti.onlyzduels.events;

import com.yerti.onlyzduels.arena.Arena;
import com.yerti.onlyzduels.arena.Duel;
import com.yerti.onlyzduels.arena.EnumArenaStatus;
import com.yerti.onlyzduels.arena.EnumDuelStatus;
import com.yerti.onlyzduels.utils.Variables;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;


public class OnPlayerKill implements Listener {

    Plugin pl;
    World mainWorld;

    public OnPlayerKill(Plugin pl) {
        this.pl = pl;
        mainWorld = Bukkit.getWorld("world");
    }

    @EventHandler
    public void onPlayerKill(PlayerDeathEvent event) {

        if (event.getEntity().getKiller() == null) {
            return;
        }

       for (Duel duel : Variables.duels) {

           if (event.getEntity().getKiller().equals(duel.getPlayerOne()) || event.getEntity().getKiller().equals(duel.getPlayerTwo())) {

               for (Arena arena : Variables.arenas) {
                   if (arena.getDuel() != null) {
                       if (arena.getDuel().equals(duel)) {
                           arena.setDuel(null);
                           arena.setArenaStatus(EnumArenaStatus.EMPTY);
                           break;
                       }
                   }
               }

               Variables.duels.remove(duel);
               Variables.printPrefixMessage(event.getEntity().getKiller().getDisplayName() + " has won the duel.", event.getEntity());
               Variables.printPrefixMessage(event.getEntity().getKiller().getDisplayName() + " has won the duel.", event.getEntity().getKiller());

               event.getEntity().getInventory().clear();
               event.getEntity().getInventory().setArmorContents(null);
               event.getEntity().getKiller().getInventory().clear();
               event.getEntity().getKiller().getInventory().setArmorContents(null);

               event.getEntity().getActivePotionEffects().clear();
               for (PotionEffect effect : event.getEntity().getKiller().getActivePotionEffects())
                   event.getEntity().getKiller().removePotionEffect(effect.getType());

               event.getEntity().getKiller().setGameMode(GameMode.ADVENTURE);
               event.getEntity().setGameMode(GameMode.ADVENTURE);

               event.getEntity().getKiller().teleport(mainWorld.getSpawnLocation());
               event.getEntity().getKiller().setHealth(20);


           }
       }



    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent event) {

        event.getPlayer().teleport(event.getPlayer().getWorld().getSpawnLocation());

        for (Duel duel : Variables.duels) {
            if (duel.getPlayerOne().equals(event.getPlayer())) {
                if (duel.getStatus() == EnumDuelStatus.REQUEST || duel.getStatus() == EnumDuelStatus.WAITING) {
                    return;
                }
                duel.getPlayerTwo().teleport(mainWorld.getSpawnLocation());
                Variables.printPrefixMessage("The other player has left the game!", duel.getPlayerTwo());
                Variables.duels.remove(duel);
                return;
            } else if (duel.getPlayerTwo().equals(event.getPlayer())) {
                if (duel.getStatus() == EnumDuelStatus.REQUEST || duel.getStatus() == EnumDuelStatus.WAITING) {
                    return;
                }
                duel.getPlayerTwo().teleport(mainWorld.getSpawnLocation());
                Variables.printPrefixMessage("The other player has left the game!", duel.getPlayerOne());
                Variables.duels.remove(duel);
                return;
            }


        }

    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();

            for (Duel duel : Variables.duels) {
                if (duel.getPlayerOne().equals(player) || duel.getPlayerOne().equals(player)) {
                    //e.getDamage()
                    if (duel.getKit().getName().contains("Combo")) {
                        player.setNoDamageTicks(0);
                    }

                }
            }
        }
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {

        for (Duel duel : Variables.duels) {
            if (duel.getStatus() == EnumDuelStatus.COUNTDOWN) {
                if (duel.getPlayerOne().equals(event.getPlayer()) || duel.getPlayerTwo().equals(event.getPlayer())) {
                    if (!event.getFrom().toVector().equals(event.getTo().toVector()))
                        event.setCancelled(true);
                }
            }

        }

    }


}

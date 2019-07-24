package com.yerti.onlyzduels.gui;

import com.yerti.core.inventories.CustomInventory;
import com.yerti.core.inventories.IInventory;
import com.yerti.core.items.CustomItemStack;
import com.yerti.onlyzduels.arena.Duel;
import com.yerti.onlyzduels.kit.DuelKit;
import com.yerti.onlyzduels.utils.Variables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

//TODO: Rename to GUIDuelsKits
public class GUIDuels implements IInventory {

    Player firstPlayer;
    Player secondPlayer;


    public GUIDuels(Player firstPlayer, Player secondPlayer) {
        this.firstPlayer = firstPlayer;
        this.secondPlayer = secondPlayer;
    }


    @Override
    public Inventory getInventory() {
        CustomInventory inventory = new CustomInventory(this, 18, "Kit Selection", new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));


        for (DuelKit kit : Variables.duelKits) {
            ItemStack displayItem = kit.getDisplayItem();
            ItemMeta meta = displayItem.getItemMeta();
            displayItem.setItemMeta(meta);
            inventory.getInventory().addItem(kit.getDisplayItem());

        }

        inventory.createBackground();
        //inventory.getInventory().setItem(13, new CustomItemStack(Material.FISHING_ROD, 1, ChatColor.YELLOW + "Cool!", true));
        return inventory.getInventory();
    }

    @Override
    public void onGUI(Player player, int slot, ItemStack itemStack) {
        if (itemStack == null || itemStack.getType().equals(Material.AIR)) {
            return;
        }

        for (DuelKit kit : Variables.duelKits) {
            if (kit.getDisplayItem().equals(itemStack)) {

                Variables.duels.add(new Duel(kit, firstPlayer, secondPlayer));
                Variables.printPrefixMessage("Sent a request to " + ChatColor.WHITE + secondPlayer.getName() + ChatColor.YELLOW + "!", player);
                Variables.printPrefixMessage("You have recieved a duel request from " + ChatColor.WHITE + player.getName() + ChatColor.YELLOW + " with the kit " + ChatColor.WHITE + kit.getName() + ChatColor.YELLOW + ". Type /duel accept " + player.getName() + " to accept.", secondPlayer);
                player.closeInventory();

            }
        }

        if (slot == 13) {
            player.closeInventory();
            player.getEquipment().setHelmet(new ItemStack(Material.SEA_LANTERN));
        }
    }

}

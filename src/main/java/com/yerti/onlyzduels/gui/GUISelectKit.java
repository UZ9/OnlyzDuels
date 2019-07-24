package com.yerti.onlyzduels.gui;

import com.yerti.core.inventories.CustomInventory;
import com.yerti.core.inventories.IInventory;
import com.yerti.core.items.CustomItemStack;
import com.yerti.onlyzduels.kit.DuelKit;
import com.yerti.onlyzduels.utils.Variables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.LightningStrike;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class GUISelectKit implements IInventory {

    @Override
    public Inventory getInventory() {
        CustomInventory inventory = new CustomInventory(this, 27, "Kit Selection", new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 7));


        for (DuelKit kit : Variables.duelKits) {
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



        //Confirm and send request
        if (slot == 13) {
            player.closeInventory();
            player.getEquipment().setHelmet(new ItemStack(Material.SEA_LANTERN));
        }

        if (slot == 10) {
            //Open Kit
        }

        if (slot == 16) {
            //Select Arena
        }
    }

}

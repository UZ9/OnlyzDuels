package com.yerti.core.inventories;

import com.yerti.core.inventories.IInventory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryHandler implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getHolder() instanceof com.yerti.core.inventories.IInventory) {
            //Inventory is one of ours
            event.setCancelled(true);
            com.yerti.core.inventories.IInventory inventory = (IInventory) event.getInventory().getHolder();
            inventory.onGUI((Player) event.getWhoClicked(), event.getRawSlot(), event.getCurrentItem());
        }
    }


}

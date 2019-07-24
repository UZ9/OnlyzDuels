package com.yerti.onlyzduels.kit;

import com.yerti.core.items.CustomItemStack;
import com.yerti.onlyzduels.utils.Variables;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class DuelKit {

    private static Plugin pl;

    int playerCount = 0;
    String name;
    String displayName;
    String guilore;
    ItemStack displayItem;
    ItemStack[] armor;
    ItemStack[] items;

    public DuelKit(Plugin pl) {
        DuelKit.pl = pl;
    }

    public DuelKit(String name) {
        this.name = name;
    }

    public DuelKit(Player player, String name) {
        this.name = name;
        this.armor = player.getEquipment().getArmorContents();
        this.items = player.getInventory().getContents();

        //Add kit to the list
        saveKit();

        Variables.printPrefixMessage("Successfully created kit " + ChatColor.WHITE + name + ChatColor.YELLOW + "!", player);
    }

    private void saveKit() {
        //Set values in config

        //Set name of kit
        pl.getConfig().set("kits." + name + ".displayname", ("&e" + name));

        //Set armor pieces
        pl.getConfig().set("kits." + name + ".guiitem", "DIAMOND_SWORD");

        //Set lore of the item
        pl.getConfig().set("kits." + name + ".guilore", "");

        //Set armor pieces
        pl.getConfig().set("kits." + name + ".helmet", armor[0]);
        pl.getConfig().set("kits." + name + ".chestplate", armor[1]);
        pl.getConfig().set("kits." + name + ".leggings", armor[2]);
        pl.getConfig().set("kits." + name + ".boots", armor[3]);
        pl.saveConfig();

        //Set inventory
        pl.getConfig().set("kits." + name + ".contents", items);

        //Save and reload the config
        pl.saveConfig();
        pl.reloadConfig();

        for (int i = 0; i < items.length; i++) {
            if (items[i] == null) {
                items[i] = new ItemStack(Material.AIR);
            }
        }

        DuelKit duelKit = new DuelKit(name);
        duelKit.setArmor(armor);
        duelKit.setItems(items);

        ItemStack displayItem = new CustomItemStack(Material.getMaterial("DIAMOND_SWORD"), 1, ChatColor.translateAlternateColorCodes('&', "&e" + name), false);
        duelKit.setDisplayItem(displayItem);
        Variables.duelKits.add(duelKit);

    }

    public String getName() {
        return name;
    }

    public ItemStack[] getArmor() {
        return armor;
    }

    public void setArmor(ItemStack[] armor) {
        this.armor = armor;
    }

    public ItemStack[] getItems() {
        return items;
    }

    public void setItems(ItemStack[] items) {
        this.items = items;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getGuilore() {
        return guilore;
    }

    public void setGuilore(String guilore) {
        this.guilore = guilore;
    }

    public ItemStack getDisplayItem() {
        return displayItem;
    }

    public void setDisplayItem(ItemStack displayItem) {
        this.displayItem = displayItem;
    }

    public void setPlayerCount(int playerCount) {
        this.playerCount = playerCount;
    }

    public int getPlayerCount() {
        return playerCount;
    }
}

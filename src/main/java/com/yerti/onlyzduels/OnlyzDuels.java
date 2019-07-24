package com.yerti.onlyzduels;

import com.yerti.core.commands.CustomCommand;
import com.yerti.core.inventories.InventoryHandler;
import com.yerti.core.items.CustomItemStack;
import com.yerti.onlyzduels.arena.Arena;
import com.yerti.onlyzduels.arena.Duel;
import com.yerti.onlyzduels.arena.EnumDuelStatus;
import com.yerti.onlyzduels.commands.CommandCreateArena;
import com.yerti.onlyzduels.commands.CommandCreateDuelKit;
import com.yerti.onlyzduels.commands.CommandDuels;
import com.yerti.onlyzduels.events.OnPlayerKill;
import com.yerti.onlyzduels.kit.DuelKit;
import com.yerti.onlyzduels.utils.Variables;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class OnlyzDuels extends JavaPlugin {

    @Override
    public void onEnable() {

        //Give plugin ot DuelKit
        new Duel(this);
        new DuelKit(this);
        new Arena(this);
        new OnPlayerKill(this);

        CustomCommand duelCommand = new CommandDuels("duel");
        CustomCommand createArena = new CommandCreateArena("duelarena", "onlyzduels.admin");
        CustomCommand createDuelKit = new CommandCreateDuelKit("createduelkit", "onlyzduels.admin");
        duelCommand.initCommand();
        createDuelKit.initCommand();
        createArena.initCommand();
        this.getServer().getPluginManager().registerEvents(new InventoryHandler(), this);
        this.getServer().getPluginManager().registerEvents(new OnPlayerKill(this), this);
        loadConfiguration();

        this.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {

            public void run() {

                for (Duel duel : Variables.duels) {
                    if (duel.getStatus() == EnumDuelStatus.WAITING) {
                        for (Arena arena : Variables.arenas) {


                            if (duel.getKit().getName().contains("Sumo")) {
                                if (arena.getName().contains("Sumo")) {
                                    duel.setupDuel(arena, duel);
                                }
                            } else {
                                duel.setupDuel(arena, duel);
                            }

                        }
                    }

                }


            }
        }, 0, 20*5);

    }

    @Override
    public void onDisable() {
        Variables.duelKits.clear();




        Variables.arenas.clear();
    }

    private void loadConfiguration() {
        this.saveConfig();

        if (getConfig().getConfigurationSection("arenas.") != null) {
            for (final String key : getConfig().getConfigurationSection("arenas.").getKeys(false)) {

                double firstX = getConfig().getDouble("arenas." + key + ".firstx");
                double firstY = getConfig().getDouble("arenas." + key + ".firsty");
                double firstZ = getConfig().getDouble("arenas." + key + ".firstz");

                double secondX = getConfig().getDouble("arenas." + key + ".secondx");
                double secondY = getConfig().getDouble("arenas." + key + ".secondy");
                double secondZ = getConfig().getDouble("arenas." + key + ".secondz");

                World world = Bukkit.getWorld(getConfig().getString("arenas." + key + ".world"));

                Variables.arenas.add(new Arena(key, world, firstX, firstY, firstZ, secondX, secondY, secondZ));




            }
        }

        if (getConfig().getConfigurationSection("kits.") != null) {
            for (final String key : getConfig().getConfigurationSection("kits.").getKeys(false)) {
                List<ItemStack> items = (List<ItemStack>) getConfig().get("kits." + key + ".contents");
                ItemStack[] armor = new ItemStack[4];
                armor[0] = getConfig().getItemStack("kits." + key + ".helmet");
                armor[1] = getConfig().getItemStack("kits." + key + ".chestplate");
                armor[2] = getConfig().getItemStack("kits." + key + ".leggings");
                armor[3] = getConfig().getItemStack("kits." + key + ".boots");

                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i) == null) {
                        items.set(i, new ItemStack(Material.AIR));
                    }
                }

                DuelKit duelKit = new DuelKit(key);
                duelKit.setArmor(armor);
                ItemStack[] itemContents = new ItemStack[items.size()];
                items.toArray(itemContents);
                duelKit.setItems(itemContents);

                ItemStack displayItem = new CustomItemStack(Material.getMaterial(getConfig().getString("kits." + key + ".guiitem")), 1, ChatColor.translateAlternateColorCodes('&', getConfig().getString("kits." + key + ".displayname")), false);
                ItemMeta meta = displayItem.getItemMeta();
                List<String> lore = new ArrayList<String>();
                lore.add(getConfig().getString("kits." + key + ".guilore"));
                meta.setLore(lore);
                displayItem.setItemMeta(meta);



                duelKit.setDisplayItem(displayItem);
                Variables.duelKits.add(duelKit);
            }

        }
    }


}

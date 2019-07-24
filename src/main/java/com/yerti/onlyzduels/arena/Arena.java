package com.yerti.onlyzduels.arena;

import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class Arena {

    private static Plugin pl;
    EnumArenaStatus arenaStatus = EnumArenaStatus.EMPTY;
    String name;
    World world;
    Duel duel;
    double firstX;
    double firstY;
    double firstZ;
    double secondX;
    double secondY;
    double secondZ;

    public Arena(Plugin pl) {
        this.pl = pl;
    }

    public Arena(String name, World world, double firstX, double firstY, double firstZ, double secondX, double secondY, double secondZ) {
        this.world = world;
        this.firstX = firstX;
        this.firstY = firstY;
        this.firstZ = firstZ;
        this.secondX = secondX;
        this.secondY = secondY;
        this.secondZ = secondZ;
        this.name = name;

        saveArena();
    }

    public void saveArena() {
        pl.getConfig().set("arenas." + name + ".firstx", firstX);
        pl.getConfig().set("arenas." + name + ".firsty", firstY);
        pl.getConfig().set("arenas." + name + ".firstz", firstZ);

        pl.getConfig().set("arenas." + name + ".secondx", secondX);
        pl.getConfig().set("arenas." + name + ".secondy", secondY);
        pl.getConfig().set("arenas." + name + ".secondz", secondZ);

        pl.getConfig().set("arenas." + name + ".world", world.getName());

        pl.saveConfig();
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public double getFirstX() {
        return firstX;
    }

    public void setFirstX(double firstX) {
        this.firstX = firstX;
    }

    public double getFirstY() {
        return firstY;
    }

    public void setFirstY(double firstY) {
        this.firstY = firstY;
    }

    public double getFirstZ() {
        return firstZ;
    }

    public void setFirstZ(double firstZ) {
        this.firstZ = firstZ;
    }

    public double getSecondX() {
        return secondX;
    }

    public void setSecondX(double secondX) {
        this.secondX = secondX;
    }

    public double getSecondY() {
        return secondY;
    }

    public void setSecondY(double secondY) {
        this.secondY = secondY;
    }

    public double getSecondZ() {
        return secondZ;
    }

    public void setSecondZ(double secondZ) {
        this.secondZ = secondZ;
    }

    public EnumArenaStatus getArenaStatus() {
        return arenaStatus;
    }

    public void setArenaStatus(EnumArenaStatus arenaStatus) {
        this.arenaStatus = arenaStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Duel getDuel() {
        return duel;
    }

    public void setDuel(Duel duel) {
        this.duel = duel;
    }
}

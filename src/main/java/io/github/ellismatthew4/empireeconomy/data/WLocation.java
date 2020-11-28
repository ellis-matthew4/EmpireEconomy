package io.github.ellismatthew4.empireeconomy.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class WLocation {
    private String world;
    private double x;
    private double y;
    private double z;

    public WLocation(Location l) {
        this.world = l.getWorld().getName();
        this.x = Math.floor(l.getX());
        this.y = Math.floor(l.getY());
        this.z = Math.floor(l.getZ());
    }

    public Location asLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }

    public String toString() {
        return "X: " + x + " Y: " + y + " Z: " + z;
    }
}

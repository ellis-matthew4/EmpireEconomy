package io.github.ellismatthew4.empireeconomy.data;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class WLocation {
    public String world;
    public Integer x;
    public Integer y;
    public Integer z;

    public WLocation(Location l) {
        this.world = l.getWorld().getName();
        this.x = (int) Math.floor(l.getX());
        this.y = (int) Math.floor(l.getY());
        this.z = (int) Math.floor(l.getZ());
    }

    public WLocation(int x, int z, String world) {
        this.world = world;
        this.x = x;
        this.z = z;
        this.y = 0;
    }

    public Location asLocation() {
        return new Location(Bukkit.getWorld(world), x, y, z);
    }

    public String toString() {
        return "X: " + x + " Y: " + y + " Z: " + z;
    }

    public int distanceToOrigin() {
        return (int) Math.sqrt(x^2 + z^2);
    }

    public Vector2 asVector() {return new Vector2(x,z);}
}

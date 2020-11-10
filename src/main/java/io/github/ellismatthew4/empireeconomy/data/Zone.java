package io.github.ellismatthew4.empireeconomy.data;


import org.bukkit.Location;

public class Zone {
    public WLocation loc1;
    public WLocation loc2;
    public String name;
    public String owner;
    public String msg = "";

    public Zone(Location loc1, Location loc2, String owner, String name) {
        this.loc1 = new WLocation(loc1);
        this.loc2 = new WLocation(loc2);
        this.owner = owner;
        this.name = name;
    }

    public boolean inside(Location l) {
        Location l1 = loc1.asLocation();
        Location l2 = loc2.asLocation();
        return
                (l.getX() >= Math.min(l1.getX(), l2.getX())) &&
                (l.getX() <= Math.max(l1.getX(), l2.getX())) &&
                (l.getZ() >= Math.min(l1.getZ(), l2.getZ())) &&
                (l.getZ() <= Math.max(l1.getZ(), l2.getZ()));

    }

    public int area() {
        Location l1 = loc1.asLocation();
        Location l2 = loc2.asLocation();
        return (int) (Math.abs(l1.getX() - l2.getX()) * Math.abs(l1.getZ() - l2.getZ()));
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

package io.github.ellismatthew4.empireeconomy.data;


import org.bukkit.Location;

public class Zone extends Property implements Comparable<Zone>{
    public WLocation loc1;
    public WLocation loc2;
    public String msg = "";
    public Shop shop;
    public Integer distanceToOrigin;
    public Double angleFromOrigin;
    public WLocation midpoint;

    public Zone(Location loc1, Location loc2, String owner, String name) {
        super(name, owner);
        this.loc1 = new WLocation(loc1);
        this.loc2 = new WLocation(loc2);
        this.shop = null;
        this.distanceToOrigin = getDistanceToOrigin();
    }

    public Zone(Location loc1, Location loc2, String owner, String name, Shop shop) {
        super(name, owner);
        this.loc1 = new WLocation(loc1);
        this.loc2 = new WLocation(loc2);
        this.shop = shop;
        this.distanceToOrigin = getDistanceToOrigin();
    }

    public void addShop(Shop shop) {
        this.shop = shop;
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

    public String toString() {
        return "§e[ZONE] " + name + (shop == null ? "" : ": Shop: " + shop.name);
    }

    @Override
    public void transfer(String newOwner) {
        owner = newOwner;
        repo = false;
        if (shop != null) {
            shop.owner = newOwner;
            shop.repo = false;
        }
    }

    @Override
    public void returnToOwner() {
        repo = false;
        if (shop != null)
            shop.repo = false;
    }

    @Override
    public void repossess() {
        repo = true;
        if (shop != null)
            shop.repo = true;
    }

    public int getDistanceToOrigin() {
        double midpointX = (loc1.x + loc2.x) / 2.0;
        double midpointZ = (loc1.z + loc2.z) / 2.0;
        midpoint = new WLocation((int) midpointX, (int) midpointZ, loc1.world);
        angleFromOrigin = (new Vector2(0.0,0.0)).angleTo(midpoint.asVector());
        return (int) Math.sqrt((midpointX * midpointX) + (midpointZ * midpointX));
    }

    @Override
    public int compareTo(Zone z) {
        if (distanceToOrigin.equals(z.distanceToOrigin)) {
            return angleFromOrigin.compareTo(z.angleFromOrigin);
        }
        return distanceToOrigin.compareTo(z.distanceToOrigin);
    }

    public int compareTo(Location l) {
        Vector2 vec = new Vector2(l.getX(), l.getZ());
        double degs = (new Vector2(0.0,0.0)).angleTo(vec);
        int ldist = (new Vector2(0.0,0.0)).distanceTo(vec);
        if (angleFromOrigin == null) {
            distanceToOrigin = getDistanceToOrigin();
        }
        if (distanceToOrigin == degs) {
            return angleFromOrigin.compareTo(degs);
        }
        return distanceToOrigin.compareTo(ldist);
    }
}

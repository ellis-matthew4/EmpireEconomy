package io.github.ellismatthew4.empireeconomy.utils;

import io.github.ellismatthew4.empireeconomy.data.Listing;
import io.github.ellismatthew4.empireeconomy.data.Shop;
import io.github.ellismatthew4.empireeconomy.data.WLocation;
import io.github.ellismatthew4.empireeconomy.data.Zone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ZoneHandler {
    private final List<Zone> zones = DataStoreService.getInstance().data.zones;
    private static ZoneHandler instance;

    public static ZoneHandler getInstance() {
        if (instance == null) {
            instance = new ZoneHandler();
            Collections.sort(instance.zones);
        }
        return instance;
    }

    public boolean addZone(Player p, Zone z) {
        if (zoneNotExists(z)) {
            zones.add(z);
            Collections.sort(zones);
        } else {
            p.sendMessage("§4[SYSTEM] Claim failed. Is this area or name taken already?");
            return false;
        }
        return true;
    }

    public boolean deleteZone(String name, String player) {
        for (int i = 0; i < zones.size(); i++) {
            Zone z = zones.get(i);
            if (z.name.equals(name) && z.owner.equals(player)) {
                if (z.shop != null) {
                    for (Listing l : z.shop.listings) {
                        Bukkit.getPlayer(z.owner).getInventory().addItem(l.asItemStack());
                    }
                }
                zones.remove(i);
                return true;
            }
        }
        return false;
    }

    private boolean zoneNotExists(Zone z) {
        for (int i = 0; i < zones.size(); i++) {
            Zone ez = zones.get(i);
            if (ez.inside(z.loc1.asLocation()) || ez.inside(z.loc2.asLocation()) || ez.name.equals(z.name)) {
                return false;
            }
        }
        return true;
    }

    public boolean zoneExists(String name) {
        for (Zone z : zones) {
            if (z.name.equals(name))
                return true;
        }
        return false;
    }

    public Zone getZone(Location l) {
        Zone res = bSearch(l);
        return res == null ? null : res;
    }

    public Zone getZone(String name) {
        for (Zone z : zones) {
            if (z.name.equals(name))
                return z;
        }
        return null;
    }

    private Zone bSearch(Location key) {
        if (zones.size() == 0) return null;
        if (zones.size() == 1) return zones.get(0).inside(key) ? zones.get(0) : null;
        int index = getNearestIndex(key);
        if (zones.get(index).inside(key)) {
            return zones.get(index);
        }
        return null;
    }

    private Integer getNearestIndex(Location key) {
        int n = zones.size();
        if (zones.get(0).inside(key)) {
            return 0;
        }
        if (zones.get(n-1).inside(key)) {
            return n - 1;
        }
        int i = 0, j = n, mid = 0;
        while (i < j) {
            mid = (i + j) / 2;
            if (zones.get(mid).inside(key)) {
                return mid;
            } else {
                if (zones.get(mid).compareTo(key) > 0) {
                    if (mid > 0 && zones.get(mid - 1).compareTo(key) < 0) {
                        return getClosestZone(mid, mid - 1, key);
                    }
                    j = mid;
                } else {
                    if (mid < n - 1 && zones.get(mid + 1).compareTo(key) > 0) {
                        return getClosestZone(mid, mid + 1, key);
                    }
                    i = mid + 1;
                }
            }
        }
        return mid;

    }

    private int getClosestZone(int a, int b, Location key) {
        if (zones.get(a).midpoint.asLocation().distanceSquared(key) < zones.get(b).midpoint.asLocation().distanceSquared(key)) {
            return a;
        } else {
            return b;
        }
    }

    public List<Zone> zonesByPlayer(Player p) {
        String key = p.getDisplayName();
        ArrayList<Zone> result = new ArrayList<Zone>();
        for (Zone z : zones) {
            if (z.owner.equals(key)) {
                result.add(z);
            }
        }
        return result;
    }

    public Zone getZone(int i) {
        return zones.get(i);
    }

    public void punish(String player) {
        for (Zone z : zones) {
            if (z.owner.equals(player)) {
                z.repossess();
            }
        }
    }

    public void pardon(String player) {
        for (Zone z : zones) {
            if (z.owner.equals(player)) {
                z.returnToOwner();
            }
        }
    }

    // Returns either the first shop with the given name, or the shop in the current zone if the name matches
    public Shop getShop(Location l, String shopName) {
        Zone current = getZone(l);
        if (current != null && current.shop != null && current.shop.name.equalsIgnoreCase(shopName)) {
            return current.shop;
        } else {
            for (Zone z : zones) {
                if (z.shop != null && z.shop.name.equalsIgnoreCase(shopName)) {
                    return z.shop;
                }
            }
        }
        return null;
    }
}

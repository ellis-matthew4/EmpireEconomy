package io.github.ellismatthew4.empireeconomy.utils;

import io.github.ellismatthew4.empireeconomy.data.Listing;
import io.github.ellismatthew4.empireeconomy.data.Zone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

public class ZoneHandler {
    private final List<Zone> zones = DataStoreService.getInstance().data.zones;

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
        for (int i = 0; i < zones.size(); i++) {
            Zone z = zones.get(i);
            if (z.inside(l)) {
                return z;
            }
        }
        return null;
    }

    public Zone getZone(String name) {
        return bSearch(zones, name);
    }

    private Zone bSearch(List<Zone> zlist, String key) {
        if (zlist.size() == 0) return null;
        if (zlist.size() == 1) {
            return key.compareToIgnoreCase(zlist.get(0).name) == 0 ? zlist.get(0) : null;
        }
        int i = (int) (zlist.size() / 2);
        Zone pivot = zlist.get(i);
        if (key.compareToIgnoreCase(pivot.name) < 0) {
            return bSearch(zlist.subList(0, i), key);
        } else if (key.compareToIgnoreCase(pivot.name) > 0) {
            return bSearch(zlist.subList(i, zlist.size()), key);
        } else {
            if (key.compareToIgnoreCase(pivot.name) == 0)
                return zlist.get(i);
            else
                return null;
        }
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
}

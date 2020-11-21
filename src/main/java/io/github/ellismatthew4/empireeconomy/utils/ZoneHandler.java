package io.github.ellismatthew4.empireeconomy.utils;

import io.github.ellismatthew4.empireeconomy.data.Listing;
import io.github.ellismatthew4.empireeconomy.data.Zone;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class ZoneHandler {
    private final List<Zone> zones = DataStoreService.getInstance().data.zones;

    public boolean addZone(Player p, Zone z) {
        if (zoneNotExists(z)) {
            zones.add(z);
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

    public int getZone(String name) {
        for (int i = 0; i < zones.size(); i++) {
            Zone z = zones.get(i);
            if (z.name.equals(name))
                return i;
        }
        return -1;
    }

    public Zone getZone(int i) {
        return zones.get(i);
    }

    public void setZoneMessage(int i, String msg) {
        zones.get(i).setMsg(msg);
    }
}

package io.github.ellismatthew4.empireeconomy.utils;

import io.github.ellismatthew4.empireeconomy.data.WarpPoint;
import io.github.ellismatthew4.empireeconomy.data.Zone;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public class WarpHandler {
    private final List<WarpPoint> wps = DataStoreService.getInstance().data.wps;

    public boolean addWarp(WarpPoint w) {
        if (!warpExists(w.name, w.owner)) {
            wps.add(w);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteWarp(String name, String player) {
        for (int i = 0; i < wps.size(); i++) {
            if (wps.get(i).name.equals(name) && wps.get(i).owner.equals(player)) {
                wps.remove(i);
                return true;
            }
        }
        return false;
    }

    public boolean warpExists(String name, String owner) {
        for (WarpPoint w : wps) {
            if (w.name.equals(name) && w.owner.equals(owner))
                return true;
        }
        return false;
    }

    public WarpPoint getWarp(String name, String owner) {
        for (WarpPoint w : wps) {
            if (w.name.equals(name) && w.owner.equals(owner))
                return w;
        }
        return null;
    }
}

package io.github.ellismatthew4.empireeconomy.utils;

import io.github.ellismatthew4.empireeconomy.data.WarpPoint;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class WarpHandler {
    private final Map<String,List<WarpPoint>> warps = DataStoreService.getInstance().data.wps;

    public boolean addWarp(WarpPoint w) {
        if (!warpExists(w.name, w.owner)) {
            if (warps.get(w.owner) == null)
                warps.put(w.owner, new ArrayList<WarpPoint>());
            warps.get(w.owner).add(w);
            return true;
        } else {
            return false;
        }
    }

    public boolean deleteWarp(String name, String player) {
        List<WarpPoint> wps = warps.get(player) == null ? new ArrayList<WarpPoint>() : warps.get(player);
        for (int i = 0; i < wps.size(); i++) {
            if (wps.get(i).name.equals(name) && wps.get(i).owner.equals(player)) {
                wps.remove(i);
                warps.put(player, wps);
                return true;
            }
        }
        return false;
    }

    public boolean warpExists(String name, String owner) {
        List<WarpPoint> wps = warps.get(owner) == null ? new ArrayList<WarpPoint>() : warps.get(owner);
        for (WarpPoint w : wps) {
            if (w.name.equals(name) && w.owner.equals(owner))
                return true;
        }
        return false;
    }

    public List<WarpPoint> warpsByPlayer(Player p) {
        return (warps.get(p.getDisplayName()) == null ? new ArrayList<WarpPoint>() : warps.get(p.getDisplayName()));
    }

    public WarpPoint getWarp(String name, String owner) {
        List<WarpPoint> wps = warps.get(owner) == null ? new ArrayList<WarpPoint>() : warps.get(owner);
        return bSearch(wps, name);
    }

    private WarpPoint bSearch(List<WarpPoint> plist, String key) {
        if (plist.size() == 0) return null;
        if (plist.size() == 1) {
            return key.compareToIgnoreCase(plist.get(0).name) == 0 ? plist.get(0) : null;
        }
        int i = (int) (plist.size() / 2);
        WarpPoint pivot = plist.get(i);
        if (key.compareToIgnoreCase(pivot.name) < 0) {
            return bSearch(plist.subList(0, i), key);
        } else if (key.compareToIgnoreCase(pivot.name) > 0) {
            return bSearch(plist.subList(i, plist.size()), key);
        } else {
            return plist.get(i);
        }
    }

    public void punish(String owner) {
        List<WarpPoint> wps = warps.get(owner) == null ? new ArrayList<WarpPoint>() : warps.get(owner);
        for (WarpPoint w : wps) {
            if (w.owner.equals(owner)) {
                w.repossess();
            }
        }
    }

    public void pardon(String owner) {
        List<WarpPoint> wps = warps.get(owner) == null ? new ArrayList<WarpPoint>() : warps.get(owner);
        for (WarpPoint w : wps) {
            if (w.owner.equals(owner)) {
                w.returnToOwner();
            }
        }
    }
}

package io.github.ellismatthew4.empireeconomy.data;

import io.github.ellismatthew4.empireeconomy.utils.DataStoreService;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class WarpPoint extends Property {
    public WLocation location;
    public boolean isPrivate;
    public int cost;

    public WarpPoint(String name, String owner, Location l, boolean isPrivate) {
        super(name, owner);
        this.location = new WLocation(l);
        this.isPrivate = isPrivate;
        this.cost = DataStoreService.getInstance().data.defaultWarpFee;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public boolean warp(Player p) {
        if (!isPrivate || p.getDisplayName().equals(owner)) {
            p.teleport(location.asLocation());
            return true;
        } else {
            p.sendMessage("§4[SYSTEM] You do not own this warp point.");
            return false;
        }
    }
}

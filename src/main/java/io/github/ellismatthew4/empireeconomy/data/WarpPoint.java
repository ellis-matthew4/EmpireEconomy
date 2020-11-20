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

    public boolean canWarp(Player p) {
        return (!isPrivate || p.getDisplayName().equals(owner));
    }

    public boolean warp(Player p) {
        p.teleport(location.asLocation());
        return true;
    }
}

package io.github.ellismatthew4.empireeconomy.data;

import io.github.ellismatthew4.empireeconomy.utils.DataStoreService;
import io.github.ellismatthew4.empireeconomy.utils.WarpHandler;
import org.bukkit.Bukkit;
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

    public String toString() {
        return "§e[WARP] " + name + ": " + location.toString();
    }

    @Override
    public void transfer(String newOwner) {
        if (new WarpHandler().warpExists(name, newOwner)) {
            Bukkit.getPlayer(newOwner).sendMessage(
                    "§4[SYSTEM] You already have a Warp Point by that name, returning to previous owner."
            );
        } else {
            owner = newOwner;
            repo = false;
        }
    }
}

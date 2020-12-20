package io.github.ellismatthew4.empireeconomy.events;

import io.github.ellismatthew4.empireeconomy.data.Shop;
import io.github.ellismatthew4.empireeconomy.data.Zone;
import io.github.ellismatthew4.empireeconomy.utils.ZoneHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;

public class shopCloseListener implements Listener {

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e) {
        Player p = (Player) e.getPlayer();
        ZoneHandler zh = new ZoneHandler();
        Zone z = zh.getZone(p.getLocation());
        if (z != null) {
            Shop shop = z.shop;
            InventoryView inv = e.getView();
            if (shop != null && inv.getTitle().equals(shop.name)) {
                shop.active = false;
            }
        }
    }
}

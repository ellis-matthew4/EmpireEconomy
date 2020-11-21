package io.github.ellismatthew4.empireeconomy.events;

import io.github.ellismatthew4.empireeconomy.data.Shop;
import io.github.ellismatthew4.empireeconomy.data.Zone;
import io.github.ellismatthew4.empireeconomy.utils.ZoneHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryView;

public class shopClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        int slot = e.getSlot();
        ZoneHandler zh = new ZoneHandler();
        Zone z = zh.getZone(p.getLocation());
        Shop shop = z.shop;
        InventoryView inv = e.getView();
        if (shop != null && inv.getTitle().equals(shop.name)) {
            shop.active = false;
            e.setCancelled(true);
            p.closeInventory();
            boolean success = shop.purchase(slot, p);
            if (!success) {
                p.sendMessage("§4[SYSTEM] You do not have enough money to buy that.");
            }
        }
    }
}
